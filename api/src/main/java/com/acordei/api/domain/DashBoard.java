package com.acordei.api.domain;

/**
 * Created by deivison on 4/12/15.
 */
public class DashBoard {

    private String tipo;
    private String titulo;
    private String conteudo;

    private int totalFatias;
    private Float[] eixoYValores;
    private String[] eixoYLabels;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getTotalFatias() {
        return totalFatias;
    }

    public void setTotalFatias(int totalFatias) {
        this.totalFatias = totalFatias;
    }

    public Float[] getEixoYValores() {
        return eixoYValores;
    }

    public void setEixoYValores(Float[] eixoYValores) {
        this.eixoYValores = eixoYValores;
    }

    public String[] getEixoYLabels() {
        return eixoYLabels;
    }

    public void setEixoYLabels(String[] eixoYLabels) {
        this.eixoYLabels = eixoYLabels;
    }
}
