package contasapp.model.lancamento;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public class Lancamento {

    private UUID id;
    private LocalDate data;
    private ContaContabil conta;
    private Tipo tipo;
    private String descricao;
    private BigDecimal debito; //Entrada de valores na conta
    private BigDecimal credito; //Saída de valores da conta


    public Lancamento(UUID id, LocalDate data, ContaContabil conta, Tipo tipo, String descricao, BigDecimal debito, BigDecimal credito) {
        this.id = id;
        this.data = data;
        this.conta = conta;
        this.tipo = tipo;
        this.descricao = descricao;
        this.credito = credito;
        this.debito = debito;
    }

    public UUID getId(){
        return this.id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public ContaContabil getConta() {
        return conta;
    }

    public void setConta(ContaContabil conta) {
        this.conta = conta;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public Object[] linhaFormatada() {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = (data != null) ? data.format(formatoData) : "N/A";

        NumberFormat formatoMoeda = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        formatoMoeda.setMinimumFractionDigits(2);
        formatoMoeda.setMaximumFractionDigits(2);
        String valorDebito = (debito != null) ? "R$ " + formatoMoeda.format(debito) : "R$ 0,00";
        String valorCredito = (credito != null) ? "R$ " + formatoMoeda.format(credito) : "R$ 0,00";

        return new Object[]{
                id.toString(),
                dataFormatada,
                valorDebito,
                valorCredito,
                (tipo != null) ? tipo.toString().replace("_", " ") : "N/A",
                (conta != null) ? conta.toString().replace("_", " ") : "N/A",
                (descricao != null) ? descricao : "N/A"
        };
    }
}
