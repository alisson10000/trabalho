package com.br.folhapagamento.dao;



import com.br.folhapagamento.modal.FolhaPagamento;
import com.br.folhapagamento.modal.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class FolhaPagamentoDao {
	private Connection connection;

	public FolhaPagamentoDao() {

	}

	public void inserir(FolhaPagamento folha) {

		String sql = "insert into folha_pagamento (funcionario_id, data_pagamento, desconto_inss, desconto_ir, salario_liquido) values (?, ?, ?, ?, ?)";

		try {
			connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setLong(1, folha.getFuncionario().getId());
			stmt.setDate(2, Date.valueOf(folha.getDataPagamento()));
			stmt.setDouble(3, folha.getDescontoInss());
			stmt.setDouble(4, folha.getDescontoIr());
			stmt.setDouble(5, folha.getSalarioLiquido());

			stmt.execute();
			stmt.close();
			connection.close();

			System.out.println("Folha de Pagamento para " + folha.getFuncionario().getNome() + " criada com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro ao gravar registro de folha de pagamento: " + e.getMessage());
		}
	}

	public List<FolhaPagamento> listar() {
		
		List<FolhaPagamento> folhas = new ArrayList<>();

	    String sql = "SELECT f.id, f.funcionario_id, f.data_pagamento, f.desconto_inss, f.desconto_ir, f.salario_liquido, " +
	                 "func.nome, func.cpf, func.data_nascimento, func.salario_bruto " +
	                 "FROM folha_pagamento f " +
	                 "INNER JOIN funcionario func ON f.funcionario_id = func.id";

	    try (Connection connection = ConnectionFactory.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            // Monta o objeto Funcionario
	            Funcionario funcionario = new Funcionario(
	                    rs.getString("nome"),
	                    rs.getString("cpf"),
	                    rs.getDate("data_nascimento").toLocalDate(),
	                    rs.getDouble("salario_bruto")
	            );
	            funcionario.setId(rs.getLong("funcionario_id"));

	            // Monta a FolhaPagamento
	            FolhaPagamento folha = new FolhaPagamento(
	                    funcionario,
	                    rs.getDate("data_pagamento").toLocalDate(), 0, 0, 0
	            );
	            folha.setCodigo(rs.getLong("id"));
	            folha.setDescontoInss(rs.getDouble("desconto_inss"));
	            folha.setDescontoIr(rs.getDouble("desconto_ir"));
	            folha.setSalarioLiquido(rs.getDouble("salario_liquido"));

	            folhas.add(folha);
	        }

	    } catch (SQLException e) {
	        System.err.println("Erro ao listar folhas de pagamento: " + e.getMessage());
	    }

	    return folhas;
	}
}