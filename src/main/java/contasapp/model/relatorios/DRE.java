package contasapp.model.relatorios;

import java.math.BigDecimal;

public class DRE extends Relatorio{

    public DRE() {
        this.buscarSaldos();
    }

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


}

