package com.br.folhapagamento.dao;



import com.br.folhapagamento.modal.Dependente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class DependenteDao {
	private Connection connection;

	public DependenteDao() {

	}

	public void inserir(Dependente dependente, Long funcionarioId) {

		String sql = "INSERT INTO dependente (nome, cpf, data_nascimento, parentesco, funcionario_id) " +
	             "VALUES (?, ?, ?, ?::parentesco_enum, ?)";
   try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {

       stmt.setString(1, dependente.getNome());
       stmt.setString(2, dependente.getCpf());
       stmt.setDate(3, Date.valueOf(dependente.getDataNascimento()));
       stmt.setString(4, dependente.getParentesco().name()); // FILHO, SOBRINHO, OUTROS
       stmt.setLong(5, funcionarioId);

       stmt.execute();
       System.out.println("Dependente " + dependente.getNome() + " criado com sucesso!");
   } catch (SQLException e) {
       System.err.println("Erro ao gravar registro de dependente: " + e.getMessage());
   }
	}
}