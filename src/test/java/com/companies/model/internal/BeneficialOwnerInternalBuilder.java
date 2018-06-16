package com.companies.model.internal;

import com.companies.util.Random;

import java.util.UUID;

public final class BeneficialOwnerInternalBuilder {

    private UUID companyId = Random.uuid();
    private String ownerName = Random.string();

    private BeneficialOwnerInternalBuilder() {
    }

    public static BeneficialOwnerInternalBuilder beneficialOwnerInternalBuilder() {
        return new BeneficialOwnerInternalBuilder();
    }

    public BeneficialOwnerInternalBuilder companyId(UUID companyId) {
        this.companyId = companyId;
        return this;
    }

    public BeneficialOwnerInternalBuilder ownerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public BeneficialOwnerInternal build() {
        return new BeneficialOwnerInternal(companyId, ownerName);
    }
}
