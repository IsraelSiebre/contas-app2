package contasapp.database;

import contasapp.model.lancamento.Lancamento;
import contasapp.model.lancamento.ContaContabil;
import contasapp.model.lancamento.Tipo;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;

import java.text.Normalizer;
import java.util.*;

public class DataBaseManager {

    // Método para adicionar um lançamento no banco de dados
    public void adicionarLancamento(Lancamento lancamento) {
        String insertSQL = "INSERT INTO lancamentos (id, data, conta, tipo, descricao, debito, credito) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db")) {

            // Cria as tabelas se não existirem
            try (Statement createStatement = connection.createStatement()) {
                // Tabela lancamentos
                String createLancamentosTable = "CREATE TABLE IF NOT EXISTS lancamentos ("
                        + "id TEXT NOT NULL,"
                        + "data TEXT NOT NULL,"
                        + "conta TEXT NOT NULL,"
                        + "tipo TEXT NOT NULL,"
                        + "descricao TEXT NOT NULL,"
                        + "debito DECIMAL(15, 2) NOT NULL,"
                        + "credito DECIMAL(15, 2) NOT NULL)";
                createStatement.execute(createLancamentosTable);

                // Tabela totais
                String createTotaisTable = "CREATE TABLE IF NOT EXISTS totais ("
                        + "conta VARCHAR(255),"
                        + "total DECIMAL(15, 2))";
                createStatement.execute(createTotaisTable);
            }

            // Insere dados iniciais na tabela totais se estiver vazia
            try (Statement checkStatement = connection.createStatement()) {
                ResultSet rs = checkStatement.executeQuery("SELECT COUNT(*) AS count FROM totais");
                if (rs.next() && rs.getInt("count") == 0) {
                    String insertTotaisSQL = "INSERT INTO totais (conta, total) VALUES "
                            + "('1.1.1 - Caixa', 0.00),"
                            + "('1.1.2 - Bancos', 0.00),"
                            + "('1.1.3 - Contas a Receber', 0.00),"
                            + "('1.1.4 - Estoques', 0.00),"
                            + "('1.1.5 - Adiantamentos', 0.00),"
                            + "('1.1.6 - Aplicações Financeiras', 0.00),"
                            + "('1.1.7 - Impostos a Recuperar', 0.00),"
                            + "('1.1.8 - Despesas Previstas', 0.00),"
                            + "('1.2.1 - Imobilizado', 0.00),"
                            + "('1.2.2 - Intangível', 0.00),"
                            + "('1.2.3 - Aplicações a Longo Prazo', 0.00),"
                            + "('1.2.4 - Investimentos', 0.00),"
                            + "('1.2.5 - Propriedades Investidas', 0.00),"
                            + "('1.2.6 - Ativo Diferido', 0.00),"
                            + "('2.1.1 - Fornecedores', 0.00),"
                            + "('2.2.1 - Empréstimos Bancários', 0.00),"
                            + "('2.3.1 - Obrigações Fiscais', 0.00),"
                            + "('2.4.1 - Provisões', 0.00),"
                            + "('2.5.1 - Dividendos a Pagar', 0.00),"
                            + "('2.6.1 - Obrigação Tributária', 0.00),"
                            + "('2.7.1 - Salários a Pagar', 0.00),"
                            + "('2.8.1 - Despesas a Pagar', 0.00),"
                            + "('2.9.1 - Outras Obrigações', 0.00),"
                            + "('2.2.2 - Obrigação de Longo Prazo', 0.00),"
                            + "('2.2.3 - Financiamento de Longo Prazo', 0.00),"
                            + "('2.2.4 - Empréstimos de Longo Prazo', 0.00),"
                            + "('3.1.1 - Capital Social', 0.00),"
                            + "('3.2.1 - Lucros Acumulados', 0.00),"
                            + "('3.3.1 - Reservas', 0.00),"
                            + "('3.3.2 - Reserva Legal', 0.00),"
                            + "('3.3.3 - Reserva Estatutária', 0.00),"
                            + "('3.4.1 - Ajustes a Valor Patrimonial', 0.00),"
                            + "('3.5.1 - Previsão de Dividendos', 0.00),"
                            + "('3.6.1 - Ajustes de Câmbio', 0.00),"
                            + "('4.1.1 - Receitas Operacionais', 0.00),"
                            + "('4.2.1 - Receitas Não Operacionais', 0.00),"
                            + "('4.1.2 - Receita de Vendas', 0.00),"
                            + "('4.1.3 - Receita de Serviços', 0.00),"
                            + "('4.3.1 - Receitas Financeiras', 0.00),"
                            + "('4.4.1 - Outras Receitas', 0.00),"
                            + "('5.1.1 - Despesas Operacionais', 0.00),"
                            + "('5.2.1 - Despesas Não Operacionais', 0.00),"
                            + "('5.1.2 - Despesas com Compras', 0.00),"
                            + "('5.1.3 - Despesas com Vendas', 0.00),"
                            + "('5.1.4 - Despesas Administrativas', 0.00),"
                            + "('5.2.2 - Despesas Financeiras', 0.00),"
                            + "('5.2.3 - Despesas Tributárias', 0.00),"
                            + "('5.2.4 - Despesas Operacionais Outras', 0.00),"
                            + "('5.2.5 - Imprevisto', 0.00)";

                    try (Statement insertStatement = connection.createStatement()) {
                        insertStatement.executeUpdate(insertTotaisSQL);
                        System.out.println("Dados iniciais inseridos na tabela totais!");
                    }
                }
            }

            // Insere o novo lançamento
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setString(1, lancamento.getId().toString());
                statement.setString(2, lancamento.getData().toString());
                statement.setString(3, lancamento.getConta().getCodigo() + " - " + lancamento.getConta().getNome());
                statement.setString(4, lancamento.getTipo().toString());
                statement.setString(5, lancamento.getDescricao());
                statement.setBigDecimal(6, lancamento.getDebito());
                statement.setBigDecimal(7, lancamento.getCredito());

                statement.executeUpdate();
                System.out.println("Lançamento adicionado com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar o lançamento: " + e.getMessage());
        }
    }

    // Método para remover um lançamento do banco de dados
    public void removerLancamento(UUID id) {
        if (!verificaSeTemDB("contasapp.db", "Não há lançamentos para excluir!")) {
            return;
        }

        String deleteSQL = "DELETE FROM lancamentos WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db");
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

            statement.setString(1, String.valueOf(id));

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Lançamento removido com sucesso!");
            } else {
                System.out.println("Nenhum lançamento encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover o lançamento: " + e.getMessage());
        }
    }

    // Método para listar todos os lançamentos
    public List<Lancamento> listarTodosLancamentos() {
        if (!verificaSeTemDB("contasapp.db", "Não há lançamentos!")) {
            return null;
        }

        List<Lancamento> lancamentos = new ArrayList<>();
        String selectSQL = "SELECT * FROM lancamentos";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db");
             PreparedStatement statement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = statement.executeQuery()) {

            // Percorrendo os resultados da consulta
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String dataString = resultSet.getString("data");
                String contaString = resultSet.getString("conta");
                String tipoString = resultSet.getString("tipo");
                String descricao = resultSet.getString("descricao");
                BigDecimal debito = resultSet.getBigDecimal("debito");
                BigDecimal credito = resultSet.getBigDecimal("credito");

                // Convertendo a data para LocalDate
                java.time.LocalDate data = java.time.LocalDate.parse(dataString);
                // Criando a instância do lançamento
                Lancamento lancamento = new Lancamento(
                        id,
                        data,
                        ContaContabil.valueOf(removerAcentos(contaString.split(" - ")[1].toUpperCase().replace(" ", "_"))),
                        Tipo.valueOf(removerAcentos(tipoString.toUpperCase().replace(" ", "_"))),
                        descricao,
                        debito,
                        credito
                );


                // Adicionando o lançamento à lista
                lancamentos.add(lancamento);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os lançamentos: " + e.getMessage());
        }

        return lancamentos;
    }

    public Lancamento encontrarLancamentoPorId(UUID id) {
        if (!verificaSeTemDB("contasapp.db", "Não há lançamentos!")) {
            return null;
        }

        String selectSQL = "SELECT * FROM lancamentos WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db");
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {

            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dataString = resultSet.getString("data");
                String contaString = resultSet.getString("conta");
                String tipoString = resultSet.getString("tipo");
                String descricao = resultSet.getString("descricao");
                BigDecimal debito = resultSet.getBigDecimal("debito");
                BigDecimal credito = resultSet.getBigDecimal("credito");

                // Convertendo a data para LocalDate
                java.time.LocalDate data = java.time.LocalDate.parse(dataString);

                // Criando e retornando o lançamento encontrado
                return new Lancamento(
                        id,
                        data,
                        ContaContabil.valueOf(removerAcentos(contaString.split(" - ")[1].toUpperCase().replace(" ", "_"))),
                        Tipo.valueOf(removerAcentos(tipoString.toUpperCase().replace(" ", "_"))),
                        descricao,
                        debito,
                        credito
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o lançamento: " + e.getMessage());
        }

        return null; // Retorna null se o lançamento não for encontrado
    }


    public void fazerBackup(String nomeArquivoBanco) {
        File arquivoOrigem = new File(nomeArquivoBanco);

        if (!verificaSeTemDB(nomeArquivoBanco, "O arquivo do banco de dados não foi encontrado!")) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha onde salvar o backup do banco de dados");

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File arquivoDestino = fileChooser.getSelectedFile();

            // Adiciona extensão .db se o usuário não colocar
            if (!arquivoDestino.getName().endsWith(".db")) {
                arquivoDestino = new File(arquivoDestino.getAbsolutePath() + ".db");
            }

            try (InputStream in = new FileInputStream(arquivoOrigem);
                 OutputStream out = new FileOutputStream(arquivoDestino)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                JOptionPane.showMessageDialog(null, "Backup salvo com sucesso!");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar o backup: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void carregarBackup() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o arquivo de backup do banco de dados");

        // Abre a janela para seleção do arquivo
        int opcao = fileChooser.showOpenDialog(null);
        if (opcao == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();

            // Define o arquivo de destino, garantindo o nome "contasapp.db"
            File arquivoDestino = new File("contasapp.db");

            // Copia o arquivo selecionado para o destino
            try (InputStream in = new FileInputStream(arquivoSelecionado);
                 OutputStream out = new FileOutputStream(arquivoDestino)) {

                byte[] buffer = new byte[1024];
                int bytesLidos;
                while ((bytesLidos = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesLidos);
                }
                JOptionPane.showMessageDialog(null, "Backup restaurado com sucesso.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao restaurar o backup: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Metodo para atualizar os totais na tabela "totais"
    public void atualizarTotais(Lancamento lancamento, String acao) {
        if (!verificaSeTemDB("contasapp.db", "Não há totais!")) {
            return;
        }

        String conta = lancamento.getConta().getCodigo() + " - " + lancamento.getConta().getNome();
        String tipoDaConta = lancamento.getConta().getTipo() + " - " + lancamento.getConta().getSubgrupo();
        BigDecimal debitoLancamento = lancamento.getDebito();
        BigDecimal creditoLancamento = lancamento.getCredito();

        // Verificar se já existe um total para a conta
        String selectSQL = "SELECT * FROM totais WHERE conta = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db");
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {

            statement.setString(1, conta);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Se existe, atualizar os totais de débito e crédito
                BigDecimal totalAtual = resultSet.getBigDecimal("total");

                if (acao.equals("adicionar")) {

                    // Atualiza o total de débito, se houver débito no lançamento
                    if (debitoLancamento.compareTo(BigDecimal.ZERO) > 0) {
                        if (tipoDaConta.contains("Ativo") || tipoDaConta.contains("Despesa")) {
                            totalAtual = totalAtual.add(debitoLancamento);
                        } else {
                            totalAtual = totalAtual.subtract(debitoLancamento);
                        }
                    }

                    // Atualiza o total de crédito, se houver crédito no lançamento
                    if (creditoLancamento.compareTo(BigDecimal.ZERO) > 0) {
                        if (tipoDaConta.contains("Passivo") || tipoDaConta.contains("Receita") || tipoDaConta.contains("PL")) {
                            totalAtual = totalAtual.add(creditoLancamento);
                        } else {
                            totalAtual = totalAtual.subtract(creditoLancamento);
                        }
                    }

                } else {

                    // Atualiza o total de débito, se houver débito no lançamento
                    if (debitoLancamento.compareTo(BigDecimal.ZERO) > 0) {
                        if (tipoDaConta.contains("Ativo") || tipoDaConta.contains("Despesa")) {
                            totalAtual = totalAtual.subtract(debitoLancamento);
                        } else {
                            totalAtual = totalAtual.add(debitoLancamento);
                        }
                    }

                    // Atualiza o total de crédito, se houver crédito no lançamento
                    if (creditoLancamento.compareTo(BigDecimal.ZERO) > 0) {
                        if (tipoDaConta.contains("Passivo") || tipoDaConta.contains("Receita") || tipoDaConta.contains("PL")) {
                            totalAtual = totalAtual.subtract(creditoLancamento);
                        } else {
                            totalAtual = totalAtual.add(creditoLancamento);
                        }
                    }
                }

                // Atualiza o total no banco de dados
                String updateSQL = "UPDATE totais SET total = ? WHERE conta = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                    updateStatement.setBigDecimal(1, totalAtual);
                    updateStatement.setString(2, conta);
                    updateStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar totais: " + e.getMessage());
        }
    }





    public BigDecimal buscarTotalDaConta(ContaContabil conta) {
        if (!verificaSeTemDB("contasapp.db", "Não há total da conta")) {
            return null;
        }

        String selectSQL = "SELECT * FROM totais WHERE conta = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db");
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {

            String contaComCodigo = conta.getCodigo() + " - " + conta.getNome();
            statement.setString(1, contaComCodigo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("total");

            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar totais: " + e.getMessage());
        }

        return null; // Retorna null se o lançamento não for encontrado
    }



    private static String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", ""); // Remove caracteres não-ASCII
    }

    public boolean verificaSeTemDB(String nomeArquivoBanco, String mensagemErro) {
        File arquivoOrigem = new File(nomeArquivoBanco);

        if (!arquivoOrigem.exists()) {
            JOptionPane.showMessageDialog(null, mensagemErro,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}

