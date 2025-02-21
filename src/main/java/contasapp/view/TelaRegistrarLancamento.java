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
import java.util.UUID;

public class TelaRegistrarLancamento extends JPanel {

    private final JDateChooser campoData;
    private final JComboBox<String> comboContaDebito;
    private final JComboBox<String> comboContaCredito;
    private final JComboBox<String> comboTipo;
    private final JTextField campoDescricao;
    private final JTextField campoValor; // Entrada de valores na conta

    public TelaRegistrarLancamento() {
        setLayout(new GridLayout(9, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels e campos de texto
        add(new JLabel("Data do Lançamento:"));
        campoData = new JDateChooser();
        campoData.setDateFormatString("dd/MM/yyyy");
        add(campoData);

        add(new JLabel("Valor do Lançamento:"));
        campoValor = new JTextField("Digite o valor do lançamentos.");
        addFocusListenerPlaceholder(campoValor, "Digite o valor do lançamento.");
        campoValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                // Permitir apenas números e o ponto (para números decimais)
                if (!Character.isDigit(c) && c != '.' && c != '\b') {
                    evt.consume(); // Cancela a entrada do caractere
                }
            }
        });
        add(campoValor);



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
        if (campoValor.getText().isEmpty() || campoValor.getText().equals("Digite o valor do lançamento.")) {
            JOptionPane.showMessageDialog(this, "O Valor do lançamentos é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Validar valores
        BigDecimal valorLancamento = new BigDecimal(campoValor.getText());
        if (valorLancamento.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(this, "O valor do lancamento deve ser positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }



        // Verificar se as contas são diferentes
        if (comboContaDebito.getSelectedItem().equals(comboContaCredito.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "A conta de Débito e a conta de Crédito não podem ser iguais.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UUID id = UUID.randomUUID();
        // Criar o lançamento de Débito
        Lancamento lancamentoDebito = new Lancamento(
                id,
                LocalDate.ofInstant(campoData.getDate().toInstant(), ZoneId.systemDefault()),
                ContaContabil.valueOf(removerAcentos(comboContaDebito.getSelectedItem().toString().split(" - ")[1].toUpperCase().replace(" ", "_"))),
                Tipo.valueOf(removerAcentos(comboTipo.getSelectedItem().toString().toUpperCase().replace(" ", "_"))),
                campoDescricao.getText(),
                new BigDecimal(campoValor.getText()),  // Débito
                BigDecimal.ZERO  // Crédito é 0
        );

        // Criar o lançamento de Crédito
        Lancamento lancamentoCredito = new Lancamento(
                id,
                LocalDate.ofInstant(campoData.getDate().toInstant(), ZoneId.systemDefault()),
                ContaContabil.valueOf(removerAcentos(comboContaCredito.getSelectedItem().toString().split(" - ")[1].toUpperCase().replace(" ", "_"))),
                Tipo.valueOf(removerAcentos(comboTipo.getSelectedItem().toString().toUpperCase().replace(" ", "_"))),
                campoDescricao.getText(),
                BigDecimal.ZERO,  // Débito é 0
                new BigDecimal(campoValor.getText())  // Crédito
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
        campoValor.setText("Digite o valor do lançamento.");
        campoValor.setForeground(Color.GRAY);


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
