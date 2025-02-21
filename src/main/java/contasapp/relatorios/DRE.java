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

    // Valores do ano anterior
    private BigDecimal receitaLiquidaAnoAnterior;
    private BigDecimal lucroLiquidoAnoAnterior;

    DataBaseManager dbManager = new DataBaseManager(); // Inicializa o DataBaseManager

    public BigDecimal calcularReceitaOperacional(){
        return this.receitaOperacional.add(this.receitaVendas).add(this.receitaServicos);
    }

    public BigDecimal calcularReceitaNaoOperacional(){
        return this.receitasNaoOperacionais.add(this.outrasReceitas);
    }

    public BigDecimal calcularReceitaTotal() {
        return this.calcularReceitaOperacional().add(this.calcularReceitaNaoOperacional());
    }

    public BigDecimal calcularLucroBruto() {
        return calcularReceitaTotal().subtract(this.despesasCompras);
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
        return calcularResultadoOperacional().add(this.receitasFinanceiras).subtract(this.despesasFinanceiras).subtract(despesasNaoOperacionais);
    }

    public BigDecimal calcularLucroLiquido() {
        return calcularResultadoAntesImpostos().subtract(this.despesasTributarias);
    }

    public void buscarValoresDRE() {
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

    // Métodos para obter valores
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

    public BigDecimal getDespesasOperacionais() {
        return despesasOperacionais;
    }
}

