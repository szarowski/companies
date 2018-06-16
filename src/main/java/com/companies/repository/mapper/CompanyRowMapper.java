package com.companies.repository.mapper;

import com.companies.model.internal.CompanyInternal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Row mapper to retrieve mapped rows from the companies database
 */
public class CompanyRowMapper implements RowMapper<CompanyInternal> {

    @Override
    public CompanyInternal mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new CompanyInternal(
                rs.getObject("company_id", UUID.class),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("city"),
                rs.getString("country"),
                rs.getString("email"),
                rs.getString("phone"));
    }
}