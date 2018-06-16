package com.companies.transform;

import com.companies.model.CompanyJson;
import com.companies.model.internal.BeneficialOwnerInternal;
import com.companies.model.internal.CompanyInternal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The implementation of the CompanyJson to CompanyInternal (and vice versa) transformer.
 */
@Component
public class CompanyJsonTransformerImpl implements CompanyJsonTransformer {

    @Override
    public CompanyInternal toCompanyInternal(final UUID companyId, final CompanyJson company) {
        return new CompanyInternal(
                companyId,
                company.getName(),
                company.getAddress(),
                company.getCity(),
                company.getCountry(),
                company.getEmail(),
                company.getPhone());
    }

    @Override
    public CompanyJson toCompanyJson(CompanyInternal company, List<BeneficialOwnerInternal> beneficialOwners) {
        return new CompanyJson(
                company.getCompanyId(),
                company.getName(),
                company.getAddress(),
                company.getCity(),
                company.getCountry(),
                company.getEmail(),
                company.getPhone(),
                beneficialOwners.stream().map(BeneficialOwnerInternal::getOwnerName).collect(Collectors.toList()));
    }
}