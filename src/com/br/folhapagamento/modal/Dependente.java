package com.br.folhapagamento.modal;

import java.time.LocalDate;




public final class Dependente extends Pessoa {

	private Parentesco parentesco;

    public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco) {
        super(nome, cpf, dataNascimento);
        this.parentesco = parentesco;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    @Override
    public String toString() {
        return "Dependente{" +
                "nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", dataNascimento=" + getDataNascimento() +
                ", parentesco=" + parentesco +
                '}';
    }

}
