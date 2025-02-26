package contasapp.view;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import contasapp.model.relatorios.DRE;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TelaDRE extends JPanel {

    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public TelaDRE() {
        DRE dre = new DRE();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Demonstração do Resultado do Exercício (DRE)"));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Botão de exportar para PDF
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton exportarButton = new JButton("Exportar para PDF");
        exportarButton.addActionListener(e -> exportarParaPDF(dre));
        painelTitulo.add(exportarButton);
        add(painelTitulo);

        adicionarEspacamento();

        // RECEITAS OPERACIONAIS
        adicionarSecao("1. RECEITAS");
        adicionarSecao("1.1 Receitas Operacionais");
        adicionarLinha("Receitas Operacionais", dre.getReceitaOperacional(), false);
        adicionarLinha("Receita de Vendas", dre.getReceitaVendas(), false);
        adicionarLinha("Receita de Serviços", dre.getReceitaServicos(), false);
        adicionarLinha("Total Receitas Operacionais", dre.calcularReceitaOperacional(), true);

        adicionarEspacamento();

        // OUTRAS RECEITAS
        adicionarSecao("1.2 Outras Receitas");
        adicionarLinha("Receitas Não Operacionais", dre.getReceitasNaoOperacionais(), false);
        adicionarLinha("Outras Receitas", dre.getOutrasReceitas(), false);
        adicionarLinha("Total Outras Receitas", dre.calcularReceitaNaoOperacional(), true);

        adicionarEspacamento();

        // RECEITA LÍQUIDA
        adicionarLinha("Receita Total", dre.calcularReceitaTotal(), true);

        adicionarEspacamento();


        // DESPESAS OPERACIONAIS
        adicionarSecao("2. DESPESAS");
        adicionarSecao("2.1 Despesas Operacionais");
        adicionarLinha("Despesas Operacionais", dre.getDespesasOperacionais(), false);
        adicionarLinha("Despesas com Compras", dre.getDespesasCompras(), false);
        adicionarLinha("Despesas com Vendas", dre.getDespesasVendas(), false);
        adicionarLinha("Despesas Administrativas", dre.getDespesasAdministrativas(), false);
        adicionarLinha("Despesas Operacionais Outras", dre.getDespesasOperacionaisOutras(), false);
        adicionarLinha("Imprevisto", dre.getImprevisto(), false);
        adicionarLinha("Resultado Operacional", dre.calcularResultadoOperacional(), true);

        adicionarEspacamento();

        // DESPESAS NÃO OPERACIONAIS
        adicionarSecao("2.2 Despesas Não Operacionais");
        adicionarLinha("Despesas Não Operacionais", dre.getDespesasNaoOperacionais(), false);

        adicionarEspacamento();

        // RESULTADO FINANCEIRO
        adicionarSecao("RESULTADO FINANCEIRO");
        adicionarLinha("Receitas Financeiras", dre.getReceitasFinanceiras(), false);
        adicionarLinha("(-) Despesas Financeiras", dre.getDespesasFinanceiras(), false);
        adicionarLinha("Resultado Antes dos Impostos", dre.calcularResultadoAntesImpostos(), true);

        adicionarEspacamento();

        // IMPOSTOS
        adicionarSecao("(-) Impostos sobre o Lucro");
        adicionarLinha("Impostos", dre.getDespesasTributarias(), false);
        adicionarLinha("Lucro Líquido", dre.calcularLucroLiquido(), true);

        adicionarEspacamento();

    }

    private void adicionarSecao(String titulo) {
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));

        painelTitulo.add(label);
        add(painelTitulo);
    }

    private void adicionarLinha(String descricao, BigDecimal valor, boolean isNegrito) {
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

    private void adicionarEspacamento() {
        add(Box.createVerticalStrut(25));
    }

    // Método para exportar o conteúdo para PDF
    public void exportarParaPDF(DRE dre) {
        try {
            // Criar JFileChooser para escolher o local e nome do arquivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar PDF");
            fileChooser.setSelectedFile(new File("DRE.pdf"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return; // Se o usuário cancelar, não faz nada
            }

            File arquivoSalvar = fileChooser.getSelectedFile();
            if (!arquivoSalvar.getName().endsWith(".pdf")) {
                arquivoSalvar = new File(arquivoSalvar.getAbsolutePath() + ".pdf");
            }

            PdfWriter writer = new PdfWriter(arquivoSalvar);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            // Adicionar título
            document.add(new Paragraph("Demonstração do Resultado do Exercício (DRE)").setFontSize(18).setBold());

            // Adicionando Receitas
            document.add(new Paragraph("1. RECEITAS").setBold());
            document.add(new Paragraph("Receitas Operacionais: R$ " + df.format(dre.getReceitaOperacional())));
            document.add(new Paragraph("Receita de Vendas: R$ " + df.format(dre.getReceitaVendas())));
            document.add(new Paragraph("Receita de Serviços: R$ " + df.format(dre.getReceitaServicos())));
            document.add(new Paragraph("Total Receitas Operacionais: R$ " + df.format(dre.calcularReceitaTotal())));

            // Adicionando Outras Receitas
            document.add(new Paragraph("1.2 Outras Receitas").setBold());
            document.add(new Paragraph("Receitas Não Operacionais: R$ " + df.format(dre.getReceitasNaoOperacionais())));
            document.add(new Paragraph("Outras Receitas: R$ " + df.format(dre.getOutrasReceitas())));
            document.add(new Paragraph("Total Outras Receitas: R$ " + df.format(dre.calcularReceitaNaoOperacional())));


            // Adicionando Despesas
            document.add(new Paragraph("2. DESPESAS").setBold());
            document.add(new Paragraph("2.1 Despesas Operacionais").setBold());
            document.add(new Paragraph("Despesas Operacionais: R$ " + df.format(dre.getDespesasOperacionais())));
            document.add(new Paragraph("Despesas com Compras: R$ " + df.format(dre.getDespesasCompras())));
            document.add(new Paragraph("Despesas com Vendas: R$ " + df.format(dre.getDespesasVendas())));
            document.add(new Paragraph("Despesas Administrativas: R$ " + df.format(dre.getDespesasAdministrativas())));
            document.add(new Paragraph("Despesas Operacionais Outras: R$ " + df.format(dre.getDespesasOperacionaisOutras())));
            document.add(new Paragraph("Imprevisto: R$ " + df.format(dre.getImprevisto())));
            document.add(new Paragraph("Resultado Operacional: R$ " + df.format(dre.calcularResultadoOperacional())));

            document.add(new Paragraph("2.2 Despesas Não Operacionais").setBold());
            document.add(new Paragraph("Despesas Não Operacionais: R$ " + df.format(dre.getDespesasNaoOperacionais())));

            // Adicionando Resultado Financeiro
            document.add(new Paragraph("RESULTADO FINANCEIRO").setBold());
            document.add(new Paragraph("Receitas Financeiras: R$ " + df.format(dre.getReceitasFinanceiras())));
            document.add(new Paragraph("(-) Despesas Financeiras: R$ " + df.format(dre.getDespesasFinanceiras())));
            document.add(new Paragraph("Resultado Antes dos Impostos: R$ " + df.format(dre.calcularResultadoAntesImpostos())));

            // Adicionando Impostos
            document.add(new Paragraph("(-) Impostos sobre o Lucro").setBold());
            document.add(new Paragraph("Impostos: R$ " + df.format(dre.getDespesasTributarias())));
            document.add(new Paragraph("Lucro Líquido: R$ " + df.format(dre.calcularLucroLiquido())));

            document.close();
            JOptionPane.showMessageDialog(this, "DRE exportada com sucesso!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar o arquivo PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}