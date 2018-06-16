package com.companies.model;

import be.joengenduvel.java.verifiers.ToStringVerifier;
import com.companies.error.model.Errors;
import com.companies.error.model.RequestError;
import com.companies.model.internal.BeneficialOwnerInternal;
import com.companies.model.internal.CompanyInternal;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static com.companies.error.model.ErrorsBuilder.errorsBuilder;
import static com.companies.error.model.RequestErrorBuilder.requestErrorBuilder;
import static com.companies.model.BeneficialOwnerJsonBuilder.beneficialOwnerJsonBuilder;
import static com.companies.model.CompanyJsonBuilder.companyJsonBuilder;
import static com.companies.model.internal.BeneficialOwnerInternalBuilder.beneficialOwnerInternalBuilder;
import static com.companies.model.internal.CompanyInternalBuilder.companyInternalBuilder;

public class ModelTest {

    @Test
    public void shouldTestEqualsAndHashCode() {
        EqualsVerifier.forClass(CompanyJson.class);
        EqualsVerifier.forClass(BeneficialOwnerJson.class);

        EqualsVerifier.forClass(CompanyInternal.class);
        EqualsVerifier.forClass(BeneficialOwnerInternal.class);

        EqualsVerifier.forClass(Errors.class);
        EqualsVerifier.forClass(RequestError.class);
    }

    @Test
    public void shouldTestToString() {
        ToStringVerifier.forClass(CompanyJson.class).containsAllPrivateFields(companyJsonBuilder().build());
        ToStringVerifier.forClass(BeneficialOwnerJson.class).containsAllPrivateFields(beneficialOwnerJsonBuilder().build());

        ToStringVerifier.forClass(CompanyInternal.class).containsAllPrivateFields(companyInternalBuilder().build());
        ToStringVerifier.forClass(BeneficialOwnerInternal.class).containsAllPrivateFields(beneficialOwnerInternalBuilder().build());

        ToStringVerifier.forClass(Errors.class).containsAllPrivateFields(errorsBuilder().build());
        ToStringVerifier.forClass(RequestError.class).containsAllPrivateFields(requestErrorBuilder().build());
    }
}