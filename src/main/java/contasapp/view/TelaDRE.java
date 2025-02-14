package contasapp.view;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import contasapp.relatorios.DRE;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TelaDRE extends JPanel {

    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public TelaDRE() {
        DRE dre = new DRE();
        dre.buscarValoresDRE();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Demonstração do Resultado do Exercício (DRE)"));
        setBackground(Color.WHITE);

        // Configuração de margem interna
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Botão de exportar para PDF
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Alinhamento à esquerda
        JButton exportarButton = new JButton("Exportar para PDF");
        exportarButton.addActionListener(e -> exportarParaPDF(dre));
        painelTitulo.add(exportarButton);
        add(painelTitulo);

        adicionarEspacamento();

        // RECEITAS OPERACIONAIS
        adicionarSecao("1. RECEITAS", true);

        adicionarSecao("1.1 Receitas Operacionais", true);
        adicionarLinha("Receitas Operacionais", dre.getReceitaOperacional(), false);
        adicionarLinha("Receita de Vendas", dre.getReceitaVendas(), false);
        adicionarLinha("Receita de Serviços", dre.getReceitaServicos(), false);

        adicionarEspacamento();

        // OUTRAS RECEITAS
        adicionarSecao("1.2 Outras Receitas", true);
        adicionarLinha("Receitas Não Operacionais", dre.getReceitasNaoOperacionais(), false);
        adicionarLinha("Receitas Financeiras", dre.getReceitasFinanceiras(), false);
        adicionarLinha("Outras Receitas", dre.getOutrasReceitas(), false);

        adicionarEspacamento();

        // RECEITA LÍQUIDA
        adicionarLinha("Receita Líquida", dre.calcularReceitaLiquida(), true);

        adicionarEspacamento();

        // CUSTOS
        adicionarSecao("(-) Custo das Mercadorias/Serviços", true);
        adicionarLinha("Custo das Mercadorias/Serviços", dre.getDespesasCompras(), false);
        adicionarLinha("Lucro Bruto", dre.calcularLucroBruto(), true);

        adicionarEspacamento();

        // DESPESAS OPERACIONAIS
        adicionarSecao("2. DESPESAS", true);

        adicionarSecao("2.1 Despesas Operacionais", true);
        adicionarLinha("Despesas com Compras", dre.getDespesasCompras(), false);
        adicionarLinha("Despesas com Vendas", dre.getDespesasVendas(), false);
        adicionarLinha("Despesas Administrativas", dre.getDespesasAdministrativas(), false);
        adicionarLinha("Despesas Operacionais Outras", dre.getDespesasOperacionaisOutras(), false);
        adicionarLinha("Resultado Operacional", dre.calcularResultadoOperacional(), true);

        adicionarEspacamento();

        // DESPESAS NÃO OPERACIONAIS
        adicionarSecao("2.2 Despesas Não Operacionais", true);
        adicionarLinha("Despesas Não Operacionais", dre.getDespesasNaoOperacionais(), false);

        adicionarEspacamento();

        // RESULTADO FINANCEIRO
        adicionarSecao("RESULTADO FINANCEIRO", true);
        adicionarLinha("Receitas Financeiras", dre.getReceitasFinanceiras(), false);
        adicionarLinha("(-) Despesas Financeiras", dre.getDespesasFinanceiras(), false);
        adicionarLinha("Resultado Antes dos Impostos", dre.calcularResultadoAntesImpostos(), true);

        adicionarEspacamento();

        // IMPOSTOS
        adicionarSecao("(-) Impostos sobre o Lucro", true);
        adicionarLinha("Imposto de Renda e Contribuição Social", dre.getDespesasTributarias(), false);
        adicionarLinha("Lucro Líquido", dre.calcularLucroLiquido(), true);

        adicionarEspacamento();
    }

    private void adicionarSecao(String titulo, boolean isNegrito) {
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Alinhamento à esquerda
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
        // Criar um JFileChooser para escolher o local de salvamento
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar PDF");
        fileChooser.setSelectedFile(new File("DRE.pdf")); // Nome padrão do arquivo

        // Filtrar apenas arquivos PDF
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));

        // Mostrar o diálogo e verificar se o usuário clicou em "Salvar"
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String caminhoArquivo = fileToSave.getAbsolutePath();

            // Adicionar a extensão .pdf se não estiver presente
            if (!caminhoArquivo.toLowerCase().endsWith(".pdf")) {
                caminhoArquivo += ".pdf";
            }

            // Criação do documento PDF
            try {
                PdfWriter writer = new PdfWriter(caminhoArquivo);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                // Adicionando título
                document.add(new Paragraph("Demonstração do Resultado do Exercício (DRE)").setFontSize(18).setBold());

                // Adicionando conteúdo
                document.add(new Paragraph("1. RECEITAS").setBold());
                document.add(new Paragraph("Receitas Operacionais: R$ " + df.format(dre.getReceitaOperacional())));
                document.add(new Paragraph("Receita de Vendas: R$ " + df.format(dre.getReceitaVendas())));
                document.add(new Paragraph("Receita de Serviços: R$ " + df.format(dre.getReceitaServicos())));
                document.add(new Paragraph("Receitas Não Operacionais: R$ " + df.format(dre.getReceitasNaoOperacionais())));
                document.add(new Paragraph("Receitas Financeiras: R$ " + df.format(dre.getReceitasFinanceiras())));
                document.add(new Paragraph("Outras Receitas: R$ " + df.format(dre.getOutrasReceitas())));
                document.add(new Paragraph("Receita Líquida: R$ " + df.format(dre.calcularReceitaLiquida())));
                document.add(new Paragraph("(-) Custo das Mercadorias/Serviços").setBold());
                document.add(new Paragraph("Custo das Mercadorias/Serviços: R$ " + df.format(dre.getDespesasCompras())));
                document.add(new Paragraph("Lucro Bruto: R$ " + df.format(dre.calcularLucroBruto())));

                document.add(new Paragraph("2. DESPESAS").setBold());
                document.add(new Paragraph("2.1 Despesas Operacionais").setBold());
                document.add(new Paragraph("Despesas com Compras: R$ " + df.format(dre.getDespesasCompras())));
                document.add(new Paragraph("Despesas com Vendas: R$ " + df.format(dre.getDespesasVendas())));
                document.add(new Paragraph("Despesas Administrativas: R$ " + df.format(dre.getDespesasAdministrativas())));
                document.add(new Paragraph("Despesas Operacionais Outras: R$ " + df.format(dre.getDespesasOperacionaisOutras())));
                document.add(new Paragraph("Resultado Operacional: R$ " + df.format(dre.calcularResultadoOperacional())));

                document.add(new Paragraph("2.2 Despesas Não Operacionais").setBold());
                document.add(new Paragraph("Despesas Não Operacionais: R$ " + df.format(dre.getDespesasNaoOperacionais())));

                document.add(new Paragraph("RESULTADO FINANCEIRO").setBold());
                document.add(new Paragraph("Receitas Financeiras: R$ " + df.format(dre.getReceitasFinanceiras())));
                document.add(new Paragraph("(-) Despesas Financeiras: R$ " + df.format(dre.getDespesasFinanceiras())));
                document.add(new Paragraph("Resultado Antes dos Impostos: R$ " + df.format(dre.calcularResultadoAntesImpostos())));

                document.add(new Paragraph("(-) Impostos sobre o Lucro").setBold());
                document.add(new Paragraph("Imposto de Renda e Contribuição Social: R$ " + df.format(dre.getDespesasTributarias())));
                document.add(new Paragraph("Lucro Líquido: R$ " + df.format(dre.calcularLucroLiquido())));

                // Fechar o documento
                document.close();

                JOptionPane.showMessageDialog(this, "PDF exportado com sucesso para: " + caminhoArquivo);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao exportar PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}