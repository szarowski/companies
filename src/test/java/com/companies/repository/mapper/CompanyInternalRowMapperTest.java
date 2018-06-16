package com.companies.repository.mapper;

import com.companies.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class CompanyInternalRowMapperTest {

    private static final String COL_COMPANY_ID ="company_id";
    private static final String COL_NAME = "name";
    private static final String COL_ADDRESS = "address";
    private static final String COL_CITY = "city";
    private static final String COL_COUNTRY = "country";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";

    private ResultSet rs;
    private static final int ROW_NUM = 88;

    @Before
    public void setupMocks() {
        rs = mock(ResultSet.class);
    }

    @Test
    public void testInteractions() throws SQLException {
        when(rs.getBytes(any(String.class))).thenReturn(Random.uuid().toString().getBytes());

        final CompanyRowMapper rowMapper = new CompanyRowMapper();
        rowMapper.mapRow(rs, ROW_NUM);

        verify(rs, Mockito.times(1)).getObject(COL_COMPANY_ID, UUID.class);
        verify(rs, Mockito.times(1)).getString(COL_NAME);
        verify(rs, Mockito.times(1)).getString(COL_ADDRESS);
        verify(rs, Mockito.times(1)).getString(COL_CITY);
        verify(rs, Mockito.times(1)).getString(COL_COUNTRY);
        verify(rs, Mockito.times(1)).getString(COL_EMAIL);
        verify(rs, Mockito.times(1)).getString(COL_PHONE);

        verifyNoMoreInteractions(rs);
    }
}