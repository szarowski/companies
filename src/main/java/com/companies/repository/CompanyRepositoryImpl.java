package com.companies.repository;

import com.companies.model.internal.CompanyInternal;
import com.companies.repository.mapper.CompanyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.companies.util.MapBuilder.mapWith;

/**
 * Implementation of the Company Repository providing persistence services for CompanyController.
 */
@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public CompanyRepositoryImpl(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void saveCompany(final CompanyInternal company) {
        jdbc.update("INSERT INTO companies (company_id, name, address, city, country, email, phone) " +
                "VALUES (:company_id, :name, :address, :city, :country, :email, :phone)",
                mapWith("company_id", (Object) company.getCompanyId())
                        .and("name", company.getName())
                        .and("address", company.getAddress())
                        .and("city", company.getCity())
                        .and("country", company.getCountry())
                        .and("email", company.getEmail())
                        .and("phone", company.getPhone()));
    }

    @Override
    public void updateCompany(final CompanyInternal company) {
        jdbc.update("UPDATE companies SET name = :name, address = :address, city = :city, " +
                        "country = :country, email = :email, phone = :phone WHERE company_id = :company_id",
                mapWith("name", (Object) company.getName())
                        .and("address", company.getAddress())
                        .and("city", company.getCity())
                        .and("country", company.getCountry())
                        .and("email", company.getEmail())
                        .and("phone", company.getPhone())
                        .and("company_id", company.getCompanyId()));
    }

    @Override
    public Optional<CompanyInternal> findCompanyById(final UUID companyId) {
        final Map<String, ?> params = mapWith("company_id", companyId);
        return jdbc.query("SELECT * FROM companies WHERE company_id = :company_id", params, new CompanyRowMapper())
                .stream().findFirst();
    }

    @Override
    public List<CompanyInternal> findAllCompanies() {
        return jdbc.query("SELECT * FROM companies", new CompanyRowMapper());
    }

    @Override
    public int deleteCompanyByCompanyId(final UUID companyId) {
        final Map<String, ?> params = mapWith("company_id", companyId);
        return jdbc.update("DELETE FROM companies WHERE company_id = :company_id", params);
    }
}