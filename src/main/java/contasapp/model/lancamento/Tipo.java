package contasapp.model.lancamento;

public enum Tipo {
    // ATIVO
    ATIVO_CIRCULANTE("Ativo Circulante", "Ativo", "Circulante"),
    ATIVO_NAO_CIRCULANTE("Ativo Não Circulante", "Ativo", "Não Circulante"),

    // PASSIVO
    PASSIVO_CIRCULANTE("Passivo Circulante", "Passivo", "Circulante"),
    PASSIVO_NAO_CIRCULANTE("Passivo Não Circulante", "Passivo", "Não Circulante"),

    // PATRIMÔNIO LÍQUIDO (PL)
    PATRIMONIO_LIQUIDO("Patrimônio Líquido", "Patrimônio Líquido", null),

    // RESULTADO
    RECEITA("Receita", "Resultado", null),
    DESPESA("Despesa", "Resultado", null);

    private final String descricao;
    private final String categoria;
    private final String subcategoria;

    // Construtor
    Tipo(String descricao, String categoria, String subcategoria) {
        this.descricao = descricao;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
