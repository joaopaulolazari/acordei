package com.acordei.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PoliticoAssiduidadeEvento {
    private String data;
    private String frequenciaNoDia;

    public PoliticoAssiduidadeEvento(String data, String frequenciaNoDia) {
        this.data = data;
        this.frequenciaNoDia = frequenciaNoDia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFrequenciaNoDia() {
        return frequenciaNoDia;
    }

    public void setFrequenciaNoDia(String frequenciaNoDia) {
        this.frequenciaNoDia = frequenciaNoDia;
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
