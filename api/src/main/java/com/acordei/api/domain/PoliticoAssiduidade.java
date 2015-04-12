package com.acordei.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class PoliticoAssiduidade {

    private List<PoliticoAssiduidadeEvento> eventos;

    public PoliticoAssiduidade() {
    }

    public PoliticoAssiduidade(List<PoliticoAssiduidadeEvento> eventos) {
        this.eventos = eventos;
    }

    public List<PoliticoAssiduidadeEvento> getEventos() {
        return eventos;
    }

    public void setEventos(List<PoliticoAssiduidadeEvento> eventos) {
        this.eventos = eventos;
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
