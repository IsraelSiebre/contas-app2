package contasapp.model.relatorios;

import java.math.BigDecimal;

public class Indices {
    private BalancoPatrimonial balancoPatrimonial = new BalancoPatrimonial();
    private DRE dre = new DRE();


    private BigDecimal safeDivide(BigDecimal numerator, BigDecimal denominator) {
        return (denominator.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : numerator.divide(denominator, 4, BigDecimal.ROUND_HALF_UP);
    }

    // Índices de Liquidez
    public BigDecimal calcularLiquidezImediata() {
        return safeDivide(balancoPatrimonial.getDisponivel(), balancoPatrimonial.getPassivoCirculante());
    }

    public BigDecimal calcularLiquidezSeca() {
        return safeDivide(
                balancoPatrimonial.getAtivoCirculante().subtract(balancoPatrimonial.estoques),
                balancoPatrimonial.getPassivoCirculante()
        );
    }

    public BigDecimal calcularLiquidezCorrente() {
        return safeDivide(balancoPatrimonial.getAtivoCirculante(), balancoPatrimonial.getPassivoCirculante());
    }

    public BigDecimal calcularLiquidezGeral() {
        return safeDivide(
                balancoPatrimonial.getAtivoCirculante().add(balancoPatrimonial.getAtivoNaoCirculante()),
                balancoPatrimonial.getPassivoCirculante().add(balancoPatrimonial.getPassivoNaoCirculante())
        );
    }

    // Índices de Estrutura de Capital
    public BigDecimal calcularEndividamentoGeral() {
        return safeDivide(balancoPatrimonial.getPassivoTotal(), balancoPatrimonial.getAtivoTotal());
    }

    public BigDecimal calcularComposicaoEndividamento() {
        return safeDivide(balancoPatrimonial.getPassivoCirculante(), balancoPatrimonial.getPassivoTotal());
    }

    public BigDecimal calcularImobilizacaoPatrimonioLiquido() {
        return safeDivide(balancoPatrimonial.getAtivoNaoCirculante(), balancoPatrimonial.getPatrimonioLiquido());
    }

    public BigDecimal calcularImobilizacaoRecursosNaoCorrentes() {
        return safeDivide(balancoPatrimonial.getAtivoNaoCirculante(), balancoPatrimonial.getPatrimonioLiquido().add(balancoPatrimonial.getPassivoNaoCirculante()));
    }

    // Índices de Rentabilidade
    public BigDecimal calcularMargemBruta() {
        return safeDivide(dre.calcularLucroBruto(), dre.calcularReceitaTotal());
    }

    public BigDecimal calcularMargemOperacional() {
        return safeDivide(dre.calcularResultadoOperacional(), dre.calcularReceitaTotal());
    }

    public BigDecimal calcularMargemLiquida() {
        return safeDivide(dre.calcularLucroLiquido(), dre.calcularReceitaTotal());
    }

    public BigDecimal calcularGiroAtivo() {
        return safeDivide(dre.calcularReceitaTotal(), balancoPatrimonial.getAtivoTotal());
    }

    public BigDecimal calcularROA() {
        return safeDivide(dre.calcularLucroLiquido(), balancoPatrimonial.getAtivoTotal());
    }

    public BigDecimal calcularROE() {
        return safeDivide(dre.calcularLucroLiquido(), balancoPatrimonial.getPatrimonioLiquido());
    }

    public BigDecimal calcularROI() {
        return safeDivide(dre.calcularResultadoOperacional(), balancoPatrimonial.getPatrimonioLiquido().add(balancoPatrimonial.getEmprestimos()));
    }


    // Índices de Estrutura e Solvência
    public BigDecimal calcularAutonomiaFinanceira() {
        return safeDivide(balancoPatrimonial.getPatrimonioLiquido(), balancoPatrimonial.getAtivoTotal());
    }


    public BigDecimal calcularGarantiaCapitalProprio() {
        return safeDivide(balancoPatrimonial.getPatrimonioLiquido(), balancoPatrimonial.getPassivoTotal());
    }
}
