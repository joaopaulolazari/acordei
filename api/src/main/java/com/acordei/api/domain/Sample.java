package com.acordei.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Sample {
    private String sampleId;

    public Sample() {
    }

    public Sample(String idAtividade) {
        this.sampleId = idAtividade;
    }

    public String getSampleId() {
        return sampleId;
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
