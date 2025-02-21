package contasapp.relatorios;

import contasapp.database.DataBaseManager;
import contasapp.model.lancamento.ContaContabil;
import java.math.BigDecimal;
import java.util.Map;

public class BalancoPatrimonial {
    private BigDecimal caixa;
    private BigDecimal bancos;
    private BigDecimal contasAReceber;
    private BigDecimal estoques;
    private BigDecimal adiantamentos;
    private BigDecimal aplicacoesFinanceiras;
    private BigDecimal impostosARecuperar;
    private BigDecimal despesasPrevistas;

    private BigDecimal imobilizado;
    private BigDecimal intangivel;
    private BigDecimal aplicacoesLongoPrazo;
    private BigDecimal investimentos;
    private BigDecimal propriedadesInvestidas;
    private BigDecimal ativoDiferido;

    private BigDecimal fornecedores;
    private BigDecimal emprestimosBancarios;
    private BigDecimal obrigacoesFiscais;
    private BigDecimal provisoes;
    private BigDecimal dividendosAPagar;
    private BigDecimal obrigacaoTributaria;
    private BigDecimal salariosAPagar;
    private BigDecimal despesasAPagar;
    private BigDecimal outrasObrigacoes;

    private BigDecimal obrigacaoLongoPrazo;
    private BigDecimal financiamento;
    private BigDecimal emprestimosLongoPrazo;

    private BigDecimal capitalSocial;
    private BigDecimal lucrosAcumulados;
    private BigDecimal reservas;
    private BigDecimal reservaLegal;
    private BigDecimal reservaEstatutaria;
    private BigDecimal ajustesValorPatrimonial;
    private BigDecimal previsaoDividendos;
    private BigDecimal ajustesCambio;

    private final DataBaseManager dbManager = new DataBaseManager();

    public BalancoPatrimonial() {
        calcularSaldos();
    }

    public void calcularSaldos() {
        this.caixa = dbManager.buscarTotalDaConta(ContaContabil.CAIXA);
        this.bancos = dbManager.buscarTotalDaConta(ContaContabil.BANCOS);
        this.contasAReceber = dbManager.buscarTotalDaConta(ContaContabil.CONTAS_A_RECEBER);
        this.estoques = dbManager.buscarTotalDaConta(ContaContabil.ESTOQUES);
        this.adiantamentos = dbManager.buscarTotalDaConta(ContaContabil.ADIANTAMENTOS);
        this.aplicacoesFinanceiras = dbManager.buscarTotalDaConta(ContaContabil.APLICACOES_FINANCEIRAS);
        this.impostosARecuperar = dbManager.buscarTotalDaConta(ContaContabil.IMPOSTOS_A_RECUPERAR);
        this.despesasPrevistas = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_PREVISTAS);

        this.imobilizado = dbManager.buscarTotalDaConta(ContaContabil.IMOBILIZADO);
        this.intangivel = dbManager.buscarTotalDaConta(ContaContabil.INTANGIVEL);
        this.aplicacoesLongoPrazo = dbManager.buscarTotalDaConta(ContaContabil.APLICACOES_A_LONGO_PRAZO);
        this.investimentos = dbManager.buscarTotalDaConta(ContaContabil.INVESTIMENTOS);
        this.propriedadesInvestidas = dbManager.buscarTotalDaConta(ContaContabil.PROPRIEDADES_INVESTIDAS);
        this.ativoDiferido = dbManager.buscarTotalDaConta(ContaContabil.ATIVO_DIFERIDO);

        this.fornecedores = dbManager.buscarTotalDaConta(ContaContabil.FORNECEDORES);
        this.emprestimosBancarios = dbManager.buscarTotalDaConta(ContaContabil.EMPRESTIMOS_BANCARIOS);
        this.obrigacoesFiscais = dbManager.buscarTotalDaConta(ContaContabil.OBRIGACOES_FISCAIS);
        this.provisoes = dbManager.buscarTotalDaConta(ContaContabil.PROVISOES);
        this.dividendosAPagar = dbManager.buscarTotalDaConta(ContaContabil.DIVIDENDOS_A_PAGAR);
        this.obrigacaoTributaria = dbManager.buscarTotalDaConta(ContaContabil.OBRIGACAO_TRIBUTARIA);
        this.salariosAPagar = dbManager.buscarTotalDaConta(ContaContabil.SALARIOS_A_PAGAR);
        this.despesasAPagar = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_A_PAGAR);
        this.outrasObrigacoes = dbManager.buscarTotalDaConta(ContaContabil.OUTRAS_OBRIGACOES);

        this.obrigacaoLongoPrazo = dbManager.buscarTotalDaConta(ContaContabil.OBRIGACAO_DE_LONGO_PRAZO);
        this.financiamento = dbManager.buscarTotalDaConta(ContaContabil.FINANCIAMENTO_DE_LONGO_PRAZO);
        this.emprestimosLongoPrazo = dbManager.buscarTotalDaConta(ContaContabil.EMPRESTIMOS_DE_LONGO_PRAZO);

        this.capitalSocial = dbManager.buscarTotalDaConta(ContaContabil.CAPITAL_SOCIAL);
        this.lucrosAcumulados = dbManager.buscarTotalDaConta(ContaContabil.LUCROS_ACUMULADOS);
        this.reservas = dbManager.buscarTotalDaConta(ContaContabil.RESERVAS);
        this.reservaLegal = dbManager.buscarTotalDaConta(ContaContabil.RESERVA_LEGAL);
        this.reservaEstatutaria = dbManager.buscarTotalDaConta(ContaContabil.RESERVA_ESTATUTARIA);
        this.ajustesValorPatrimonial = dbManager.buscarTotalDaConta(ContaContabil.AJUSTES_A_VALOR_PATRIMONIAL);
        this.previsaoDividendos = dbManager.buscarTotalDaConta(ContaContabil.PREVISAO_DE_DIVIDENDOS);
        this.ajustesCambio = dbManager.buscarTotalDaConta(ContaContabil.AJUSTES_DE_CAMBIO);
    }


    public BigDecimal getAtivoCirculante() {
        return caixa.add(bancos).add(contasAReceber).add(estoques).add(adiantamentos)
                .add(aplicacoesFinanceiras).add(impostosARecuperar).add(despesasPrevistas);
    }

    public BigDecimal getAtivoNaoCirculante() {
        return imobilizado.add(intangivel).add(aplicacoesLongoPrazo).add(investimentos)
                .add(propriedadesInvestidas).add(ativoDiferido);
    }

    public BigDecimal getAtivoTotal() {
        return getAtivoCirculante().add(getAtivoNaoCirculante());
    }

    public BigDecimal getPassivoCirculante() {
        return fornecedores.add(emprestimosBancarios).add(obrigacoesFiscais).add(provisoes)
                .add(dividendosAPagar).add(obrigacaoTributaria).add(salariosAPagar).add(despesasAPagar)
                .add(outrasObrigacoes);
    }

    public BigDecimal getPassivoNaoCirculante() {
        return obrigacaoLongoPrazo.add(financiamento).add(emprestimosLongoPrazo);
    }

    public BigDecimal getPassivoTotal() {
        return getPassivoCirculante().add(getPassivoNaoCirculante());
    }

    public BigDecimal getPatrimonioLiquido() {
        return capitalSocial.add(lucrosAcumulados).add(reservas).add(reservaLegal)
                .add(reservaEstatutaria).add(ajustesValorPatrimonial).add(previsaoDividendos).add(ajustesCambio);
    }

    public BigDecimal getPassivoEPatrimonioLiquido() {
        return this.getPatrimonioLiquido().add(this.getPassivoTotal());
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
}
