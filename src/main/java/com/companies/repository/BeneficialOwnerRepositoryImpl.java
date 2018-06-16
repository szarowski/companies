package com.companies.repository;

import com.companies.model.internal.BeneficialOwnerInternal;
import com.companies.repository.mapper.BeneficialOwnerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.companies.util.MapBuilder.mapWith;

/**
 * Implementation of the BeneficialOwner Repository providing persistence services for CompanyController.
 */
@Repository
public class BeneficialOwnerRepositoryImpl implements BeneficialOwnerRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public BeneficialOwnerRepositoryImpl(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void saveBeneficialOwner(final BeneficialOwnerInternal beneficialOwner) {
        jdbc.update("INSERT INTO beneficial_owners (company_id, owner_name) VALUES (:company_id, :owner_name)",
                mapWith("company_id", (Object) beneficialOwner.getCompanyId()).and("owner_name",
                        beneficialOwner.getOwnerName()));
    }

    @Override
    public List<BeneficialOwnerInternal> findBeneficialOwnerById(final UUID companyId) {
        final Map<String, ?> params = mapWith("company_id", companyId);
        return jdbc.query("SELECT * FROM beneficial_owners WHERE company_id = :company_id", params,
                new BeneficialOwnerRowMapper());
    }

    @Override
    public int deleteAllBeneficialOwnersById(final UUID companyId) {
        final Map<String, ?> params = mapWith("company_id", companyId);
        return jdbc.update("DELETE FROM beneficial_owners WHERE company_id = :company_id", params);
    }
}