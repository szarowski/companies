package com.companies.model;

import com.companies.validator.NotEmptyFields;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class CompanyJson {

    @NotNull
    private final UUID companyId;

    @NotNull
    private final String name;

    @NotNull
    private final String address;

    @NotNull
    private final String city;

    @NotNull
    private final String country;

    @Email
    private final String email;

    private final String phone;

    @NotNull
    @NotEmptyFields
    private final List<String> beneficialOwners;

    @JsonCreator
    public CompanyJson(final UUID companyId, final String name, final String address, final String city,
                       final String country, final String email, final String phone, final List<String> beneficialOwners) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phone = phone;
        this.beneficialOwners = beneficialOwners;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getBeneficialOwners() {
        return beneficialOwners;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CompanyJson that = (CompanyJson) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(country, that.country) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(beneficialOwners, that.beneficialOwners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, name, address, city, country, email, phone, beneficialOwners);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("companyId", companyId)
                .add("name", name)
                .add("address", address)
                .add("city", city)
                .add("country", country)
                .add("email", email)
                .add("phone", phone)
                .add("beneficialOwners", beneficialOwners)
                .toString();
    }
}