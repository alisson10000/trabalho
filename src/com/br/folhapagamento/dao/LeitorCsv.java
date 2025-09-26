package com.br.folhapagamento.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.br.folhapagamento.exception.DependenteException;
import com.br.folhapagamento.modal.Dependente;
import com.br.folhapagamento.modal.Funcionario;
import com.br.folhapagamento.modal.Parentesco;

public class LeitorCsv {
	 public List<Funcionario> lerArquivo(String caminho) {
	        List<Funcionario> funcionarios = new ArrayList<>();

	        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
	            String linha;
	            Funcionario funcionarioAtual = null;

	            while ((linha = br.readLine()) != null) {
	                linha = linha.trim();

	                // linha em branco → separa funcionários
	                if (linha.isEmpty()) {
	                    if (funcionarioAtual != null) {
	                        funcionarios.add(funcionarioAtual);
	                        funcionarioAtual = null;
	                    }
	                    continue;
	                }

	                String[] partes = linha.split(";");
	                
	                // funcionário
	                if (funcionarioAtual == null) {
	                    String nome = partes[0];
	                    String cpf = partes[1];
	                    LocalDate dataNasc = LocalDate.parse(partes[2], java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
	                    double salarioBruto = Double.parseDouble(partes[3]);

	                    funcionarioAtual = new Funcionario(null, nome, cpf, dataNasc, salarioBruto);
	                } 
	                // dependente
	                else {
	                    String nome = partes[0];
	                    String cpf = partes[1];
	                    LocalDate dataNasc = LocalDate.parse(partes[2], java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
	                    Parentesco parentesco = Parentesco.valueOf(partes[3].toUpperCase());

	                    Dependente dependente = new Dependente(nome, cpf, dataNasc, parentesco);
	                    try {
	                        funcionarioAtual.adicionarDependente(dependente);
	                    } catch (DependenteException e) {
	                        System.err.println("Erro ao adicionar dependente: " + e.getMessage());
	                    }
	                }
	            }

	            // adiciona o último funcionário
	            if (funcionarioAtual != null) {
	                funcionarios.add(funcionarioAtual);
	            }

	        } catch (IOException e) {
	            System.err.println("Erro ao ler arquivo: " + e.getMessage());
	        }

	        return funcionarios;
	    }
}
