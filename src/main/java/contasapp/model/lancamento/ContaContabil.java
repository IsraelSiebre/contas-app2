package contasapp.model.lancamento;

import java.util.Arrays;

public enum ContaContabil {
    // ATIVO - CIRCULANTE
    CAIXA("Caixa", "1.1.1", "Ativo", "Circulante"),
    BANCOS("Bancos", "1.1.2", "Ativo", "Circulante"),
    CONTAS_A_RECEBER("Contas a Receber", "1.1.3", "Ativo", "Circulante"),
    ESTOQUES("Estoques", "1.1.4", "Ativo", "Circulante"),
    ADIANTAMENTOS("Adiantamentos", "1.1.5", "Ativo", "Circulante"),
    APLICAÇÕES_FINANCEIRAS("Aplicações Financeiras", "1.1.6", "Ativo", "Circulante"),
    IMPOSTOS_A_RECUPERAR("Impostos a Recuperar", "1.1.7", "Ativo", "Circulante"),
    DESPESAS_PREVISTAS("Despesas Previstasa", "1.1.8", "Ativo", "Circulante"),

    // ATIVO - NÃO CIRCULANTE
    IMOBILIZADO("Imobilizado", "1.2.1", "Ativo", "Não Circulante"),
    INTANGIVEL("Intangível", "1.2.2", "Ativo", "Não Circulante"),
    APLICACOES_LONGO_PRAZO("Aplicações a Longo Prazo", "1.2.3", "Ativo", "Não Circulante"),
    INVESTIMENTOS("Investimentos", "1.2.4", "Ativo", "Não Circulante"),
    PROPRIEDADES_INVESTIDAS("Propriedades Investidas", "1.2.5", "Ativo", "Não Circulante"),
    ATIVO_DEFERIDO("Ativo Diferido", "1.2.6", "Ativo", "Não Circulante"),

    // PASSIVO - CIRCULANTE
    FORNECEDORES("Fornecedores", "2.1.1", "Passivo", "Circulante"),
    EMPRESTIMOS_BANCARIOS("Empréstimos Bancários", "2.2.1", "Passivo", "Circulante"),
    OBRIGACOES_FISCAIS("Obrigações Fiscais", "2.3.1", "Passivo", "Circulante"),
    PROVISOES("Provisões", "2.4.1", "Passivo", "Circulante"),
    DIVIDENDOS_A_PAGAR("Dividendos a Pagar", "2.5.1", "Passivo", "Circulante"),
    OBRIGACAO_TRIBUTARIA("Obrigação Tributária", "2.6.1", "Passivo", "Circulante"),
    SALARIOS_A_PAGAR("Salários a Pagar", "2.7.1", "Passivo", "Circulante"),
    DESPESAS_A_PAGAR("Despesas a Pagar", "2.8.1", "Passivo", "Circulante"),
    OUTRAS_OBRIGACOES("Outras Obrigações", "2.9.1", "Passivo", "Circulante"),

    // PASSIVO - NÃO CIRCULANTE
    OBRIGACAO_LONGO_PRAZO("Obrigação de Longo Prazo", "2.2.2", "Passivo", "Não Circulante"),
    FINANCIAMENTO("Financiamento de Longo Prazo", "2.2.3", "Passivo", "Não Circulante"),
    EMPRESTIMOS_LONGO_PRAZO("Empréstimos de Longo Prazo", "2.2.4", "Passivo", "Não Circulante"),

    // PATRIMÔNIO LÍQUIDO
    CAPITAL_SOCIAL("Capital Social", "3.1.1", "PL", null),
    LUCROS_ACUMULADOS("Lucros Acumulados", "3.2.1", "PL", null),
    RESERVAS("Reservas", "3.3.1", "PL", null),
    RESERVA_LEGAL("Reserva Legal", "3.3.2", "PL", null),
    RESERVA_ESTATUTARIA("Reserva Estatutária", "3.3.3", "PL", null),
    AJUSTES_A_VALOR_PATRIMONIAL("Ajustes a Valor Patrimonial", "3.4.1", "PL", null),
    PREVISAO_DIVIDENDOS("Previsão de Dividendos", "3.5.1", "PL", null),
    AJUSTES_CAMBIA("Ajustes de Câmbio", "3.6.1", "PL", null),

    // RECEITAS
    RECEITAS_OPERACIONAIS("Receitas Operacionais", "4.1.1", "Resultado", "Receita"),
    RECEITAS_NAO_OPERACIONAIS("Receitas Não Operacionais", "4.2.1", "Resultado", "Receita"),
    RECEITA_VENDA("Receita de Vendas", "4.1.2", "Resultado", "Receita"),
    RECEITA_SERVIÇOS("Receita de Serviços", "4.1.3", "Resultado", "Receita"),
    RECEITAS_FINANCEIRAS("Receitas Financeiras", "4.3.1", "Resultado", "Receita"),
    OUTRAS_RECEITAS("Outras Receitas", "4.4.1", "Resultado", "Receita"),

    // DESPESAS
    DESPESAS_OPERACIONAIS("Despesas Operacionais", "5.1.1", "Resultado", "Despesa"),
    DESPESAS_NAO_OPERACIONAIS("Despesas Não Operacionais", "5.2.1", "Resultado", "Despesa"),
    DESPESA_COMPRAS("Despesas com Compras", "5.1.2", "Resultado", "Despesa"),
    DESPESA_VENDAS("Despesas com Vendas", "5.1.3", "Resultado", "Despesa"),
    DESPESA_ADMINISTRATIVA("Despesas Administrativas", "5.1.4", "Resultado", "Despesa"),
    DESPESA_FINANCEIRA("Despesas Financeiras", "5.2.2", "Resultado", "Despesa"),
    DESPESAS_TRIBUTARIAS("Despesas Tributárias", "5.2.3", "Resultado", "Despesa"),
    DESPESA_OPERACIONAL_OUTRAS("Despesas Operacionais Outras", "5.2.4", "Resultado", "Despesa"),
    IMPREVISTO("Imprevisto", "5.2.5", "Resultado", "Despesa");


    private final String nome;
    private final String codigo;
    private final String tipo;
    private final String subgrupo;

    ContaContabil(String nome, String codigo, String tipo, String subgrupo) {
        this.nome = nome;
        this.codigo = codigo;
        this.tipo = tipo;
        this.subgrupo = subgrupo;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSubgrupo() {
        return subgrupo;
    }

    public static String[] getContas() {
        // Mapeia os valores do enum para o nome e código das contas concatenados
        return Arrays.stream(ContaContabil.values())
                .map(conta -> conta.getCodigo() + " - " + conta.getNome())  // Concatena o nome e o código da conta
                .toArray(String[]::new);      // Converte para array
    }
}
