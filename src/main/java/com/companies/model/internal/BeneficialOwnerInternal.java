package com.companies.model.internal;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

public final class BeneficialOwnerInternal {

    private final UUID companyId;

    private final String ownerName;

    public BeneficialOwnerInternal(UUID companyId, String ownerName) {
        this.companyId = companyId;
        this.ownerName = ownerName;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BeneficialOwnerInternal that = (BeneficialOwnerInternal) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(ownerName, that.ownerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, ownerName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("companyId", companyId)
                .add("ownerName", ownerName)
                .toString();
    }
}