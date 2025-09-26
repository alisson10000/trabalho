package com.br.folhapagamento.modal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.br.folhapagamento.exception.DependenteException;

public non-sealed class Funcionario extends Pessoa implements Tributavel {
	private Long id;
    private double salarioBruto;
    private double descontoInss;
    private double descontoIr;
    private List<Dependente> dependentes = new ArrayList<>();

    // Construtor completo (para quando já tem ID do banco)
    public Funcionario(Long id, String nome, String cpf, LocalDate dataNascimento, double salarioBruto) {
        super(nome, cpf, dataNascimento);
        this.id = id;
        this.salarioBruto = salarioBruto;
    }

    // Construtor sem ID (novo funcionário)
    public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto) {
        super(nome, cpf, dataNascimento);
        this.salarioBruto = salarioBruto;
    }

    // Adiciona dependente com validação
    //Esse throws lança e vai para o leitor csv
    public void adicionarDependente(Dependente d) throws DependenteException {
        for (Dependente dep : dependentes) {
            if (dep.getCpf().equals(d.getCpf())) {
                throw new DependenteException("Dependente com CPF " + d.getCpf() + " já existe!");
            }
        }
        dependentes.add(d);
    }

    @Override
    public double calcularInss() {
    	double salario = salarioBruto;

        if (salario <= 1518.00) {
            descontoInss = salario * 0.075 - 0.00;
        } else if (salario <= 2793.88) {
            descontoInss = salario * 0.09 - 22.77;
        } else if (salario <= 4190.83) {
            descontoInss = salario * 0.12 - 100.60;
        } else if (salario <= 8157.41) {
            descontoInss = salario * 0.14 - 190.42;
        } else {
            descontoInss = 951.62; // valor limite da contribuição
        }

        return descontoInss;
    }

    public double calcularBaseIr() {
        return salarioBruto - calcularInss() - (dependentes.size() * 189.59);
    }

    @Override
    public double calcularIr() {
        double base = calcularBaseIr();        if (base <= 2259.20) {
            descontoIr = 0.0;
        } else if (base <= 2826.65) {
            descontoIr = base * 0.075 - 169.44;
        } else if (base <= 3751.05) {
            descontoIr = base * 0.15 - 381.44;
        } else if (base <= 4664.68) {
            descontoIr = base * 0.225 - 662.77;
        } else {
            descontoIr = base * 0.275 - 896.00;
        }
        return descontoIr;
    }

    public double calcularSalarioLiquido() {
        return salarioBruto - calcularInss() - calcularIr();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getSalarioBruto() { return salarioBruto; }
    public void setSalarioBruto(double salarioBruto) {
        if (salarioBruto < 0) throw new IllegalArgumentException("Salário bruto não pode ser negativo!");
        this.salarioBruto = salarioBruto;
    }

    public double getDescontoInss() { return descontoInss; }
    public double getDescontoIr() { return descontoIr; }

    public List<Dependente> getDependentes() { return dependentes; }
    public void setDependentes(List<Dependente> dependentes) { this.dependentes = dependentes; }
		

		
}
