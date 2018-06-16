package com.companies.error.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class Errors {

    private final List<RequestError> errors;

    @JsonCreator
    public Errors(final List<RequestError> errors) {
        this.errors = Optional.ofNullable(errors).orElse(emptyList());
    }

    public List<RequestError> getErrors() { return errors; }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Errors errors1 = (Errors) o;
        return Objects.equals(errors, errors1.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("errors", errors).toString();
    }
}