package contasapp.relatorios;

import contasapp.database.DataBaseManager;
import contasapp.model.lancamento.ContaContabil;
import java.math.BigDecimal;

public class DRE {

    // RECEITAS
    private BigDecimal receitaOperacional;
    private BigDecimal receitasNaoOperacionais;
    private BigDecimal receitaVendas;
    private BigDecimal receitaServicos;
    private BigDecimal receitasFinanceiras;
    private BigDecimal outrasReceitas;

    // DESPESAS OPERACIONAIS
    private BigDecimal despesasOperacionais;
    private BigDecimal despesasNaoOperacionais;
    private BigDecimal despesasCompras;
    private BigDecimal despesasVendas;
    private BigDecimal despesasAdministrativas;
    private BigDecimal despesasFinanceiras;
    private BigDecimal despesasTributarias;
    private BigDecimal despesasOperacionaisOutras;
    private BigDecimal imprevisto;



    DataBaseManager dbManager = new DataBaseManager(); // Inicializa o DataBaseManager


    // Métodos para calcular totais com BigDecimal
    public BigDecimal calcularReceitaLiquida() {
        return this.receitaOperacional.add(this.receitaVendas).add(this.receitaServicos).add(this.receitasNaoOperacionais).add(this.receitasFinanceiras).add(this.outrasReceitas);
    }

    public BigDecimal calcularLucroBruto() {
        return calcularReceitaLiquida().subtract(this.despesasCompras);
    }

    public BigDecimal calcularResultadoOperacional() {
        return calcularLucroBruto()
                .subtract(this.despesasVendas)
                .subtract(this.despesasAdministrativas)
                .subtract(this.despesasOperacionaisOutras)
                .subtract(this.despesasOperacionais)
                .subtract(this.imprevisto);
    }

    public BigDecimal calcularResultadoAntesImpostos() {
        return calcularResultadoOperacional().add(this.receitasFinanceiras).subtract(this.despesasFinanceiras);
    }

    public BigDecimal calcularLucroLiquido() {
        return calcularResultadoAntesImpostos().subtract(this.despesasTributarias);
    }

    public void buscarValoresDRE() {
        // RECEITAS
        this.receitaOperacional= dbManager.buscarTotalDaConta(ContaContabil.RECEITAS_OPERACIONAIS).get("credito");
        this.receitasNaoOperacionais = dbManager.buscarTotalDaConta(ContaContabil.RECEITAS_NAO_OPERACIONAIS).get("credito");
        this.receitaVendas = dbManager.buscarTotalDaConta(ContaContabil.RECEITA_VENDA).get("credito");
        this.receitaServicos = dbManager.buscarTotalDaConta(ContaContabil.RECEITA_SERVIÇOS).get("credito");
        this.receitasFinanceiras = dbManager.buscarTotalDaConta(ContaContabil.RECEITAS_FINANCEIRAS).get("credito");
        this.outrasReceitas = dbManager.buscarTotalDaConta(ContaContabil.OUTRAS_RECEITAS).get("credito");

        // DESPESAS
        this.despesasOperacionais = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_OPERACIONAIS).get("debito");
        this.despesasNaoOperacionais = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_NAO_OPERACIONAIS).get("debito");
        this.despesasCompras = dbManager.buscarTotalDaConta(ContaContabil.DESPESA_COMPRAS).get("debito");
        this.despesasVendas = dbManager.buscarTotalDaConta(ContaContabil.DESPESA_VENDAS).get("debito");
        this.despesasAdministrativas = dbManager.buscarTotalDaConta(ContaContabil.DESPESA_ADMINISTRATIVA).get("debito");
        this.despesasFinanceiras = dbManager.buscarTotalDaConta(ContaContabil.DESPESA_FINANCEIRA).get("debito");
        this.despesasTributarias = dbManager.buscarTotalDaConta(ContaContabil.DESPESAS_TRIBUTARIAS).get("debito");
        this.despesasOperacionaisOutras = dbManager.buscarTotalDaConta(ContaContabil.DESPESA_OPERACIONAL_OUTRAS).get("debito");
        this.imprevisto = dbManager.buscarTotalDaConta(ContaContabil.IMPREVISTO).get("debito");

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
