package contasapp.model.relatorios;

import contasapp.database.DataBaseManager;
import contasapp.model.lancamento.ContaContabil;

import java.math.BigDecimal;

public class Balancete{

    public BigDecimal buscarEClassifcarSaldoDaConta(ContaContabil conta, String descricaoDaLinha){
        DataBaseManager dbManager = new DataBaseManager();

        if (descricaoDaLinha.equals("debito")) {
            if (conta.getTipo().contains("Ativo") || conta.getTipo().contains("Despesa")) {
                return dbManager.buscarTotalDaConta(conta);
            }

        }

        if (descricaoDaLinha.equals("credito")) {
            if (conta.getTipo().contains("Passivo") || conta.getTipo().contains("Receita") || conta.getTipo().contains("PL")) {
                return dbManager.buscarTotalDaConta(conta);
            }
        }

        return BigDecimal.ZERO;
    }
}

