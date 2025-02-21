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
        String insertSQL = "INSERT INTO lancamentos (id, data, debito, credito, tipo, descricao, conta) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db");
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {

            // Gerando um UUID único para o lançamento
            statement.setString(1, lancamento.getId().toString());
            statement.setString(2, lancamento.getData().toString());
            statement.setBigDecimal(3, lancamento.getDebito());
            statement.setBigDecimal(4, lancamento.getCredito());
            statement.setString(5, lancamento.getTipo().toString()); // Tipo de movimentação
            statement.setString(6, lancamento.getDescricao());
            statement.setString(7, lancamento.getConta().getCodigo().toString() + " - " + lancamento.getConta().getNome().toString()); // Conta contábil

            // Executa a inserção no banco de dados
            statement.executeUpdate();
            System.out.println("Lançamento adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar o lançamento: " + e.getMessage());
        }
    }

    // Método para remover um lançamento do banco de dados
    public void removerLancamento(UUID id) {
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
        List<Lancamento> lancamentos = new ArrayList<>();
        String selectSQL = "SELECT * FROM lancamentos";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db");
             PreparedStatement statement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = statement.executeQuery()) {

            // Percorrendo os resultados da consulta
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String dataString = resultSet.getString("data");
                BigDecimal debito = resultSet.getBigDecimal("debito");
                BigDecimal credito = resultSet.getBigDecimal("credito");
                String descricao = resultSet.getString("descricao");
                String tipoString = resultSet.getString("tipo");
                String contaString = resultSet.getString("conta");

                // Convertendo a data para LocalDate
                java.time.LocalDate data = java.time.LocalDate.parse(dataString);
                // Criando a instância do lançamento
                Lancamento lancamento = new Lancamento(
                        id,
                        data,
                        ContaContabil.valueOf(removerAcentos(contaString.toString().split(" - ")[1].toUpperCase().replace(" ", "_"))),
                        Tipo.valueOf(removerAcentos(tipoString.toString().toUpperCase().replace(" ", "_"))),
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
        String selectSQL = "SELECT * FROM lancamentos WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contasapp.db");
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {

            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dataString = resultSet.getString("data");
                BigDecimal debito = resultSet.getBigDecimal("debito");
                BigDecimal credito = resultSet.getBigDecimal("credito");
                String descricao = resultSet.getString("descricao");
                String tipoString = resultSet.getString("tipo");
                String contaString = resultSet.getString("conta");

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

        if (!arquivoOrigem.exists()) {
            JOptionPane.showMessageDialog(null, "O arquivo do banco de dados não foi encontrado!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
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
}

