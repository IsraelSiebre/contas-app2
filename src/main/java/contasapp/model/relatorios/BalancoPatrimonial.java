package contasapp.model.relatorios;

import java.math.BigDecimal;

public class BalancoPatrimonial extends Relatorio {

    public BalancoPatrimonial() {
        this.buscarSaldos();
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

    public BigDecimal getDisponivel() {
        return caixa.add(bancos).add(aplicacoesFinanceiras);
    }

    public BigDecimal getEmprestimos() {
        return emprestimosBancarios.add(emprestimosLongoPrazo);
    }

}
