package com.acordei.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Politico {
    private String matricula;
    private String nome;
    private String email;
    private String foto;
    private String uf;
    private String biografia;
    private String situacao;

    public Politico(String matricula, String nome, String foto, String uf) {
        this.matricula = matricula;
        this.nome = nome;
        this.foto = foto;
        this.uf = uf;
    }

    public Politico(PoliticoAssiduidade assiduidade) {
        this.assiduidade = assiduidade;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public PoliticoAssiduidade getAssiduidade() {
        return assiduidade;
    }

    public void setAssiduidade(PoliticoAssiduidade assiduidade) {
        this.assiduidade = assiduidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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

}
