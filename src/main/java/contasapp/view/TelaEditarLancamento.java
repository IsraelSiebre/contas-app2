package contasapp.view;

import contasapp.database.DataBaseManager;
import contasapp.model.lancamento.Lancamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class TelaEditarLancamento extends JPanel {
    private final JTable tabelaLancamentos;
    private final DefaultTableModel modeloTabela;
    private UUID lancamentoSelecionadoId; // Apenas o UUID do lançamento selecionado

    public TelaEditarLancamento() {
        setLayout(new BorderLayout());

        // Criar a tabela
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Data");
        modeloTabela.addColumn("Débito");
        modeloTabela.addColumn("Crédito");
        modeloTabela.addColumn("Tipo");
        modeloTabela.addColumn("Conta Contábil");
        modeloTabela.addColumn("Descrição");

        tabelaLancamentos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaLancamentos);
        add(scrollPane, BorderLayout.CENTER);

        // Carregar os lançamentos na tabela
        carregarLancamentos();

        // Painel com os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());

        JButton botaoExcluir = new JButton("Excluir");
        botaoExcluir.addActionListener(_ -> excluirLancamento());

        painelBotoes.add(botaoExcluir);

        add(painelBotoes, BorderLayout.SOUTH);

        // Listener para seleção de linha (captura apenas o UUID)
        tabelaLancamentos.getSelectionModel().addListSelectionListener(_ -> {
            int selectedRow = tabelaLancamentos.getSelectedRow();
            if (selectedRow != -1) {
                lancamentoSelecionadoId = UUID.fromString(modeloTabela.getValueAt(selectedRow, 0).toString());
            }
        });
    }

    private void carregarLancamentos() {
        // Cria a instância do LancamentoDAO para buscar os lançamentos
        DataBaseManager dataBaseManager = new DataBaseManager();
        List<Lancamento> lancamentos = dataBaseManager.listarTodosLancamentos();

        // Limpa os dados existentes na tabela
        modeloTabela.setRowCount(0);

        // Adiciona os lançamentos na tabela
        for (Lancamento lancamento : lancamentos) {
            modeloTabela.addRow(lancamento.linhaFormatada());
        }
    }

    private void excluirLancamento() {
        if (lancamentoSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um lançamento para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmar exclusão
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este lançamento?", "Excluir Lançamento", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {

            DataBaseManager dataBaseManager = new DataBaseManager();
            Lancamento lancamento = dataBaseManager.encontrarLancamentoPorId(lancamentoSelecionadoId);

            if (lancamento != null) {
                dataBaseManager.atualizarTotais(lancamento, "remover");

            } else {
                JOptionPane.showMessageDialog(this, "Lançamento não encontrado nos totais.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Remover o lançamento do banco de dados
            dataBaseManager.removerLancamento(lancamentoSelecionadoId);

            // Remover o lançamento da tabela
            carregarLancamentos();
            JOptionPane.showMessageDialog(this, "Lançamento excluído com sucesso.");
        }



    }
}
