package com.companies.controller;

import com.companies.config.TestRestConfig;
import com.companies.error.model.Errors;
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

import static com.companies.model.CompanyJsonBuilder.companyJsonBuilder;
import static com.companies.util.IntegrationTestHelper.apiUrl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.RequestEntity.delete;
import static org.springframework.http.RequestEntity.patch;
import static org.springframework.http.RequestEntity.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestRestConfig.class)
public class CompanyControllerDeleteITest {

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
    public void shouldDeleteCompanyData() {
        CompanyJson company = companyJsonBuilder().build();

        restTemplate.exchange(post(apiUrl("/v1/companies", port)).body(company), CompanyJson.class);

        ResponseEntity<Void> response = restTemplate.exchange(
                delete(apiUrl("/v1/companies/" + company.getCompanyId(), port)).build(), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();

        int dataCount = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM companies WHERE company_id = ?", int.class, company.getCompanyId());
        assertThat(dataCount).isEqualTo(0);
        dataCount  = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM beneficial_owners WHERE company_id  = ?", int.class, company.getCompanyId());
        assertThat(dataCount).isEqualTo(0);
    }

    @Test
    public void shouldReturn404ForWrongPath() {
        ResponseEntity<Errors> response = restTemplate.exchange(
                delete(apiUrl("/v1/incorrect/" + Random.uuid(), port)).build(), Errors.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturn404ForCompanyNotFound() {
        ResponseEntity<Void> response = restTemplate.exchange(
                delete(apiUrl("/v1/companies/" + Random.uuid(), port)).build(), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturn405ForForInvalidMethod() {
        ResponseEntity<Errors> response = restTemplate.exchange(
                patch(apiUrl("/v1/companies/" + Random.uuid(), port)).build(), Errors.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }
}