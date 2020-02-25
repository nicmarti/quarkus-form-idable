package org.lunatech.formidable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FormFieldWithErrorsTest {

    @Test
    @DisplayName("should not have any error when created")
    void shouldCreateAFormFieldWithErrorsWithEmptyListOfErrors() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        assertFalse(tested.hasErrors());
    }

    @Test
    @DisplayName("should store a new error when there is a null String")
    void shouldTestForNullString() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        FormFieldWithErrors updated = tested.nonEmpty("someField", null);
        assertFalse(tested.hasErrors());
        assertTrue(updated.hasErrors());
    }

    @Test
    @DisplayName("should store a new error when there is an empty String")
    void shouldTestForEmptyString() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        FormFieldWithErrors updated = tested.nonEmpty("someField", null);
        assertFalse(tested.hasErrors());
        assertTrue(updated.hasErrors());
    }

    @Test
    @DisplayName("should not raise an error for a valid String")
    void shouldValidateAString() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        FormFieldWithErrors updated = tested.nonEmpty("someField", "someValue");
        assertFalse(updated.hasErrors());
    }

    @Test
    @DisplayName("the List of errors should be read-only")
    void shouldNotUpdateTheInternalListOfErrors() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        tested.nonEmpty("someField", "");
        FormFieldWithErrors updated = tested.nonEmpty("someField", null);
        assertTrue(tested.getErrors().isEmpty());
        assertFalse(updated.getErrors().isEmpty());
    }

    @Test
    @DisplayName("show the details for each error")
    void shouldTestFormError() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        FormFieldWithErrors updated = tested.nonEmpty("someField", null);
        assertFalse(tested.hasErrors());
        FormFieldWithErrors.FormError someError = updated.getErrors().get(0);
        assertEquals("someField", someError.getFieldName());
        assertEquals("someField cannot be empty", someError.getMessage());
    }

    @Test
    @DisplayName("should create a new FormFieldWithErrors")
    void shouldCreateFormField() {
        FormFieldWithErrors tested = FormFieldWithErrors.prepareNew();
        assertFalse(tested.hasErrors());
    }


    @Test
    @DisplayName("return an empty error message if there is no error")
    void shouldReturnAnEmptyErrorMessageForValidField() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        FormFieldWithErrors updated = tested.nonEmpty("someField", "some data");
        assertEquals("", updated.getErrorMessage());
    }

    @Test
    @DisplayName("return a valid error message with details when there is one error")
    void shouldReturnAValidStringMessageForErrors() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        FormFieldWithErrors updated = tested.nonEmpty("someField", null);
        assertFalse(updated.getErrorMessage().isEmpty());
    }

    @Test
    @DisplayName("the toString method return no details if there is no errors")
    void shouldReturnAsStringWithNoErrors() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        FormFieldWithErrors updated = tested.nonEmpty("someField", "some data");
        assertEquals("FormFieldWithErrors{}", updated.toString());
    }

        @Test
    @DisplayName("the toString method return the errors")
    void shouldShowSomeErrors() {
        FormFieldWithErrors tested = new FormFieldWithErrors();
        FormFieldWithErrors updated = tested.nonEmpty("someField", null);
        assertNotEquals("FormFieldWithErrors{}", updated.toString());
    }

}
