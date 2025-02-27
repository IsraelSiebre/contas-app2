package contasapp.view;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import contasapp.model.relatorios.Indices;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TelaIndices extends TelaRelatorio{

    protected final DecimalFormat df = new DecimalFormat("#,##0.00");

    public TelaIndices() {
        Indices indices = new Indices();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Indices"));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Botão de exportar para PDF
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton exportarButton = new JButton("Exportar para PDF");
        exportarButton.addActionListener(e -> exportarParaPDF(indices));
        painelTitulo.add(exportarButton);
        add(painelTitulo);

        adicionarEspacamento();

        adicionarSecao("Índices de Liquidez (Capacidade de pagar obrigações)");
        adicionarLinha("Liquidez Imediata = Disponível / Passivo Circulante", indices.calcularLiquidezImediata(), false);
        adicionarLinha("Liquidez Seca = (Ativo Circulante - Estoques) / Passivo Circulante", indices.calcularLiquidezSeca(), false);
        adicionarLinha("Liquidez Corrente = Ativo Circulante / Passivo Circulante", indices.calcularLiquidezCorrente(), false);
        adicionarLinha("Liquidez Geral = (Ativo Circulante + Realizável a Longo Prazo) / (Passivo Circulante + Exigível a Longo Prazo)", indices.calcularLiquidezGeral(), false);

        adicionarEspacamento();

        adicionarSecao("Índices de Estrutura de Capital (Grau de endividamento)");
        adicionarLinha("Endividamento Geral = Passivo Total / Ativo Total", indices.calcularEndividamentoGeral(), false);
        adicionarLinha("Composição do Endividamento = Passivo Circulante / Passivo Total", indices.calcularComposicaoEndividamento(), false);
        adicionarLinha("Imobilização do Patrimônio Líquido = Ativo Não Circulante / Patrimônio Líquido", indices.calcularImobilizacaoPatrimonioLiquido(), false);
        adicionarLinha("Imobilização dos Recursos Não Correntes = Ativo Não Circulante / (PL + Exigível a Longo Prazo)", indices.calcularImobilizacaoRecursosNaoCorrentes(), false);

        adicionarEspacamento();

        adicionarSecao("Índices de Rentabilidade (Lucro e retorno sobre investimentos)");
        adicionarLinha("Margem Bruta = Lucro Bruto / Receita Líquida", indices.calcularMargemBruta(), false);
        adicionarLinha("Margem Operacional = Lucro Operacional / Receita Líquida", indices.calcularMargemOperacional(), false);
        adicionarLinha("Margem Líquida = Lucro Líquido / Receita Líquida", indices.calcularMargemLiquida(), false);
        adicionarLinha("Giro do Ativo = Receita Líquida / Ativo Total", indices.calcularGiroAtivo(), false);
        adicionarLinha("ROA (Return on Assets) = Lucro Líquido / Ativo Total", indices.calcularROA(), false);
        adicionarLinha("ROE (Return on Equity) = Lucro Líquido / Patrimônio Líquido", indices.calcularROE(), false);
        adicionarLinha("ROI (Return on Investment) = Lucro Operacional / (Patrimônio Líquido + Empréstimos)", indices.calcularROI(), false);



        adicionarEspacamento();

        adicionarSecao("Índices de Estrutura e Solvência");
        adicionarLinha("Autonomia Financeira = Patrimônio Líquido / Ativo Total", indices.calcularAutonomiaFinanceira(), false);
        adicionarLinha("Garantia de Capital Próprio = Patrimônio Líquido / Exigível Total", indices.calcularGarantiaCapitalProprio(), false);

        adicionarEspacamento();
    }

    @Override
    protected void adicionarLinha(String descricao, BigDecimal valor, boolean isNegrito) {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setBackground(Color.WHITE);
        linha.setBorder(BorderFactory.createEmptyBorder(2, isNegrito ? 10 : 30, 2, 10));

        JLabel labelDescricao = new JLabel(descricao);
        labelDescricao.setFont(new Font("Arial", isNegrito ? Font.BOLD : Font.PLAIN, 12));

        JLabel labelValor = new JLabel(df.format(valor));
        labelValor.setFont(new Font("Arial", Font.PLAIN, 12));

        linha.add(labelDescricao, BorderLayout.WEST);
        linha.add(labelValor, BorderLayout.EAST);

        add(linha);
    }

    // Método para exportar o conteúdo para PDF
    public void exportarParaPDF(Indices indices) {
        try {
            // Criar JFileChooser para escolher o local e nome do arquivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar PDF");
            fileChooser.setSelectedFile(new File("Indices.pdf"));
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
            document.add(new Paragraph("Indices").setFontSize(18).setBold());

            document.add(new Paragraph("Índices de Liquidez (Capacidade de pagar obrigações)").setBold());
            document.add(new Paragraph("Liquidez Imediata = Disponível / Passivo Circulante  " + df.format(indices.calcularLiquidezImediata())));
            document.add(new Paragraph("Liquidez Seca = (Ativo Circulante - Estoques) / Passivo Circulante  "  + df.format(indices.calcularLiquidezSeca())));
            document.add(new Paragraph("Liquidez Corrente = Ativo Circulante / Passivo Circulante  " + df.format(indices.calcularLiquidezCorrente())));
            document.add(new Paragraph("Liquidez Geral = (Ativo Circulante + Realizável a Longo Prazo) / (Passivo Circulante + Exigível a Longo Prazo)  " + df.format(indices.calcularLiquidezGeral())));

            document.add(new Paragraph("Índices de Estrutura de Capital (Grau de endividamento)").setBold());
            document.add(new Paragraph("Endividamento Geral = Passivo Total / Ativo Total  " + df.format(indices.calcularEndividamentoGeral())));
            document.add(new Paragraph("Composição do Endividamento = Passivo Circulante / Passivo Total  " + df.format(indices.calcularComposicaoEndividamento())));
            document.add(new Paragraph("Imobilização do Patrimônio Líquido = Ativo Não Circulante / Patrimônio Líquido  " + df.format(indices.calcularImobilizacaoPatrimonioLiquido())));
            document.add(new Paragraph("Imobilização dos Recursos Não Correntes = Ativo Não Circulante / (PL + Exigível a Longo Prazo)  " + df.format(indices.calcularImobilizacaoRecursosNaoCorrentes())));

            document.add(new Paragraph("Índices de Rentabilidade (Lucro e retorno sobre investimentos)").setBold());
            document.add(new Paragraph("Margem Bruta = Lucro Bruto / Receita Líquida  " + df.format(indices.calcularMargemBruta())));
            document.add(new Paragraph("Margem Operacional = Lucro Operacional / Receita Líquida  " + df.format(indices.calcularMargemOperacional())));
            document.add(new Paragraph("Margem Líquida = Lucro Líquido / Receita Líquida  " + df.format(indices.calcularMargemLiquida())));
            document.add(new Paragraph("Giro do Ativo = Receita Líquida / Ativo Total  " + df.format(indices.calcularGiroAtivo())));
            document.add(new Paragraph("ROA (Return on Assets) = Lucro Líquido / Ativo Total  " + df.format(indices.calcularROA())));
            document.add(new Paragraph("ROE (Return on Equity) = Lucro Líquido / Patrimônio Líquido  " + df.format(indices.calcularROE())));
            document.add(new Paragraph("ROI (Return on Investment) = Lucro Operacional / (Patrimônio Líquido + Empréstimos)  " + df.format(indices.calcularROI())));

            document.add(new Paragraph("Índices de Estrutura e Solvência  ").setBold());
            document.add(new Paragraph("Autonomia Financeira = Patrimônio Líquido / Ativo Total  " + df.format(indices.calcularAutonomiaFinanceira())));
            document.add(new Paragraph("Garantia de Capital Próprio = Patrimônio Líquido / Exigível Total  " + df.format(indices.calcularGarantiaCapitalProprio())));

            document.close();
            JOptionPane.showMessageDialog(this, "Indices exportados com sucesso!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar o arquivo PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
