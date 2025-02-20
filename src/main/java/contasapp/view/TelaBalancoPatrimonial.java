package contasapp.view;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import contasapp.relatorios.BalancoPatrimonial;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TelaBalancoPatrimonial extends JPanel {

    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public TelaBalancoPatrimonial() {
        BalancoPatrimonial balancoPatrimonial = new BalancoPatrimonial();
        balancoPatrimonial.calcularSaldos();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Balanço Patrimonial"));
        setBackground(Color.WHITE);

        // Configuração de margem interna
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Botão de exportar para PDF
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton exportarButton = new JButton("Exportar para PDF");
        exportarButton.addActionListener(e -> exportarParaPDF(balancoPatrimonial));
        painelTitulo.add(exportarButton);
        add(painelTitulo);

        adicionarEspacamento();

        // ATIVO CIRCULANTE
        adicionarSecao("1. ATIVO");
        adicionarSecao("1.1. CIRCULANTE");

        adicionarLinha("1.1.1 - Caixa", balancoPatrimonial.getCaixa(), false);
        adicionarLinha("1.1.2 - Bancos", balancoPatrimonial.getBancos(), false);
        adicionarLinha("1.1.3 - Contas a Receber", balancoPatrimonial.getContasAReceber(), false);
        adicionarLinha("1.1.4 - Estoques", balancoPatrimonial.getEstoques(), false);
        adicionarLinha("1.1.5 - Adiantamentos", balancoPatrimonial.getAdiantamentos(), false);
        adicionarLinha("1.1.6 - Aplicações Financeiras", balancoPatrimonial.getAplicacoesFinanceiras(), false);
        adicionarLinha("1.1.7 - Impostos a Recuperar", balancoPatrimonial.getImpostosARecuperar(), false);
        adicionarLinha("1.1.8 - Despesas Previstas", balancoPatrimonial.getDespesasPrevistas(), false);
        adicionarLinha("Total do Ativo Circulante", balancoPatrimonial.getAtivoCirculante(), true);

        adicionarEspacamento();

        // ATIVO NÃO CIRCULANTE
        adicionarSecao("1.2. NÃO CIRCULANTE");

        adicionarLinha("1.2.1 - Imobilizado", balancoPatrimonial.getImobilizado(), false);
        adicionarLinha("1.2.2 - Intangível", balancoPatrimonial.getIntangivel(), false);
        adicionarLinha("1.2.3 - Aplicações a Longo Prazo", balancoPatrimonial.getAplicacoesLongoPrazo(), false);
        adicionarLinha("1.2.4 - Investimentos", balancoPatrimonial.getInvestimentos(), false);
        adicionarLinha("1.2.5 - Propriedades Investidas", balancoPatrimonial.getPropriedadesInvestidas(), false);
        adicionarLinha("1.2.6 - Ativo Diferido", balancoPatrimonial.getAtivoDiferido(), false);
        adicionarLinha("Total do Ativo Não Circulante", balancoPatrimonial.getAtivoNaoCirculante(), true);

        adicionarEspacamento();

        adicionarLinha("Total do Ativo", balancoPatrimonial.getAtivoTotal(), true);

        adicionarEspacamento();

        // PASSIVO CIRCULANTE
        adicionarSecao("2. PASSIVO");
        adicionarSecao("2.1. CIRCULANTE");

        adicionarLinha("2.1.1 - Fornecedores", balancoPatrimonial.getFornecedores(), false);
        adicionarLinha("2.2.1 - Empréstimos Bancários", balancoPatrimonial.getEmprestimosBancarios(), false);
        adicionarLinha("2.3.1 - Obrigações Fiscais", balancoPatrimonial.getObrigacoesFiscais(), false);
        adicionarLinha("2.4.1 - Provisões", balancoPatrimonial.getProvisoes(), false);
        adicionarLinha("2.5.1 - Dividendos a Pagar", balancoPatrimonial.getDividendosAPagar(), false);
        adicionarLinha("2.6.1 - Obrigação Tributária", balancoPatrimonial.getObrigacaoTributaria(), false);
        adicionarLinha("2.7.1 - Salários a Pagar", balancoPatrimonial.getSalariosAPagar(), false);
        adicionarLinha("2.8.1 - Despesas a Pagar", balancoPatrimonial.getDespesasAPagar(), false);
        adicionarLinha("2.9.1 - Outras Obrigações", balancoPatrimonial.getOutrasObrigacoes(), false);
        adicionarLinha("Total do Passivo Circulante", balancoPatrimonial.getPassivoCirculante(), true);

        adicionarEspacamento();

        // PASSIVO NÃO CIRCULANTE
        adicionarSecao("2.2. NÃO CIRCULANTE");

        adicionarLinha("2.2.2 - Obrigação de Longo Prazo", balancoPatrimonial.getObrigacaoLongoPrazo(), false);
        adicionarLinha("2.2.3 - Financiamento de Longo Prazo", balancoPatrimonial.getFinanciamento(), false);
        adicionarLinha("2.2.4 - Empréstimos de Longo Prazo", balancoPatrimonial.getEmprestimosLongoPrazo(), false);
        adicionarLinha("Total do Passivo Não Circulante", balancoPatrimonial.getPassivoNaoCirculante(), true);

        adicionarEspacamento();

        adicionarLinha("Total do Passivo", balancoPatrimonial.getPassivoTotal(), true);

        adicionarEspacamento();

        // PATRIMÔNIO LÍQUIDO
        adicionarSecao("3. PATRIMÔNIO LÍQUIDO");

        adicionarLinha("3.1.1 - Capital Social", balancoPatrimonial.getCapitalSocial(), false);
        adicionarLinha("3.2.1 - Lucros Acumulados", balancoPatrimonial.getLucrosAcumulados(), false);
        adicionarLinha("3.3.1 - Reservas", balancoPatrimonial.getReservas(), false);
        adicionarLinha("3.3.2 - Reserva Legal", balancoPatrimonial.getReservaLegal(), false);
        adicionarLinha("3.3.3 - Reserva Estatutária", balancoPatrimonial.getReservaEstatutaria(), false);
        adicionarLinha("3.4.1 - Ajustes a Valor Patrimonial", balancoPatrimonial.getAjustesValorPatrimonial(), false);
        adicionarLinha("3.5.1 - Previsão de Dividendos", balancoPatrimonial.getPrevisaoDividendos(), false);
        adicionarLinha("3.6.1 - Ajustes de Câmbio", balancoPatrimonial.getAjustesCambio(), false);
        adicionarLinha("Total do Patrimônio Líquido", balancoPatrimonial.getPatrimonioLiquido(), true);

        adicionarEspacamento();

        adicionarSecao("Resumo");
        adicionarLinha("Total do Ativo", balancoPatrimonial.getAtivoTotal(), true);
        adicionarLinha("Total do Passivo + Patrimônio Líquido", balancoPatrimonial.getPassivoEPatrimonioLiquido(), true);

        adicionarEspacamento();
    }

    private void adicionarSecao(String titulo) {
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
    private void exportarParaPDF(BalancoPatrimonial balanco) {
        try {
            // Criar JFileChooser para escolher o local e nome do arquivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar PDF");
            fileChooser.setSelectedFile(new File("BalancoPatrimonial.pdf"));
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
            document.add(new Paragraph("Balanço Patrimonial").setFontSize(18).setBold());

            // Adicionando Ativo
            document.add(new Paragraph("1. ATIVO").setBold());
            document.add(new Paragraph("1.1. CIRCULANTE").setBold());
            document.add(new Paragraph("Caixa: R$ " + df.format(balanco.getCaixa())));
            document.add(new Paragraph("Bancos: R$ " + df.format(balanco.getBancos())));
            document.add(new Paragraph("Contas a Receber: R$ " + df.format(balanco.getContasAReceber())));
            document.add(new Paragraph("Estoques: R$ " + df.format(balanco.getEstoques())));
            document.add(new Paragraph("Adiantamentos: R$ " + df.format(balanco.getAdiantamentos())));
            document.add(new Paragraph("Aplicações Financeiras: R$ " + df.format(balanco.getAplicacoesFinanceiras())));
            document.add(new Paragraph("Impostos a Recuperar: R$ " + df.format(balanco.getImpostosARecuperar())));
            document.add(new Paragraph("Despesas Previstas: R$ " + df.format(balanco.getDespesasPrevistas())));
            document.add(new Paragraph("Total do Ativo Circulante: R$ " + df.format(balanco.getAtivoCirculante())).setBold());

            document.add(new Paragraph("1.2. NÃO CIRCULANTE").setBold());
            document.add(new Paragraph("Imobilizado: R$ " + df.format(balanco.getImobilizado())));
            document.add(new Paragraph("Intangível: R$ " + df.format(balanco.getIntangivel())));
            document.add(new Paragraph("Aplicações a Longo Prazo: R$ " + df.format(balanco.getAplicacoesLongoPrazo())));
            document.add(new Paragraph("Investimentos: R$ " + df.format(balanco.getInvestimentos())));
            document.add(new Paragraph("Propriedades Investidas: R$ " + df.format(balanco.getPropriedadesInvestidas())));
            document.add(new Paragraph("Ativo Diferido: R$ " + df.format(balanco.getAtivoDiferido())));
            document.add(new Paragraph("Total do Ativo Não Circulante: R$ " + df.format(balanco.getAtivoNaoCirculante())).setBold());

            document.add(new Paragraph("Total do Ativo: R$ " + df.format(balanco.getAtivoTotal())).setBold());

            // Adicionando Passivo
            document.add(new Paragraph("2. PASSIVO").setBold());
            document.add(new Paragraph("2.1. CIRCULANTE").setBold());
            document.add(new Paragraph("Fornecedores: R$ " + df.format(balanco.getFornecedores())));
            document.add(new Paragraph("Empréstimos Bancários: R$ " + df.format(balanco.getEmprestimosBancarios())));
            document.add(new Paragraph("Obrigações Fiscais: R$ " + df.format(balanco.getObrigacoesFiscais())));
            document.add(new Paragraph("Provisões: R$ " + df.format(balanco.getProvisoes())));
            document.add(new Paragraph("Dividendos a Pagar: R$ " + df.format(balanco.getDividendosAPagar())));
            document.add(new Paragraph("Obrigação Tributária: R$ " + df.format(balanco.getObrigacaoTributaria())));
            document.add(new Paragraph("Salários a Pagar: R$ " + df.format(balanco.getSalariosAPagar())));
            document.add(new Paragraph("Despesas a Pagar: R$ " + df.format(balanco.getDespesasAPagar())));
            document.add(new Paragraph("Outras Obrigações: R$ " + df.format(balanco.getOutrasObrigacoes())));
            document.add(new Paragraph("Total do Passivo Circulante: R$ " + df.format(balanco.getPassivoCirculante())).setBold());

            // Adicionando Patrimônio Líquido
            document.add(new Paragraph("3. PATRIMÔNIO LÍQUIDO").setBold());
            document.add(new Paragraph("Capital Social: R$ " + df.format(balanco.getCapitalSocial())));
            document.add(new Paragraph("Lucros Acumulados: R$ " + df.format(balanco.getLucrosAcumulados())));
            document.add(new Paragraph("Reservas: R$ " + df.format(balanco.getReservas())));
            document.add(new Paragraph("Reserva Legal: R$ " + df.format(balanco.getReservaLegal())));
            document.add(new Paragraph("Reserva Estatutária: R$ " + df.format(balanco.getReservaEstatutaria())));
            document.add(new Paragraph("Ajustes a Valor Patrimonial: R$ " + df.format(balanco.getAjustesValorPatrimonial())));
            document.add(new Paragraph("Previsão de Dividendos: R$ " + df.format(balanco.getPrevisaoDividendos())));
            document.add(new Paragraph("Ajustes de Câmbio: R$ " + df.format(balanco.getAjustesCambio())));
            document.add(new Paragraph("Total do Patrimônio Líquido: R$ " + df.format(balanco.getPatrimonioLiquido())).setBold());

            document.close();
            JOptionPane.showMessageDialog(this, "Balanço Patrimonial exportado com sucesso!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar o arquivo PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


}