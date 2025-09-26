package com.br.folhapagamento.modal;

import java.time.LocalDate;

public class FolhaPagamento {

	private Funcionario funcionario;
	private LocalDate dataPagamento;
	private double descontoInss;
	private double descontoIr;
	private double salarioLiquido;
	private Long codigo;

	

	/**
	 * @param funcionario
	 * @param dataPagamento
	 * @param descontoInss
	 * @param descontoIr
	 * @param salarioLiquido
	 */
	public FolhaPagamento(Funcionario funcionario, LocalDate dataPagamento,
            double descontoInss, double descontoIr, double salarioLiquido) {
this.funcionario = funcionario;
this.dataPagamento = dataPagamento;
this.descontoInss = descontoInss;
this.descontoIr = descontoIr;
this.salarioLiquido = salarioLiquido;
}


	public Funcionario getFuncionario() {
		return funcionario;
	}



	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}



	public LocalDate getDataPagamento() {
		return dataPagamento;
	}



	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}



	public double getDescontoInss() {
		return descontoInss;
	}



	public void setDescontoInss(double descontoInss) {
		this.descontoInss = descontoInss;
	}



	public double getDescontoIr() {
		return descontoIr;
	}



	public void setDescontoIr(double descontoIr) {
		this.descontoIr = descontoIr;
	}



	public double getSalarioLiquido() {
		return salarioLiquido;
	}



	public void setSalarioLiquido(double salarioLiquido) {
		this.salarioLiquido = salarioLiquido;
	}



	public void processar() {
		// Usa os métodos do Funcionario
	    this.descontoInss = funcionario.calcularInss();
	    this.descontoIr = funcionario.calcularIr();
	    this.salarioLiquido = funcionario.calcularSalarioLiquido();
		
	}

	public Long getCodigo() {
	    return codigo;
	}

	public void setCodigo(long long1) {
		this.codigo = codigo;
		
	}


	
}
