package com.br.folhapagamento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String URL = "jdbc:postgresql://localhost:5432/folha_pagamento_db";
	private static final String USER = "postgres";
	private static final String PASSWORD = "123456";

	

	public static Connection getConnection() throws SQLException {
		System.out.println("Tentando conexão com o banco...");
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}





	
	
	
	
	
	
	
	
}
