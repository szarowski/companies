package com.companies.service;

import com.companies.model.BeneficialOwnerJson;
import com.companies.model.CompanyJson;

import java.util.List;
import java.util.UUID;

/**
 * The Service to process requests from the Company REST Controller.
 */
public interface CompanyService {

    /**
     * Create and store a CompanyJson element in the database.
     *
     * @param company the CompanyJson input data
     * @return the CompanyJson object created in case of success.
     */
    CompanyJson createCompany(CompanyJson company);

    /**
     * Retrieve a CompanyJson from the database based on the provided ID.
     *
     * @param companyId the ID of the CompanyJson element
     * @return the CompanyJson from the database based on the provided ID.
     */
    CompanyJson getCompany(UUID companyId);

    /**
     * Update a CompanyJson in the database based on the provided ID.
     *
     * @param companyId the ID of the CompanyJson element
     * @param company the CompanyJson from the database based on the provided ID.
     * @return true if update is successful, false otherwise
     */
    boolean updateCompany(UUID companyId, CompanyJson company);

    /**
     * Add a BeneficialOwner name to the Company on the provided ID.
     *
     * @param companyId the ID of the CompanyJson element
     * @param beneficialOwner the beneficial owner name to be added to the database
     * @return true if update is successful, false otherwise
     */
    boolean addBeneficialOwner(UUID companyId, BeneficialOwnerJson beneficialOwner);

    /**
     * Delete a CompanyJson in the database based on the provided ID.
     *
     * @param companyId the ID of the CompanyJson element
     * @return true if delete is successful, false otherwise
     */
    boolean deleteCompany(UUID companyId);

    /**
     * Retrieve all companies from the database.
     *
     * @return the list of all CompanyJson elements in the database
     */
    List<CompanyJson> listCompanys();
}