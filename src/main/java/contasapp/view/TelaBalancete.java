package contasapp.view;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import contasapp.model.lancamento.ContaContabil;
import contasapp.model.relatorios.Balancete;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;


public class TelaBalancete extends TelaRelatorio {

    public TelaBalancete(){
        Balancete balancete = new Balancete();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Balancete"));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Botão de exportar para PDF
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton exportarButton = new JButton("Exportar para PDF");
        exportarButton.addActionListener(e -> exportarParaPDF(balancete));
        painelTitulo.add(exportarButton);
        add(painelTitulo);

        adicionarEspacamento();

        for (ContaContabil conta : ContaContabil.values()) {
            adicionarSecao(conta.getCodigo() + " - " + conta.getNome());
            adicionarLinha("Crédito: ", balancete.buscarEClassifcarSaldoDaConta(conta, "credito"), false);
            adicionarLinha("Débito: ", balancete.buscarEClassifcarSaldoDaConta(conta, "debito"), false);
            adicionarEspacamento();

        }

    }


    // Método para exportar o conteúdo para PDF
    public void exportarParaPDF(Balancete balancete) {
        try {
            // Criar JFileChooser para escolher o local e nome do arquivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar PDF");
            fileChooser.setSelectedFile(new File("Balancete.pdf"));
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
            document.add(new Paragraph("Balancete").setFontSize(18).setBold());

            for (ContaContabil conta : ContaContabil.values()) {
                document.add(new Paragraph(conta.getCodigo() + " - " + conta.getNome())).setBold();
                document.add(new Paragraph("Crédito: R$ " + df.format(balancete.buscarEClassifcarSaldoDaConta(conta, "credito"))));
                document.add(new Paragraph("Débito: R$ " + df.format(balancete.buscarEClassifcarSaldoDaConta(conta, "debito"))));
            }

            document.close();
            JOptionPane.showMessageDialog(this, "Balancete exportada com sucesso!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar o arquivo PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
