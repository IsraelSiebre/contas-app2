package contasapp.view;

import contasapp.database.DataBaseManager;
import contasapp.model.lancamento.ContaContabil;
import com.toedter.calendar.JDateChooser;
import contasapp.model.lancamento.Lancamento;
import contasapp.model.lancamento.Tipo;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.ZoneId;

public class TelaRegistrarLancamento extends JPanel {

    private final JDateChooser campoData;
    private final JComboBox<String> comboContaDebito;
    private final JComboBox<String> comboContaCredito;
    private final JComboBox<String> comboTipo;
    private final JTextField campoDescricao;
    private final JTextField campoDebito; // Entrada de valores na conta
    private final JTextField campoCredito; // Saída de valores da conta

    public TelaRegistrarLancamento() {
        setLayout(new GridLayout(9, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels e campos de texto
        add(new JLabel("Data do Lançamento:"));
        campoData = new JDateChooser();
        campoData.setDateFormatString("dd/MM/yyyy");
        add(campoData);

        add(new JLabel("Valor do Débito:"));
        campoDebito = new JTextField("Digite o valor do Débito.");
        addFocusListenerPlaceholder(campoDebito, "Digite o valor do Débito.");
        campoDebito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                // Permitir apenas números e o ponto (para números decimais)
                if (!Character.isDigit(c) && c != '.' && c != '\b') {
                    evt.consume(); // Cancela a entrada do caractere
                }
            }
        });
        add(campoDebito);

        add(new JLabel("Valor do Crédito:"));
        campoCredito = new JTextField("Digite o valor do Crédito.");
        addFocusListenerPlaceholder(campoCredito, "Digite o valor do Crédito.");
        campoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                // Permitir apenas números e o ponto (para números decimais)
                if (!Character.isDigit(c) && c != '.' && c != '\b') {
                    evt.consume(); // Cancela a entrada do caractere
                }
            }
        });
        add(campoCredito);

        add(new JLabel("Tipo do Lançamento:"));
        comboTipo = new JComboBox<>(new String[]{
                Tipo.ATIVO_CIRCULANTE.getDescricao(),
                Tipo.ATIVO_NAO_CIRCULANTE.getDescricao(),
                Tipo.PASSIVO_CIRCULANTE.getDescricao(),
                Tipo.PASSIVO_NAO_CIRCULANTE.getDescricao(),
                Tipo.PATRIMONIO_LIQUIDO.getDescricao(),
                Tipo.RECEITA.getDescricao(),
                Tipo.DESPESA.getDescricao()
        });
        add(comboTipo);

        add(new JLabel("Conta Débito:"));
        comboContaDebito = new JComboBox<>(ContaContabil.getContas());
        add(comboContaDebito);

        add(new JLabel("Conta Crédito:"));
        comboContaCredito = new JComboBox<>(ContaContabil.getContas());
        add(comboContaCredito);

        add(new JLabel("Descrição:"));
        campoDescricao = new JTextField("Digite a descrição");
        addFocusListenerPlaceholder(campoDescricao, "Digite a descrição");
        add(campoDescricao);

        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(_ -> salvarLancamentos());
        painelBotoes.add(botaoSalvar);

        add(painelBotoes);
    }

    private void addFocusListenerPlaceholder(JTextField campo, String placeholder) {
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);  // Altera a cor do texto para preto quando o campo é editado
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);  // Texto placeholder ficará cinza
                }
            }
        });
        campo.setForeground(Color.GRAY);  // Texto placeholder ficará cinza
    }

    private void salvarLancamentos() {
        // Verificar campos obrigatórios
        if (campoData.getDate() == null) {
            JOptionPane.showMessageDialog(this, "A Data é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (campoDescricao.getText().isEmpty() || campoDescricao.getText().equals("Digite a descrição")) {
            JOptionPane.showMessageDialog(this, "A Descrição é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (campoDebito.getText().isEmpty() || campoDebito.getText().equals("Digite o valor do Débito.")) {
            JOptionPane.showMessageDialog(this, "O Valor do Débito é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (campoCredito.getText().isEmpty() || campoCredito.getText().equals("Digite o valor do Crédito.")) {
            JOptionPane.showMessageDialog(this, "O Valor do Crédito é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar valores
        BigDecimal valorDebito = new BigDecimal(campoDebito.getText());
        if (valorDebito.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this, "O valor do Débito deve ser positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        BigDecimal valorCredito = new BigDecimal(campoCredito.getText());
        if (valorCredito.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this, "O valor do Crédito deve ser positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se os valores são iguais
        if (valorDebito.compareTo(valorCredito) != 0) {
            JOptionPane.showMessageDialog(this, "Os valores de Débito e Crédito devem ser iguais.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se as contas são diferentes
        if (comboContaDebito.getSelectedItem().equals(comboContaCredito.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "A conta de Débito e a conta de Crédito não podem ser iguais.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criar o lançamento de Débito
        Lancamento lancamentoDebito = new Lancamento(
                LocalDate.ofInstant(campoData.getDate().toInstant(), ZoneId.systemDefault()),
                ContaContabil.valueOf(removerAcentos(comboContaDebito.getSelectedItem().toString().split(" - ")[1].toUpperCase().replace(" ", "_"))),
                Tipo.valueOf(removerAcentos(comboTipo.getSelectedItem().toString().toUpperCase().replace(" ", "_"))),
                campoDescricao.getText(),
                new BigDecimal(campoDebito.getText()),  // Débito
                BigDecimal.ZERO  // Crédito é 0
        );

        // Criar o lançamento de Crédito
        Lancamento lancamentoCredito = new Lancamento(
                LocalDate.ofInstant(campoData.getDate().toInstant(), ZoneId.systemDefault()),
                ContaContabil.valueOf(removerAcentos(comboContaCredito.getSelectedItem().toString().split(" - ")[1].toUpperCase().replace(" ", "_"))),
                Tipo.valueOf(removerAcentos(comboTipo.getSelectedItem().toString().toUpperCase().replace(" ", "_"))),
                campoDescricao.getText(),
                BigDecimal.ZERO,  // Débito é 0
                new BigDecimal(campoCredito.getText())  // Crédito
        );

        // Adicionar os lançamentos no banco de dados
        DataBaseManager dataBaseManager = new DataBaseManager();
        dataBaseManager.adicionarLancamento(lancamentoDebito);  // Adicionar lançamento de débito
        dataBaseManager.adicionarLancamento(lancamentoCredito); // Adicionar lançamento de crédito

        // Atualizar a tabela "totais"
        dataBaseManager.atualizarTotais(lancamentoDebito, "adicionar");
        dataBaseManager.atualizarTotais(lancamentoCredito, "adicionar");

        JOptionPane.showMessageDialog(this, "Lançamentos salvos com sucesso!");
        limparCampos();
    }


    private void limparCampos() {
        campoData.setDate(null);
        campoDebito.setText("Entrada de valores na conta.");
        campoDebito.setForeground(Color.GRAY);

        campoCredito.setText("Saída de valores na conta.");
        campoCredito.setForeground(Color.GRAY);

        campoDescricao.setText("Digite a descrição");
        campoDescricao.setForeground(Color.GRAY);

        comboTipo.setSelectedIndex(0);
        comboContaDebito.setSelectedIndex(0);
        comboContaCredito.setSelectedIndex(0);
    }

    private static String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", ""); // Remove caracteres não-ASCII
    }
}
