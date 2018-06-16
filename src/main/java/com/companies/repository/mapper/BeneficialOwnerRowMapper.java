package com.companies.repository.mapper;

import com.companies.model.internal.BeneficialOwnerInternal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Row mapper to retrieve mapped rows from the beneficiary_owners database
 */
public class BeneficialOwnerRowMapper implements RowMapper<BeneficialOwnerInternal> {

    @Override
    public BeneficialOwnerInternal mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new BeneficialOwnerInternal(
                rs.getObject("company_id", UUID.class),
                rs.getString("owner_name"));
    }
}