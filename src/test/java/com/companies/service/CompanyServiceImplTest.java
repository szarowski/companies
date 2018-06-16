package com.companies.service;

import com.companies.model.BeneficialOwnerJson;
import com.companies.model.CompanyJson;
import com.companies.model.internal.BeneficialOwnerInternal;
import com.companies.model.internal.CompanyInternal;
import com.companies.repository.BeneficialOwnerRepository;
import com.companies.repository.CompanyRepository;
import com.companies.transform.CompanyJsonTransformer;
import com.companies.util.Random;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.companies.model.BeneficialOwnerJsonBuilder.beneficialOwnerJsonBuilder;
import static com.companies.model.CompanyJsonBuilder.companyJsonBuilder;
import static com.companies.model.internal.BeneficialOwnerInternalBuilder.beneficialOwnerInternalBuilder;
import static com.companies.model.internal.CompanyInternalBuilder.companyInternalBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest {
    
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private BeneficialOwnerRepository beneficialOwnerRepository;
    @Mock
    private CompanyJsonTransformer companyTransformer;

    private CompanyServiceImpl service;

    @Before
    public void setUp() {
        service = new CompanyServiceImpl(companyRepository, beneficialOwnerRepository, companyTransformer);
    }

    @Test
    public void shouldCreateCompany() {
        CompanyJson company = companyJsonBuilder().build();

        given(companyRepository.findCompanyById(company.getCompanyId())).willReturn(Optional.empty());

        CompanyJson storedData = service.createCompany(company);

        assertThat(storedData).isEqualTo(company);
    }

    @Test
    public void shouldCreateAndGetCompany() {
        UUID id = Random.uuid();
        CompanyJson company = companyJsonBuilder().companyId(id).build();

        given(companyRepository.findCompanyById(company.getCompanyId())).willReturn(Optional.empty());

        CompanyJson storedData = service.createCompany(company);

        assertThat(storedData).isEqualTo(company);

        CompanyInternal companyInternal = companyInternalBuilder().companyId(id).city("London").build();

        BeneficialOwnerInternal beneficialOwnerInternal = beneficialOwnerInternalBuilder().companyId(id).build();

        given(companyRepository.findCompanyById(id)).willReturn(Optional.of(companyInternal));
        given(beneficialOwnerRepository.findBeneficialOwnerById(id)).willReturn(ImmutableList.of(beneficialOwnerInternal));
        given(companyTransformer.toCompanyJson(companyInternal, ImmutableList.of(beneficialOwnerInternal))).willReturn(company);

        CompanyJson actualData = service.getCompany(id);

        assertThat(actualData).isEqualTo(company);
    }

    @Test
    public void shouldCreateSelectAndUpdateCompany() {
        UUID id = Random.uuid();
        CompanyJson company = companyJsonBuilder().companyId(id).build();

        given(companyRepository.findCompanyById(company.getCompanyId())).willReturn(Optional.empty());

        CompanyJson storedData = service.createCompany(company);

        assertThat(storedData).isEqualTo(company);

        CompanyJson company2 = companyJsonBuilder().companyId(id).address("London").build();
        CompanyInternal companyInternal = companyInternalBuilder().companyId(id).city("London").build();

        given(companyRepository.deleteCompanyByCompanyId(id)).willReturn(1);

        service.updateCompany(id, company2);

        BeneficialOwnerInternal beneficialOwnerInternal = beneficialOwnerInternalBuilder().companyId(id).build();

        given(companyRepository.findCompanyById(id)).willReturn(Optional.of(companyInternal));
        given(beneficialOwnerRepository.findBeneficialOwnerById(id)).willReturn(ImmutableList.of(beneficialOwnerInternal));
        given(companyTransformer.toCompanyJson(companyInternal, ImmutableList.of(beneficialOwnerInternal))).willReturn(company2);

        CompanyJson actualData = service.getCompany(id);

        assertThat(actualData).isEqualTo(company2);
    }

    @Test
    public void shouldCreateSelectAndDeleteCompany() {
        UUID id = Random.uuid();
        CompanyJson company = companyJsonBuilder().companyId(id).build();

        given(companyRepository.findCompanyById(company.getCompanyId())).willReturn(Optional.empty());

        CompanyJson storedData = service.createCompany(company);

        assertThat(storedData).isEqualTo(company);

        CompanyInternal companyInternal = companyInternalBuilder().companyId(id).city("London").build();

        BeneficialOwnerInternal beneficialOwnerInternal = beneficialOwnerInternalBuilder().companyId(id).build();

        given(companyRepository.findCompanyById(id)).willReturn(Optional.of(companyInternal));
        given(beneficialOwnerRepository.findBeneficialOwnerById(id)).willReturn(ImmutableList.of(beneficialOwnerInternal));
        given(companyTransformer.toCompanyJson(companyInternal, ImmutableList.of(beneficialOwnerInternal))).willReturn(company);

        CompanyJson actualData = service.getCompany(id);

        assertThat(actualData).isEqualTo(company);

        given(companyRepository.deleteCompanyByCompanyId(id)).willReturn(1);

        assertThat(service.deleteCompany(id)).isTrue();
    }

    @Test
    public void shouldCreateAndListCompany() {
        UUID id = Random.uuid();
        CompanyJson company = companyJsonBuilder().companyId(id).build();

        given(companyRepository.findCompanyById(company.getCompanyId())).willReturn(Optional.empty());

        CompanyJson storedData = service.createCompany(company);

        assertThat(storedData).isEqualTo(company);

        CompanyInternal companyInternal = companyInternalBuilder().companyId(id).city("London").build();

        BeneficialOwnerInternal beneficialOwnerInternal = beneficialOwnerInternalBuilder().companyId(id).build();

        given(companyRepository.findAllCompanies()).willReturn(ImmutableList.of(companyInternal));
        given(beneficialOwnerRepository.findBeneficialOwnerById(id)).willReturn(ImmutableList.of(beneficialOwnerInternal));
        given(companyTransformer.toCompanyJson(companyInternal, ImmutableList.of(beneficialOwnerInternal))).willReturn(company);

        List<CompanyJson> actualData = service.listCompanys();

        assertThat(actualData).isNotEmpty();
    }

    @Test
    public void shouldCreateAddBeneficialOwnerAndListCompany() {
        UUID id = Random.uuid();
        CompanyJson company = companyJsonBuilder().companyId(id).build();

        given(companyRepository.findCompanyById(company.getCompanyId())).willReturn(Optional.empty());

        CompanyJson storedData = service.createCompany(company);

        assertThat(storedData).isEqualTo(company);

        CompanyInternal companyInternal = companyInternalBuilder().companyId(id).city("London").build();

        BeneficialOwnerInternal beneficialOwnerInternal = beneficialOwnerInternalBuilder().companyId(id).build();
        BeneficialOwnerJson beneficialOwner = beneficialOwnerJsonBuilder().build();
        BeneficialOwnerInternal beneficialOwnerInternal2 = new BeneficialOwnerInternal(id, beneficialOwner.getOwnerName());

        given(companyRepository.findCompanyById(company.getCompanyId())).willReturn(Optional.of(companyInternal));
        given(beneficialOwnerRepository.findBeneficialOwnerById(id)).willReturn(ImmutableList.of(beneficialOwnerInternal,
                beneficialOwnerInternal2));
        doNothing().when(beneficialOwnerRepository).saveBeneficialOwner(beneficialOwnerInternal2);

        service.addBeneficialOwner(id, beneficialOwner);

        CompanyJson company2 = companyJsonBuilder().companyId(id).beneficialOwners(ImmutableList.of(beneficialOwnerInternal.getOwnerName(),
                beneficialOwnerInternal2.getOwnerName())).build();

        given(companyRepository.findCompanyById(id)).willReturn(Optional.of(companyInternal));
        given(beneficialOwnerRepository.findBeneficialOwnerById(id)).willReturn(ImmutableList.of(beneficialOwnerInternal,
                beneficialOwnerInternal2));
        given(companyTransformer.toCompanyJson(companyInternal, ImmutableList.of(beneficialOwnerInternal,
                beneficialOwnerInternal2))).willReturn(company2);

        CompanyJson actualData = service.getCompany(id);

        assertThat(actualData).isEqualTo(company2);
    }

    @Test
    public void shouldReturnFalseDeletingNonExistingCompany() {
        UUID id = Random.uuid();

        given(companyRepository.findCompanyById(id)).willReturn(Optional.empty());

        assertThat(service.deleteCompany(id)).isFalse();
    }

    @Test
    public void shouldReturnNullIfNotFoundCompanyById() {
        UUID id = Random.uuid();

        given(companyRepository.findCompanyById(id)).willReturn(Optional.empty());

        CompanyJson actualData = service.getCompany(id);

        assertThat(actualData).isNull();
    }
}