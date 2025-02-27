package contasapp.view;

import contasapp.database.DataBaseManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class MenuPrincipal extends JFrame {
    private final JPanel painelPrincipal;
    private Image backgroundImage;

    private static final int WIDTH = 900;
    private static final int HEIGHT = 550;

    public MenuPrincipal() {
        setTitle("Sistema Contábil");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Carregar a imagem de fundo
        carregarImagemDeFundo();

        // Criar a barra de menu
        JMenuBar menuBar = criarMenuBar();

        // Definir a barra de menu no JFrame
        setJMenuBar(menuBar);

        // Criar painel principal para exibição das telas
        painelPrincipal = criarPainelPrincipal();

        // Encapsular o painel principal em um JScrollPane
        JScrollPane scrollPane = new JScrollPane(painelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void carregarImagemDeFundo() {
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/background.png"));
        } catch (IOException e) {
            System.err.println("Erro ao carregar a imagem de fundo: " + e.getMessage());
        }
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Criar menus principais
        JMenu menuArquivo = new JMenu("Opções");
        JMenu menuMovimentacoes = new JMenu("Lançamentos");
        JMenu menuRelatorios = new JMenu("Relatórios");

        // Itens do menu "Arquivo"
        adicionarItensMenuArquivo(menuArquivo);

        // Itens do menu "Movimentações"
        adicionarItensMenuMovimentacoes(menuMovimentacoes);

        // Itens do menu "Relatórios"
        adicionarItensMenuRelatorios(menuRelatorios);

        // Adicionar menus à barra de menu
        menuBar.add(menuArquivo);
        menuBar.add(menuMovimentacoes);
        menuBar.add(menuRelatorios);

        return menuBar;
    }

    private void adicionarItensMenuArquivo(JMenu menu) {
        JMenuItem itemFazerBackup = new JMenuItem("Fazer Backup");
        JMenuItem itemCarregarBackup = new JMenuItem("Carregar Backup");
        JMenuItem itemSair = new JMenuItem("Sair");

        itemFazerBackup.addActionListener(_ -> new DataBaseManager().fazerBackup("contasapp.db"));
        itemCarregarBackup.addActionListener(_ -> new DataBaseManager().carregarBackup());
        itemSair.addActionListener(_ -> System.exit(0));

        menu.add(itemFazerBackup);
        menu.add(itemCarregarBackup);
        menu.add(itemSair);
    }

    private void adicionarItensMenuMovimentacoes(JMenu menu) {
        JMenuItem itemCadastrarLancamento = new JMenuItem("Registrar Lançamento");
        JMenuItem itemEditarLancamento = new JMenuItem("Editar Lançamento");

        itemCadastrarLancamento.addActionListener(_ -> abrirTela(new TelaRegistrarLancamento()));
        itemEditarLancamento.addActionListener(_ -> abrirTela(new TelaEditarLancamento()));

        menu.add(itemCadastrarLancamento);
        menu.add(itemEditarLancamento);
    }

    private void adicionarItensMenuRelatorios(JMenu menu) {
        JMenuItem itemRelatorioDRE = new JMenuItem("DRE");
        JMenuItem itemRelatorioBalancoPatrimonial = new JMenuItem("Balanço Patrimonial");
        JMenuItem itemRelatorioBalancete = new JMenuItem("Balancete");
        JMenuItem itemRelatorioIndices = new JMenuItem("Índices");

        itemRelatorioDRE.addActionListener(_ -> abrirTela(new TelaDRE()));
        itemRelatorioBalancoPatrimonial.addActionListener(_ -> abrirTela(new TelaBalancoPatrimonial()));
        itemRelatorioBalancete.addActionListener(_ -> abrirTela(new TelaBalancete()));
        itemRelatorioIndices.addActionListener(_ -> abrirTela(new TelaIndices()));

        menu.add(itemRelatorioDRE);
        menu.add(itemRelatorioBalancoPatrimonial);
        menu.add(itemRelatorioBalancete);
        menu.add(itemRelatorioIndices);
    }

    private JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Obtém a largura e altura da imagem
                    int imgWidth = backgroundImage.getWidth(null);
                    int imgHeight = backgroundImage.getHeight(null);

                    // Calcula a posição para centralizar a imagem no painel
                    int x = (getWidth() - imgWidth) / 2;
                    int y = (getHeight() - imgHeight) / 2;

                    // Desenha a imagem no centro sem esticar
                    g.drawImage(backgroundImage, x, y, this);
                }
            }
        };
        painel.setLayout(new CardLayout());
        return painel;
    }


    // Método para abrir diferentes telas no painel principal
    private void abrirTela(JPanel novaTela) {
        painelPrincipal.removeAll();
        painelPrincipal.add(novaTela);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}