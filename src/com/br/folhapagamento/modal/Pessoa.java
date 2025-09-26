package com.br.folhapagamento.modal;

import java.time.LocalDate;

public sealed abstract   class Pessoa permits Funcionario,Dependente{

	private String nome;
	private String cpf;
	private LocalDate dataNascimento;

	public Pessoa(String nome, String cpf, LocalDate dataNascimento) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}


	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
}
