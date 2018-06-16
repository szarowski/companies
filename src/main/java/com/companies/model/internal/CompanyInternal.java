package com.companies.model.internal;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

public final class CompanyInternal {

    private final UUID companyId;

    private final String name;

    private final String address;

    private final String city;

    private final String country;

    private final String email;

    private final String phone;

    public CompanyInternal(final UUID companyId, final String name, final String address, final String city,
                           final String country, final String email, final String phone) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phone = phone;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CompanyInternal that = (CompanyInternal) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(country, that.country) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, name, address, city, country, email, phone);
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
                .toString();
    }
}