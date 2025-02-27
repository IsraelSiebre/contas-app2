package contasapp.model.relatorios;

import contasapp.database.DataBaseManager;
import contasapp.model.lancamento.ContaContabil;

import java.math.BigDecimal;

public abstract class Relatorio {

    // ATIVO - CIRCULANTE
    protected BigDecimal caixa;
    protected BigDecimal bancos;
    protected BigDecimal contasAReceber;
    protected BigDecimal estoques;
    protected BigDecimal adiantamentos;
    protected BigDecimal aplicacoesFinanceiras;
    protected BigDecimal impostosARecuperar;
    protected BigDecimal despesasPrevistas;

    // ATIVO - NÃO CIRCULANTE
    protected BigDecimal imobilizado;
    protected BigDecimal intangivel;
    protected BigDecimal aplicacoesLongoPrazo;
    protected BigDecimal investimentos;
    protected BigDecimal propriedadesInvestidas;
    protected BigDecimal ativoDiferido;

    // PASSIVO - CIRCULANTE
    protected BigDecimal fornecedores;
    protected BigDecimal emprestimosBancarios;
    protected BigDecimal obrigacoesFiscais;
    protected BigDecimal provisoes;
    protected BigDecimal dividendosAPagar;
    protected BigDecimal obrigacaoTributaria;
    protected BigDecimal salariosAPagar;
    protected BigDecimal despesasAPagar;
    protected BigDecimal outrasObrigacoes;

    // PASSIVO - NÃO CIRCULANTE
    protected BigDecimal obrigacaoLongoPrazo;
    protected BigDecimal financiamento;
    protected BigDecimal emprestimosLongoPrazo;

    // PATRIMÔNIO LÍQUIDO
    protected BigDecimal capitalSocial;
    protected BigDecimal lucrosAcumulados;
    protected BigDecimal reservas;
    protected BigDecimal reservaLegal;
    protected BigDecimal reservaEstatutaria;
    protected BigDecimal ajustesValorPatrimonial;
    protected BigDecimal previsaoDividendos;
    protected BigDecimal ajustesCambio;

    // RECEITAS
    protected BigDecimal receitaOperacional;
    protected BigDecimal receitasNaoOperacionais;
    protected BigDecimal receitaVendas;
    protected BigDecimal receitaServicos;
    protected BigDecimal receitasFinanceiras;
    protected BigDecimal outrasReceitas;

    // DESPESAS OPERACIONAIS
    protected BigDecimal despesasOperacionais;
    protected BigDecimal despesasNaoOperacionais;
    protected BigDecimal despesasCompras;
    protected BigDecimal despesasVendas;
    protected BigDecimal despesasAdministrativas;
    protected BigDecimal despesasFinanceiras;
    protected BigDecimal despesasTributarias;
    protected BigDecimal despesasOperacionaisOutras;
    protected BigDecimal imprevisto;


    protected void buscarSaldos(){
        DataBaseManager dbManager = new DataBaseManager();

        // ATIVO - CIRCULANTE
        this.caixa = dbManager.buscarTotalDaConta(ContaContabil.CAIXA);
        this.bancos = dbManager.buscarTotalDaConta(ContaContabil.BANCOS);
        this.contasAReceber = dbManager.buscarTotalDaConta(ContaContabil.CONTAS_A_RECEBER);
        this.estoques = dbManager.buscarTotalDaConta(ContaContabil.ESTOQUES);
        this.adiantamentos = dbManager.buscarTotalDaConta(ContaContabil.ADIANTAMENTOS);
        this.aplicacoesFinanceiras = dbManager.buscarTotalDaConta(ContaContabil.APLICACOES_FINANCEIRAS);
        this.impostosARecuperar = dbManager.buscarTotalDaConta(ContaContabil.IMPOSTOS_A_RECUPERAR);
        this.despesasPrevistas = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_PREVISTAS);

        // ATIVO - NÃO CIRCULANTE
        this.imobilizado = dbManager.buscarTotalDaConta(ContaContabil.IMOBILIZADO);
        this.intangivel = dbManager.buscarTotalDaConta(ContaContabil.INTANGIVEL);
        this.aplicacoesLongoPrazo = dbManager.buscarTotalDaConta(ContaContabil.APLICACOES_A_LONGO_PRAZO);
        this.investimentos = dbManager.buscarTotalDaConta(ContaContabil.INVESTIMENTOS);
        this.propriedadesInvestidas = dbManager.buscarTotalDaConta(ContaContabil.PROPRIEDADES_INVESTIDAS);
        this.ativoDiferido = dbManager.buscarTotalDaConta(ContaContabil.ATIVO_DIFERIDO);

        // PASSIVO - CIRCULANTE
        this.fornecedores = dbManager.buscarTotalDaConta(ContaContabil.FORNECEDORES);
        this.emprestimosBancarios = dbManager.buscarTotalDaConta(ContaContabil.EMPRESTIMOS_BANCARIOS);
        this.obrigacoesFiscais = dbManager.buscarTotalDaConta(ContaContabil.OBRIGACOES_FISCAIS);
        this.provisoes = dbManager.buscarTotalDaConta(ContaContabil.PROVISOES);
        this.dividendosAPagar = dbManager.buscarTotalDaConta(ContaContabil.DIVIDENDOS_A_PAGAR);
        this.obrigacaoTributaria = dbManager.buscarTotalDaConta(ContaContabil.OBRIGACAO_TRIBUTARIA);
        this.salariosAPagar = dbManager.buscarTotalDaConta(ContaContabil.SALARIOS_A_PAGAR);
        this.despesasAPagar = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_A_PAGAR);
        this.outrasObrigacoes = dbManager.buscarTotalDaConta(ContaContabil.OUTRAS_OBRIGACOES);

        // PASSIVO - NÃO CIRCULANTE
        this.obrigacaoLongoPrazo = dbManager.buscarTotalDaConta(ContaContabil.OBRIGACAO_DE_LONGO_PRAZO);
        this.financiamento = dbManager.buscarTotalDaConta(ContaContabil.FINANCIAMENTO_DE_LONGO_PRAZO);
        this.emprestimosLongoPrazo = dbManager.buscarTotalDaConta(ContaContabil.EMPRESTIMOS_DE_LONGO_PRAZO);

        // PATRIMÔNIO LÍQUIDO
        this.capitalSocial = dbManager.buscarTotalDaConta(ContaContabil.CAPITAL_SOCIAL);
        this.lucrosAcumulados = dbManager.buscarTotalDaConta(ContaContabil.LUCROS_ACUMULADOS);
        this.reservas = dbManager.buscarTotalDaConta(ContaContabil.RESERVAS);
        this.reservaLegal = dbManager.buscarTotalDaConta(ContaContabil.RESERVA_LEGAL);
        this.reservaEstatutaria = dbManager.buscarTotalDaConta(ContaContabil.RESERVA_ESTATUTARIA);
        this.ajustesValorPatrimonial = dbManager.buscarTotalDaConta(ContaContabil.AJUSTES_A_VALOR_PATRIMONIAL);
        this.previsaoDividendos = dbManager.buscarTotalDaConta(ContaContabil.PREVISAO_DE_DIVIDENDOS);
        this.ajustesCambio = dbManager.buscarTotalDaConta(ContaContabil.AJUSTES_DE_CAMBIO);

        // RECEITAS
        this.receitaOperacional = dbManager.buscarTotalDaConta(ContaContabil.RECEITAS_OPERACIONAIS);
        this.receitasNaoOperacionais = dbManager.buscarTotalDaConta(ContaContabil.RECEITAS_NAO_OPERACIONAIS);
        this.receitaVendas = dbManager.buscarTotalDaConta(ContaContabil.RECEITA_DE_VENDAS);
        this.receitaServicos = dbManager.buscarTotalDaConta(ContaContabil.RECEITA_DE_SERVICOS);
        this.receitasFinanceiras = dbManager.buscarTotalDaConta(ContaContabil.RECEITAS_FINANCEIRAS);
        this.outrasReceitas = dbManager.buscarTotalDaConta(ContaContabil.OUTRAS_RECEITAS);

        // DESPESAS
        this.despesasOperacionais = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_OPERACIONAIS);
        this.despesasNaoOperacionais = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_NAO_OPERACIONAIS);
        this.despesasCompras = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_COM_COMPRAS);
        this.despesasVendas = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_COM_VENDAS);
        this.despesasAdministrativas = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_ADMINISTRATIVAS);
        this.despesasFinanceiras = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_FINANCEIRAS);
        this.despesasTributarias = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_TRIBUTARIAS);
        this.despesasOperacionaisOutras = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_OPERACIONAIS_OUTRAS);
        this.imprevisto = dbManager.buscarTotalDaConta(ContaContabil.IMPREVISTO);

    }

    public BigDecimal getCaixa() {
        return caixa;
    }

    public BigDecimal getBancos() {
        return bancos;
    }

    public BigDecimal getContasAReceber() {
        return contasAReceber;
    }

    public BigDecimal getEstoques() {
        return estoques;
    }

    public BigDecimal getAdiantamentos() {
        return adiantamentos;
    }

    public BigDecimal getAplicacoesFinanceiras() {
        return aplicacoesFinanceiras;
    }

    public BigDecimal getImpostosARecuperar() {
        return impostosARecuperar;
    }

    public BigDecimal getDespesasPrevistas() {
        return despesasPrevistas;
    }

    public BigDecimal getImobilizado() {
        return imobilizado;
    }

    public BigDecimal getIntangivel() {
        return intangivel;
    }

    public BigDecimal getAplicacoesLongoPrazo() {
        return aplicacoesLongoPrazo;
    }

    public BigDecimal getInvestimentos() {
        return investimentos;
    }

    public BigDecimal getPropriedadesInvestidas() {
        return propriedadesInvestidas;
    }

    public BigDecimal getAtivoDiferido() {
        return ativoDiferido;
    }

    public BigDecimal getFornecedores() {
        return fornecedores;
    }

    public BigDecimal getEmprestimosBancarios() {
        return emprestimosBancarios;
    }

    public BigDecimal getObrigacoesFiscais() {
        return obrigacoesFiscais;
    }

    public BigDecimal getProvisoes() {
        return provisoes;
    }

    public BigDecimal getDividendosAPagar() {
        return dividendosAPagar;
    }

    public BigDecimal getObrigacaoTributaria() {
        return obrigacaoTributaria;
    }

    public BigDecimal getSalariosAPagar() {
        return salariosAPagar;
    }

    public BigDecimal getDespesasAPagar() {
        return despesasAPagar;
    }

    public BigDecimal getOutrasObrigacoes() {
        return outrasObrigacoes;
    }

    public BigDecimal getObrigacaoLongoPrazo() {
        return obrigacaoLongoPrazo;
    }

    public BigDecimal getFinanciamento() {
        return financiamento;
    }

    public BigDecimal getEmprestimosLongoPrazo() {
        return emprestimosLongoPrazo;
    }

    public BigDecimal getCapitalSocial() {
        return capitalSocial;
    }

    public BigDecimal getLucrosAcumulados() {
        return lucrosAcumulados;
    }

    public BigDecimal getReservas() {
        return reservas;
    }

    public BigDecimal getReservaLegal() {
        return reservaLegal;
    }

    public BigDecimal getReservaEstatutaria() {
        return reservaEstatutaria;
    }

    public BigDecimal getAjustesValorPatrimonial() {
        return ajustesValorPatrimonial;
    }

    public BigDecimal getPrevisaoDividendos() {
        return previsaoDividendos;
    }

    public BigDecimal getAjustesCambio() {
        return ajustesCambio;
    }

    public BigDecimal getReceitaOperacional() {
        return receitaOperacional;
    }

    public BigDecimal getReceitasNaoOperacionais() {
        return receitasNaoOperacionais;
    }

    public BigDecimal getReceitaVendas() {
        return receitaVendas;
    }

    public BigDecimal getReceitaServicos() {
        return receitaServicos;
    }

    public BigDecimal getReceitasFinanceiras() {
        return receitasFinanceiras;
    }

    public BigDecimal getOutrasReceitas() {
        return outrasReceitas;
    }

    public BigDecimal getDespesasOperacionais() {
        return despesasOperacionais;
    }

    public BigDecimal getDespesasNaoOperacionais() {
        return despesasNaoOperacionais;
    }

    public BigDecimal getDespesasCompras() {
        return despesasCompras;
    }

    public BigDecimal getDespesasVendas() {
        return despesasVendas;
    }

    public BigDecimal getDespesasAdministrativas() {
        return despesasAdministrativas;
    }

    public BigDecimal getDespesasFinanceiras() {
        return despesasFinanceiras;
    }

    public BigDecimal getDespesasTributarias() {
        return despesasTributarias;
    }

    public BigDecimal getDespesasOperacionaisOutras() {
        return despesasOperacionaisOutras;
    }

    public BigDecimal getImprevisto() {
        return imprevisto;
    }
}
