package com.companies.model;

import com.companies.util.Random;

import java.util.List;
import java.util.UUID;

public final class CompanyJsonBuilder {

    private UUID companyId = Random.uuid();
    private String name = Random.string();
    private String address = Random.string();
    private String city = Random.string();
    private String country = Random.country();
    private String email = Random.email();
    private String phone = Random.string();
    private List<String> beneficialOwners = Random.list(Random.string());

    private CompanyJsonBuilder() {
    }

    public static CompanyJsonBuilder companyJsonBuilder() {
        return new CompanyJsonBuilder();
    }

    public CompanyJsonBuilder companyId(UUID companyId) {
        this.companyId = companyId;
        return this;
    }

    public CompanyJsonBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CompanyJsonBuilder address(String address) {
        this.address = address;
        return this;
    }

    public CompanyJsonBuilder city(String city) {
        this.city = city;
        return this;
    }

    public CompanyJsonBuilder country(String country) {
        this.country = country;
        return this;
    }

    public CompanyJsonBuilder email(String email) {
        this.email = email;
        return this;
    }

    public CompanyJsonBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public CompanyJsonBuilder beneficialOwners(List<String> beneficialOwners) {
        this.beneficialOwners = beneficialOwners;
        return this;
    }

    public CompanyJson build() {
        return new CompanyJson(companyId, name, address, city, country, email, phone, beneficialOwners);
    }
}
