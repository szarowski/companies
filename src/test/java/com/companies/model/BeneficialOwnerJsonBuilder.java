package com.companies.model;

import com.companies.util.Random;

public final class BeneficialOwnerJsonBuilder {

    private String ownerName = Random.string();

    private BeneficialOwnerJsonBuilder() {
    }

    public static BeneficialOwnerJsonBuilder beneficialOwnerJsonBuilder() {
        return new BeneficialOwnerJsonBuilder();
    }

    public BeneficialOwnerJsonBuilder ownerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public BeneficialOwnerJson build() {
        return new BeneficialOwnerJson(ownerName);
    }
}
