package com.companies.controller;

import com.companies.model.BeneficialOwnerJson;
import com.companies.model.CompanyJson;
import com.companies.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Company Controller to handle CRUD operations.
 */
@RestController
@RequestMapping("/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(final CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyJson> createCompany(@Valid @RequestBody final CompanyJson company) {
        final CompanyJson companyCreated = companyService.createCompany(company);
        if (companyCreated == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(companyCreated, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{companyId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CompanyJson> getCompany(@PathVariable("companyId") final UUID companyId) {
        final CompanyJson companyCreated = companyService.getCompany(companyId);
        if (companyCreated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyCreated, HttpStatus.OK);
    }

    @PutMapping(path = "/{companyId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateCompany(@PathVariable("companyId") final UUID companyId,
                                              @Valid @RequestBody final CompanyJson company) {
        final boolean companyUpdated = companyService.updateCompany(companyId, company);
        if (!companyUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCompany(@PathVariable("companyId") final UUID companyId) {
        final boolean companyDeleted = companyService.deleteCompany(companyId);
        if (!companyDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CompanyJson> listCompanies() {
        return companyService.listCompanys();
    }

    @PutMapping(path = "/{companyId}/add_beneficial_owner", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CompanyJson> addBeneficialOwner(@PathVariable("companyId") final UUID companyId,
                                                          @Valid @RequestBody final BeneficialOwnerJson beneficiaryOwner) {
        final CompanyJson companyCreated = companyService.getCompany(companyId);
        if (companyCreated != null) {
            if (companyService.addBeneficialOwner(companyId, beneficiaryOwner)) {
                return new ResponseEntity<>(companyCreated, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}