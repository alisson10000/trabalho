package com.br.folhapagamento.principal;

import java.sql.Connection;
import java.sql.SQLException;
import com.br.folhapagamento.dao.ConnectionFactory;
import com.br.folhapagamento.dao.EscritorCsv;
import com.br.folhapagamento.dao.FolhaPagamentoDao;
import com.br.folhapagamento.dao.FuncionarioDao;
import com.br.folhapagamento.dao.LeitorCsv;
import com.br.folhapagamento.modal.FolhaPagamento;
import com.br.folhapagamento.modal.Funcionario;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;






public class PrincipalFolhaPagamento {

	public static void main(String[] args) {
		 // Teste de conexão (trecho que você pediu para preservar)
        try (Connection conn = ConnectionFactory.getConnection()) {
            if (conn != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Informe o caminho do arquivo de entrada CSV: ");
            String entrada = sc.nextLine();

            System.out.print("Informe o caminho do arquivo de saída CSV: ");
            String saida = sc.nextLine();

            // Ler funcionários e dependentes do CSV
            LeitorCsv leitor = new LeitorCsv();
            //ler o array funcionarios no leitor csv
            List<Funcionario> funcionarios = leitor.lerArquivo(entrada);

            // DAOs
            FuncionarioDao funcionarioDao = new FuncionarioDao();
            FolhaPagamentoDao folhaDao = new FolhaPagamentoDao();//folha dao

            // Processar folha de pagamento 
            //esse array funcionarios é do arquivo leitorcsv
            for (Funcionario f : funcionarios) {
                try {
                    funcionarioDao.inserir(f); // insere funcionário + dependentes no banco

                    FolhaPagamento folha = new FolhaPagamento(f, LocalDate.now(), 0, 0, 0);
                    // aqui ele joga os valores para ser processado no folha de pagamento
                    folha.processar();

                    folhaDao.inserir(folha); // salva a folha processada no banco
                } catch (Exception ex) {
                    System.err.println("Erro ao processar funcionário " + f.getNome() + ": " + ex.getMessage());
                }
            }

            // Buscar todas as folhas processadas e exportar para CSV
            List<FolhaPagamento> folhas = folhaDao.listar();
            EscritorCsv escritor = new EscritorCsv();
            escritor.escreverArquivo(saida, folhas);

            System.out.println("Processamento concluído com sucesso!");
            System.out.println("Arquivo gerado em: " + saida);

        } finally {
            sc.close();
        }
	}

}
