package com.br.folhapagamento.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.br.folhapagamento.modal.Funcionario;
import java.sql.Statement;


public class FuncionarioDao {
	private Connection connection;

    public FuncionarioDao() {
    }

    public void inserir(Funcionario funcionario) {
    	String sql = "INSERT INTO funcionario(nome, cpf, data_nascimento, salario_bruto) VALUES (?, ?, ?, ?)";

        try {
            connection = ConnectionFactory.getConnection();

            if (cpfExiste(funcionario.getCpf())) {
                throw new SQLException("CPF do funcionário já cadastrado: " + funcionario.getCpf());
            }

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setDouble(4, funcionario.getSalarioBruto());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                funcionario.setId(generatedKeys.getLong(1));
            }

            stmt.close();
            System.out.println("Funcionário criado com sucesso! ID: " + funcionario.getId());

            // Aqui insere os dependentes vinculados
            DependenteDao dependenteDao = new DependenteDao();
            for (var d : funcionario.getDependentes()) {
                dependenteDao.inserir(d, funcionario.getId());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao gravar registro de funcionário: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                //é um método de exceções em Java que imprime no
                //console a mensagem do erro e toda a pilha de chamadas//
                //de métodos que levaram até ele, ajudando a identificar
                //onde e por que ocorreu a falha.
            }
        }
    }

    private boolean cpfExiste(String cpf) {
        String sql = "SELECT count(*) FROM funcionario WHERE cpf = ?";

        try (Connection checkConnection = ConnectionFactory.getConnection();
             PreparedStatement stmt = checkConnection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar CPF: " + e.getMessage());
        }
        return false;
    }

}
