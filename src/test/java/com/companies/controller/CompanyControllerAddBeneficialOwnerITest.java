package com.companies.controller;

import com.companies.config.TestRestConfig;
import com.companies.error.model.Errors;
import com.companies.error.model.RequestError;
import com.companies.model.BeneficialOwnerJson;
import com.companies.model.CompanyJson;
import com.companies.util.Random;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static com.companies.model.BeneficialOwnerJsonBuilder.beneficialOwnerJsonBuilder;
import static com.companies.model.CompanyJsonBuilder.companyJsonBuilder;
import static com.companies.util.IntegrationTestHelper.apiUrl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.patch;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.http.RequestEntity.put;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestRestConfig.class)
public class CompanyControllerAddBeneficialOwnerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @After
    public void wipeDb() {
        jdbcTemplate.update("DELETE FROM beneficial_owners");
        jdbcTemplate.update("DELETE FROM companies");
    }

    @Test
    public void shouldCreateCompanyAddBeneficialOwnerAndSelect() {
        UUID id = Random.uuid();
        CompanyJson companyJson = companyJsonBuilder().companyId(id).build();

        restTemplate.exchange(post(apiUrl("/v1/companies", port)).body(companyJson), CompanyJson.class);

        BeneficialOwnerJson beneficialOwner = beneficialOwnerJsonBuilder().build();

        List<String> beneficialOwners = companyJson.getBeneficialOwners();
        beneficialOwners.add(beneficialOwner.getOwnerName());

        ResponseEntity<Void> response = restTemplate.exchange(
                put(apiUrl("/v1/companies/" + id + "/add_beneficial_owner", port)).body(beneficialOwner), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();

        ResponseEntity<CompanyJson> response2 = restTemplate.exchange(
                get(apiUrl("/v1/companies/" + id, port)).build(), CompanyJson.class);

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody().getCompanyId()).isEqualTo(id);
        assertThat(response2.getBody().getBeneficialOwners()).isEqualTo(beneficialOwners);

        int dataCount = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM companies WHERE company_id = ?", int.class, response2.getBody().getCompanyId());
        assertThat(dataCount).isEqualTo(1);
        dataCount = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM beneficial_owners WHERE company_id  = ?", int.class, response2.getBody().getCompanyId());
        assertThat(dataCount).isEqualTo(2);
    }

    @Test
    public void shouldReturn404CompanyNotFound() {
        CompanyJson companyJson = companyJsonBuilder().build();

        ResponseEntity<Errors> response = restTemplate.exchange(
                put(apiUrl("/v1/incorrect/" + Random.uuid(), port)).body(companyJson), Errors.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturn405ForForInvalidMethod() {
        ResponseEntity<Errors> response = restTemplate.exchange(
                patch(apiUrl("/v1/companies/" + Random.uuid(), port)).contentType(APPLICATION_JSON).body("Howdy!"), Errors.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    public void shouldReturn415ForWrongContentType() {
        ResponseEntity<Errors> response = restTemplate.exchange(
                put(apiUrl("/v1/companies/" + Random.uuid(), port)).contentType(APPLICATION_PDF).build(), Errors.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void shouldReturn422OnValidationErrors() {
        UUID companyId = Random.uuid();
        CompanyJson companyJson = companyJsonBuilder().companyId(companyId).build();

        restTemplate.exchange(post(apiUrl("/v1/companies", port)).body(companyJson), CompanyJson.class);

        BeneficialOwnerJson beneficialOwner1 = beneficialOwnerJsonBuilder().ownerName(null).build();

        ResponseEntity<Errors> responseNoBeneficialOwner = restTemplate.exchange(
                put(apiUrl("/v1/companies/" + companyId + "/add_beneficial_owner", port)).body(beneficialOwner1), Errors.class);


        assertThat(responseNoBeneficialOwner.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(responseNoBeneficialOwner.getBody().getErrors())
                .hasSize(1)
                .contains(
                        new RequestError("NotNull", "owner_name may not be null", "owner_name", null)
                );
    }
}