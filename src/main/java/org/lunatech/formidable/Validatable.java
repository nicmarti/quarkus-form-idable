package org.lunatech.formidable;

import io.vavr.control.Either;

/**
 * A marker interface to denote that a DTO is validatable and able to return the Entity
 *
 * @param <T> is the entity to build if successful
 */
public interface Validatable<T> {

    /**
     * Performs a validation, returns an Either with 2 cases :
     * - A Left with errors if the entity was not validated
     * - A Right with the sub entity, ready to be persisted
     * @return an Either with list of errors or the validated Entry.
     */
    Either<FormFieldWithErrors, T> valid();
}
