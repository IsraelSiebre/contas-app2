package contasapp.view;

import contasapp.database.DataBaseManager;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public abstract class TelaRelatorio extends JPanel{


    protected final DecimalFormat df = new DecimalFormat("#,##0.00");

    protected void adicionarSecao(String titulo) {
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Alinhamento à esquerda
        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));

        painelTitulo.add(label);
        add(painelTitulo);
    }


    protected void adicionarLinha(String descricao, BigDecimal valor, boolean isNegrito) {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setBackground(Color.WHITE);
        linha.setBorder(BorderFactory.createEmptyBorder(2, isNegrito ? 10 : 30, 2, 10));

        JLabel labelDescricao = new JLabel(descricao);
        labelDescricao.setFont(new Font("Arial", isNegrito ? Font.BOLD : Font.PLAIN, 12));

        JLabel labelValor = new JLabel("R$ " + df.format(valor));
        labelValor.setFont(new Font("Arial", Font.PLAIN, 12));

        linha.add(labelDescricao, BorderLayout.WEST);
        linha.add(labelValor, BorderLayout.EAST);

        add(linha);
    }

    protected void adicionarEspacamento() {
        add(Box.createVerticalStrut(25));
    }

}
