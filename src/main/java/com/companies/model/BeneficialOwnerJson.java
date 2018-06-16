package com.companies.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.MoreObjects;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class BeneficialOwnerJson {

    @NotNull
    private final String ownerName;

    @JsonCreator
    public BeneficialOwnerJson(final String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BeneficialOwnerJson that = (BeneficialOwnerJson) o;
        return Objects.equals(ownerName, that.ownerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ownerName", ownerName)
                .toString();
    }
}