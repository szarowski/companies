package com.companies.repository;

import com.companies.model.internal.CompanyInternal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Company Repository providing persistence services for CompanyController.
 */
public interface CompanyRepository {

    /**
     * Saves the CompanyInternal into the database.
     *
     * @param company the CompanyInternal object to save
     */
    void saveCompany(CompanyInternal company);

    /**
     * Finds a CompanyInternal in the database.
     *
     * @param companyId the ID to find as UUID
     * @return the Optional<CompanyInternal> object
     */
    Optional<CompanyInternal> findCompanyById(UUID companyId);

    /**
     * Finds the List<CompanyInternal> of all company elements.
     *
     * @return the List<CompanyInternal> of all company
     */
    List<CompanyInternal> findAllCompanies();

    /**
     * Updates the CompanyInternal in the database.
     *
     * @param company the CompanyInternal object to update
     */
    void updateCompany(CompanyInternal company);

    /**
     * Deletes the CompanyInternal element from the database.
     *
     * @param companyId the ID of the element to delete as UUID
     * @return A number of elements deleted
     */
    int deleteCompanyByCompanyId(UUID companyId);
}