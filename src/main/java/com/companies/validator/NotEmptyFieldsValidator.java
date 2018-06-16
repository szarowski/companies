package com.companies.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyFieldsValidator implements ConstraintValidator<NotEmptyFields, List<String>> {

    @Override
    public void initialize(final NotEmptyFields notEmptyFields) {
    }

    @Override
    public boolean isValid(final List<String> objects, final ConstraintValidatorContext context) {
        return objects == null || !objects.isEmpty() && objects.stream().noneMatch(nef -> nef.trim().isEmpty());
    }
}