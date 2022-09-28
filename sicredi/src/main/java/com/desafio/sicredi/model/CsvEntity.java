package com.desafio.sicredi.model;

import com.opencsv.bean.CsvBindByPosition;

public class CsvEntity {

    @CsvBindByPosition(position = 0)
    private String agencia;
    @CsvBindByPosition(position = 1)
    private String conta;
    @CsvBindByPosition(position = 2)
    private double saldo;
    @CsvBindByPosition(position = 3)
    private String status;

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getStatus() {
        return status;
    }

    public String[] toString(String serviceRetorno) {
        String[] dados = new String[5];
        dados[0] = agencia;
        dados[1] = conta;
        dados[2] = String.valueOf(saldo);
        dados[3] = status;
        dados[4] = serviceRetorno;

        return dados;
    }
}