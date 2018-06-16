package com.companies.model.internal;

import com.companies.util.Random;

import java.util.UUID;

public final class CompanyInternalBuilder {

    private UUID companyId = Random.uuid();
    private String name = Random.string();
    private String address = Random.string();
    private String city = Random.string();
    private String country = Random.country();
    private String email = Random.email();
    private String phone = Random.string();

    private CompanyInternalBuilder() {
    }

    public static CompanyInternalBuilder companyInternalBuilder() {
        return new CompanyInternalBuilder();
    }

    public CompanyInternalBuilder companyId(UUID companyId) {
        this.companyId = companyId;
        return this;
    }

    public CompanyInternalBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CompanyInternalBuilder address(String address) {
        this.address = address;
        return this;
    }

    public CompanyInternalBuilder city(String city) {
        this.city = city;
        return this;
    }

    public CompanyInternalBuilder country(String country) {
        this.country = country;
        return this;
    }

    public CompanyInternalBuilder email(String email) {
        this.email = email;
        return this;
    }

    public CompanyInternalBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public CompanyInternal build() {
        return new CompanyInternal(companyId, name, address, city, country, email, phone);
    }
}
