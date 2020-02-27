package org.lunatech.formidable;

import io.quarkus.qute.RawString;
import io.quarkus.qute.TemplateExtension;

import java.util.stream.Collectors;

/**
 * Extension methods can be used to extend the data classes with new functionality.
 * We cannot mix a static method that is annotated with TemplateExtension in the class Form, since this class
 * is already annotated with TemplateExtension. Else, the Form Value resolver will return NOT_FOUND.
 *
 * @author nmartignole
 */
@TemplateExtension
public class FormTemplateExtension {

    public static final RawString RAW_STRING = new RawString("");

    public static io.quarkus.qute.RawString renderIfErrors(Form form) {
        if (form.getFormFieldWithErrors() != null && form.getFormFieldWithErrors().hasErrors()) {
            String listOfErrors = form.getFormFieldWithErrors().getErrors().stream().map(error -> "field: " + error.getFieldName() + " error: " + error.getMessage() + "<br>").collect(Collectors.joining());
            String r = "<div class=\"alert alert-danger\">" + listOfErrors + "</div>";
            return new RawString(r);
        } else {
            return RAW_STRING;
        }
    }

    public static boolean isFieldInvalid(Form form, String fieldName) {
        if (form.getFormFieldWithErrors() != null) {
            return form.getFormFieldWithErrors().getErrors().stream().anyMatch(e -> e.getFieldName().equals(fieldName));
        } else {
            return false;
        }
    }

    public static io.quarkus.qute.RawString fieldValue(Form form, String fieldName) {
        if (form.getFieldMapper() != null) {
            return form.getFieldMapper().getValue(fieldName).map(v -> new RawString(v)).orElse(RAW_STRING);
        } else {
            return RAW_STRING;
        }
    }

    public static String actionURI(Form form) {
        return form.getActionURI();
    }

    public static boolean hasErrors(Form form) {
        if (form.getFormFieldWithErrors() != null) {
            return form.getFormFieldWithErrors().hasErrors();
        }
        return false;
    }

}
