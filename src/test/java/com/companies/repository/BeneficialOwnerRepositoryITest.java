package com.companies.repository;

import com.companies.model.internal.BeneficialOwnerInternal;
import com.companies.model.internal.CompanyInternal;
import com.companies.util.RepositoryTest;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;
import java.util.UUID;

import static com.companies.model.internal.BeneficialOwnerInternalBuilder.beneficialOwnerInternalBuilder;
import static com.companies.model.internal.CompanyInternalBuilder.companyInternalBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@RepositoryTest({ CompanyRepositoryImpl.class, BeneficialOwnerRepositoryImpl.class })
public class BeneficialOwnerRepositoryITest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BeneficialOwnerRepository beneficialOwnerRepository;

    @Autowired
    private JdbcTemplate jdbc;

    @After
    public void wipeDb() {
        JdbcTestUtils.deleteFromTables(jdbc, "beneficial_owners");
        JdbcTestUtils.deleteFromTables(jdbc, "companies");
    }

    @Test
    public void shouldInsertSelectAndDelete() {
        UUID id = UUID.randomUUID();
        CompanyInternal companyInternal = companyInternalBuilder().companyId(id).build();

        companyRepository.saveCompany(companyInternal);

        BeneficialOwnerInternal expectedObject = beneficialOwnerInternalBuilder().companyId(id).build();

        beneficialOwnerRepository.saveBeneficialOwner(expectedObject);

        List<BeneficialOwnerInternal> actualObjects = beneficialOwnerRepository.findBeneficialOwnerById(id);

        assertThat(actualObjects).isNotEmpty();
        assertThat(actualObjects.toString()).contains(expectedObject.toString());

        beneficialOwnerRepository.deleteAllBeneficialOwnersById(id);

        actualObjects = beneficialOwnerRepository.findBeneficialOwnerById(id);

        assertThat(actualObjects).isEmpty();
    }
}