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
    private String nomePoliticoRelacionado;
    private String fotoPoliticoRelacionado;
    private String idPoliticoRelacionado;

    private int totalFatias;
    private List<Double> eixoYValores;
    private List<String> eixoYLabels;

    public DashBoard(String tipo, String titulo, String conteudo, String nomePoliticoRelacionado, String fotoPoliticoRelacionado, String idPoliticoRelacionado, int totalFatias, List<Double> eixoYValores, List<String> eixoYLabels) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.nomePoliticoRelacionado = nomePoliticoRelacionado;
        this.fotoPoliticoRelacionado = fotoPoliticoRelacionado;
        this.idPoliticoRelacionado = idPoliticoRelacionado;
        this.totalFatias = totalFatias;
        this.eixoYValores = eixoYValores;
        this.eixoYLabels = eixoYLabels;
    }

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

    public String getNomePoliticoRelacionado() {
        return nomePoliticoRelacionado;
    }

    public void setNomePoliticoRelacionado(String nomePoliticoRelacionado) {
        this.nomePoliticoRelacionado = nomePoliticoRelacionado;
    }

    public String getFotoPoliticoRelacionado() {
        return fotoPoliticoRelacionado;
    }

    public void setFotoPoliticoRelacionado(String fotoPoliticoRelacionado) {
        this.fotoPoliticoRelacionado = fotoPoliticoRelacionado;
    }

    public String getIdPoliticoRelacionado() {
        return idPoliticoRelacionado;
    }

    public void setIdPoliticoRelacionado(String idPoliticoRelacionado) {
        this.idPoliticoRelacionado = idPoliticoRelacionado;
    }


}
