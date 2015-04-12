package com.acordei.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Politico {
    private String matricula;
    private String nome;
    private String nomeUrna;
    private String nomeParlamentar;
    private String email;
    private String foto;
    private String uf;
    private String biografia;
    private String situacao;

    public Politico() {
    }

    public Politico(String matricula, String nome, String nomeParlamentar, String email, String foto, String uf) {
        this.matricula = matricula;
        this.nome = nome;
        this.nomeParlamentar = nomeParlamentar;
        this.email = email;
        this.foto = foto;
        this.uf = uf;
    }

    public Politico(String matricula, String nome, String nomeParlamentar, String email, String foto, String uf, String biografia, String situacao) {
        this.matricula = matricula;
        this.nome = nome;
        this.nomeParlamentar = nomeParlamentar;
        this.email = email;
        this.foto = foto;
        this.uf = uf;
        this.biografia = biografia;
        this.situacao = situacao;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getNomeParlamentar() {
        return nomeParlamentar;
    }

    public void setNomeParlamentar(String nomeParlamentar) {
        this.nomeParlamentar = nomeParlamentar;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(o, this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public void setNomeUrna(String nomeUrna) {
        this.nomeUrna = nomeUrna;
    }
}
