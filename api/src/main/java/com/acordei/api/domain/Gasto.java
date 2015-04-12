package com.acordei.api.domain;

/**
 * Created by deivison on 4/12/15.
 */
public class Gasto {

    private String tipo;
    private Double valor;

    public Gasto(String tipo, Double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {

        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
