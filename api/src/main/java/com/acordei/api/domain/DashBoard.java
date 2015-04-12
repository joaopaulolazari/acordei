package com.acordei.api.domain;

import org.bson.Document;

import java.util.List;

/**
 * Created by deivison on 4/12/15.
 */
public class DashBoard {

    private String tipo;
    private String titulo;
    private String conteudo;

    private int totalFatias;
    private List<Double> eixoYValores;
    private List<String> eixoYLabels;

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

    public List<Double> getEixoYValores() {
        return eixoYValores;
    }

    public void setEixoYValores(List<Double> eixoYValores) {
        this.eixoYValores = eixoYValores;
    }

    public List<String> getEixoYLabels() {
        return eixoYLabels;
    }

    public void setEixoYLabels(List<String> eixoYLabels) {
        this.eixoYLabels = eixoYLabels;
    }
}
