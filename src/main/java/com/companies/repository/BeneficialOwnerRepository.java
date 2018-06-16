package com.companies.repository;

import com.companies.model.internal.BeneficialOwnerInternal;

import java.util.List;
import java.util.UUID;

/**
 * BeneficialOwner Repository providing persistence services for BeneficialOwnerController.
 */
public interface BeneficialOwnerRepository {

    /**
     * Saves the BeneficialOwner String into the database.
     *
     * @param beneficialOwner the BeneficialOwnerInternal object to save
     */
    void saveBeneficialOwner(BeneficialOwnerInternal beneficialOwner);

    /**
     * Finds a BeneficialOwner String in the database by companyId.
     *
     * @param companyId the ID to find as UUID
     * @return the list of BeneficialOwnerInternal objects
     */
    List<BeneficialOwnerInternal> findBeneficialOwnerById(UUID companyId);

    /**
     * Deletes all the BeneficialOwner Strings from the database by companyId.
     *
     * @param companyId the ID of the company to delete benefitial owners from (as UUID)
     * @return A number of elements deleted
     */
    int deleteAllBeneficialOwnersById(UUID companyId);
}