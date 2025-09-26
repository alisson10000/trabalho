package com.br.folhapagamento.dao;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import com.br.folhapagamento.modal.FolhaPagamento;
public class EscritorCsv {
	public void escreverArquivo(String caminho, List<FolhaPagamento> folhas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {

            DecimalFormat df = new DecimalFormat("0.00");

            for (FolhaPagamento folha : folhas) {
                String linha = String.join(";",
                        folha.getFuncionario().getNome(),
                        folha.getFuncionario().getCpf(),
                        df.format(folha.getDescontoInss()),
                        df.format(folha.getDescontoIr()),
                        df.format(folha.getSalarioLiquido())
                );

                bw.write(linha);
                bw.newLine();
            }

            System.out.println("Arquivo CSV gerado com sucesso em: " + caminho);

        } catch (IOException e) {
            System.err.println("Erro ao escrever arquivo CSV: " + e.getMessage());
        }
    }
}
