package com.companies.controller;

import com.companies.config.TestRestConfig;
import com.companies.error.model.Errors;
import com.companies.error.model.RequestError;
import com.companies.model.CompanyJson;
import com.companies.util.Random;
import com.google.common.collect.ImmutableList;
import org.assertj.core.util.Lists;
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

import java.util.UUID;

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
public class CompanyControllerUpdateITest {

    private static final String INVALID_USER_EMAIL = "xxx_yyy.com";

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
    public void shouldUpdateCompanyData() {
        UUID id = Random.uuid();
        CompanyJson companyJson = companyJsonBuilder().companyId(id).build();

        restTemplate.exchange(post(apiUrl("/v1/companies", port)).body(companyJson), CompanyJson.class);

        CompanyJson companyJson2 = companyJsonBuilder().companyId(id)
                .beneficialOwners(companyJson.getBeneficialOwners()).build();

        ResponseEntity<Void> response = restTemplate.exchange(
                put(apiUrl("/v1/companies/" + id, port)).body(companyJson2), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();

        ResponseEntity<CompanyJson> response2 = restTemplate.exchange(
                get(apiUrl("/v1/companies/" + id, port)).build(), CompanyJson.class);

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody().getCompanyId()).isEqualTo(id);
        assertThat(response2.getBody()).isEqualTo(companyJson2);

        int dataCount = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM companies WHERE company_id = ?", int.class, response2.getBody().getCompanyId());
        assertThat(dataCount).isEqualTo(1);
        dataCount = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM beneficial_owners WHERE company_id  = ?", int.class, response2.getBody().getCompanyId());
        assertThat(dataCount).isEqualTo(1);
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

        CompanyJson company1 = companyJsonBuilder().email(INVALID_USER_EMAIL).companyId(companyId).build();

        ResponseEntity<Errors> responseNoEmail = restTemplate.exchange(
                put(apiUrl("/v1/companies/" + companyId, port)).body(company1), Errors.class);


        assertThat(responseNoEmail.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(responseNoEmail.getBody().getErrors())
                .hasSize(1)
                .contains(
                        new RequestError("Email", "email not a well-formed email address", "email", null)
                );

        CompanyJson company2 = companyJsonBuilder().companyId(null).address(null).city(null).country(null).name(null)
                .email(null).phone(null).beneficialOwners(null).build();

        ResponseEntity<Errors> responseNulls = restTemplate.exchange(
                put(apiUrl("/v1/companies/" + companyId, port)).body(company2), Errors.class);

        assertThat(responseNulls.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(responseNulls.getBody().getErrors())
                .hasSize(6)
                .contains(
                        new RequestError("NotNull", "address may not be null", "address", null),
                        new RequestError("NotNull", "country may not be null", "country", null),
                        new RequestError("NotNull", "city may not be null", "city", null),
                        new RequestError("NotNull", "name may not be null", "name", null),
                        new RequestError("NotNull", "beneficial_owners may not be null", "beneficial_owners", null),
                        new RequestError("NotNull", "company_id may not be null", "company_id", null)
                );

        CompanyJson company3 = companyJsonBuilder().beneficialOwners(Lists.emptyList()).build();

        ResponseEntity<Errors> responseEmptyBeneficialOwners = restTemplate.exchange(
                put(apiUrl("/v1/companies/" + companyId, port)).body(company3), Errors.class);


        assertThat(responseEmptyBeneficialOwners.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(responseEmptyBeneficialOwners.getBody().getErrors())
                .hasSize(1)
                .contains(
                        new RequestError("NotEmptyFields", "beneficial_owners List cannot contain empty fields", "beneficial_owners", null)
                );

        CompanyJson company4 = companyJsonBuilder().beneficialOwners(ImmutableList.of("", "")).build();

        ResponseEntity<Errors> responseEmptyBeneficialOwnerNames = restTemplate.exchange(
                put(apiUrl("/v1/companies/" + companyId, port)).body(company4), Errors.class);


        assertThat(responseEmptyBeneficialOwnerNames.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(responseEmptyBeneficialOwnerNames.getBody().getErrors())
                .hasSize(1)
                .contains(
                        new RequestError("NotEmptyFields", "beneficial_owners List cannot contain empty fields", "beneficial_owners", null)
                );
    }
}