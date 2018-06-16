package com.companies.repository;

import com.companies.model.internal.CompanyInternal;
import com.companies.util.RepositoryTest;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Optional;
import java.util.UUID;

import static com.companies.model.internal.CompanyInternalBuilder.companyInternalBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@RepositoryTest(CompanyRepositoryImpl.class)
public class CompanyRepositoryImplITest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JdbcTemplate jdbc;

    @After
    public void wipeDb() {
        JdbcTestUtils.deleteFromTables(jdbc, "beneficial_owners");
        JdbcTestUtils.deleteFromTables(jdbc, "companies");
    }

    @Test
    public void shouldInsertUpdateGetAndDelete() {
        UUID id = UUID.randomUUID();
        CompanyInternal expectedObject = companyInternalBuilder().companyId(id).build();

        companyRepository.saveCompany(expectedObject);

        Optional<CompanyInternal> actualObject = companyRepository.findCompanyById(id);

        assertThat(actualObject.get()).isEqualTo(expectedObject);
        assertThat(actualObject.toString()).contains(expectedObject.toString());

        CompanyInternal expectedObject2 = companyInternalBuilder().companyId(id).city("London").build();

        companyRepository.updateCompany(expectedObject2);

        Optional<CompanyInternal> actualObject2 = companyRepository.findCompanyById(id);

        assertThat(actualObject2.get()).isEqualTo(expectedObject2);
        assertThat(actualObject2.toString()).contains(expectedObject2.toString());

        companyRepository.deleteCompanyByCompanyId(id);

        actualObject = companyRepository.findCompanyById(id);

        assertThat(actualObject).isNotPresent();
    }
}