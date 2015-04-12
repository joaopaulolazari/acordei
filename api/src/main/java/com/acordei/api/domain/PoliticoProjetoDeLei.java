package com.acordei.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PoliticoProjetoDeLei {
    private String tipoProposicao;
    private String descricao;
    private String idSituacao;
    private String situacao;

    public PoliticoProjetoDeLei() {
    }

    public PoliticoProjetoDeLei(String tipoProposicao, String descricao, String idSituacao, String situacao) {
        this.tipoProposicao = tipoProposicao;
        this.descricao = descricao;
        this.idSituacao = idSituacao;
        this.situacao = situacao;
    }

    public String getTipoProposicao() {
        return tipoProposicao;
    }

    public void setTipoProposicao(String tipoProposicao) {
        this.tipoProposicao = tipoProposicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdSituacao() {
        return idSituacao;
    }

    public void setIdSituacao(String idSituacao) {
        this.idSituacao = idSituacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
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
