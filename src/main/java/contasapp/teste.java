package contasapp;

import contasapp.relatorios.DRE;

public class teste {

    public static void main(String[] args){
        DRE dre = new DRE();
        dre.buscarValoresDRE();

        System.out.println(dre.getReceitaVendas());

    }
}
