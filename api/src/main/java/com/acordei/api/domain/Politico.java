package com.acordei.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Politico {
    private PoliticoAssiduidade assiduidade;

    public Politico(PoliticoAssiduidade assiduidade) {
        this.assiduidade = assiduidade;
    }

    public PoliticoAssiduidade getAssiduidade() {
        return assiduidade;
    }

    public void setAssiduidade(PoliticoAssiduidade assiduidade) {
        this.assiduidade = assiduidade;
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
