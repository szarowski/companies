package com.companies.transform;

import com.companies.model.CompanyJson;
import com.companies.model.internal.BeneficialOwnerInternal;
import com.companies.model.internal.CompanyInternal;

import java.util.List;
import java.util.UUID;

/**
 * CompanyJson to CompanyInternal (and vice versa) transformer.
 */
public interface CompanyJsonTransformer {

    /**
     * Transforms a CompanyJson element with id as UUID to a new CompanyInternal internal element.
     *
     * @param companyId the ID of the CompanyJson object
     * @param company the CompanyJson object
     * @return the CompanyInternal object
     */
    CompanyInternal toCompanyInternal(UUID companyId, CompanyJson company);

    /**
     * Transforms a CompanyInternal and BeneficialOwnerInternals to a new CompanyJson element.
     *
     * @param company the CompanyInternal object
     * @param beneficialOwners the List<BeneficialOwnerInternal> object
     * @return the CompanyJson object
     */
    CompanyJson toCompanyJson(CompanyInternal company, List<BeneficialOwnerInternal> beneficialOwners);
}