package com.companies.service;

import com.companies.model.BeneficialOwnerJson;
import com.companies.model.CompanyJson;
import com.companies.model.internal.BeneficialOwnerInternal;
import com.companies.model.internal.CompanyInternal;
import com.companies.repository.BeneficialOwnerRepository;
import com.companies.repository.CompanyRepository;
import com.companies.transform.CompanyJsonTransformer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * The Implementation of the Service to process requests from the Company REST Controller.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOG = getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    private final BeneficialOwnerRepository beneficialOwnerRepository;

    private final CompanyJsonTransformer transformer;

    @Autowired
    public CompanyServiceImpl(final CompanyRepository companyRepository,
                              final BeneficialOwnerRepository beneficialOwnerRepository,
                              final CompanyJsonTransformer transformer) {
        this.companyRepository = companyRepository;
        this.beneficialOwnerRepository = beneficialOwnerRepository;
        this.transformer = transformer;
    }

    @Override
    public CompanyJson createCompany(final CompanyJson company) {
        LOG.info("Creating company {}", company.getCompanyId());
        if (companyRepository.findCompanyById(company.getCompanyId()).isPresent()) {
            return null;
        }
        companyRepository.saveCompany(transformer.toCompanyInternal(company.getCompanyId(), company));
        company.getBeneficialOwners().forEach(
                bo -> beneficialOwnerRepository.saveBeneficialOwner(
                        new BeneficialOwnerInternal(company.getCompanyId(), bo)));
        return company;
    }

    @Override
    public CompanyJson getCompany(final UUID id) {
        final Optional<CompanyInternal> company = companyRepository.findCompanyById(id);
        if (company.isPresent()) {
            final List<BeneficialOwnerInternal> beneficialOwners =
                    beneficialOwnerRepository.findBeneficialOwnerById(id);
            return transformer.toCompanyJson(company.get(), beneficialOwners);
        }
        return null;
    }

    @Override
    public boolean updateCompany(final UUID id, final CompanyJson company) {
        LOG.info("Updating company {}", id);
        final Optional<CompanyInternal> companyOpt = companyRepository.findCompanyById(id);
        if (companyOpt.isPresent()) {
            companyRepository.updateCompany(transformer.toCompanyInternal(id, company));
            beneficialOwnerRepository.deleteAllBeneficialOwnersById(id);
            company.getBeneficialOwners().forEach(
                    bo -> beneficialOwnerRepository.saveBeneficialOwner(
                            new BeneficialOwnerInternal(company.getCompanyId(), bo)));
            return true;
        }

        return false;
    }

    @Override
    public boolean addBeneficialOwner(final UUID companyId, final BeneficialOwnerJson beneficialOwner) {
        LOG.info("Adding beneficial owner {} to the company {}", beneficialOwner.getOwnerName(), companyId);
        final Optional<CompanyInternal> companyOpt = companyRepository.findCompanyById(companyId);
        if (companyOpt.isPresent()) {
            final List<BeneficialOwnerInternal> beneficialOwners =
                    beneficialOwnerRepository.findBeneficialOwnerById(companyId);
            final BeneficialOwnerInternal beneficialOwnerInternal =
                    new BeneficialOwnerInternal(companyId, beneficialOwner.getOwnerName());
            if (!beneficialOwners.contains(beneficialOwnerInternal)) {
                beneficialOwnerRepository.saveBeneficialOwner(beneficialOwnerInternal);
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteCompany(final UUID companyId) {
        LOG.info("Deleting company {}", companyId);
        final int itemsDeleted = companyRepository.deleteCompanyByCompanyId(companyId);
        return itemsDeleted != 0;
    }

    @Override
    public List<CompanyJson> listCompanys() {
        LOG.info("Getting all companies");
        return companyRepository.findAllCompanies().stream().map(c -> transformer.toCompanyJson(c,
                beneficialOwnerRepository.findBeneficialOwnerById(c.getCompanyId()))).collect(Collectors.toList());
    }
}