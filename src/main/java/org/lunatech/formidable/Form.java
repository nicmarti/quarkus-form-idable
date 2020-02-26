package org.lunatech.formidable;

import io.quarkus.qute.RawString;
import io.quarkus.qute.TemplateExtension;

import java.util.stream.Collectors;

import static io.quarkus.qute.TemplateExtension.ANY;

/**
 * This is a simple DTO to store why the form was not validated.
 * We can use this with a 400 Bad Request and re-render the template.
 * I tried to mimic Play2 Java Form in a very simplistic way.
 * <p>
 * Other ideas : we could specify the list of fields with errors, and the reason for each field.
 * <p>
 * But at the end, this would be more or less like Play2 Form and FormFactory with a ValidationError.
 * Maybe one day.
 *
 * @author Nicolas Martignole
 */
@TemplateExtension
public class Form {
    private String actionURI;
    private FormFieldWithErrors formFieldWithErrors;
    private FieldMapper fieldMapper;

    public Form(final String actionURI, final FormDTO dto, final FormFieldWithErrors formFieldWithErrors) {
        this.actionURI = actionURI;
        if (dto == null) {
            this.fieldMapper = new FieldMapper();
        } else {
            this.fieldMapper = FieldMapper.parse(dto);
        }
        this.formFieldWithErrors = formFieldWithErrors;
    }

    public Form(final String actionURI, final FormDTO dto) {
        this(actionURI, dto, null);
    }

    public Form(final String actionURI) {
        this(actionURI, null, null);
    }


    public String getActionURI() {
        return actionURI;
    }

    /**
     * I had to write this because we cannot test if the formErrors instance is defined or not in Qute.
     * <p>
     * I tried this :
     * <p>
     * #{if formErrors}
     * <div class="alert alert-danger">
     * formErrors.reason
     * </div>
     * {/if}
     *
     * <p>
     * But it will print "NOT_FOUND" in the HTML page
     * <p>
     * I also tried to use the elvis operator. But the div with alert would be print (and I don't want to)
     * <div class="alert alert-danger">
     * #{formErrors.reason ?: ''}
     * </div>
     *
     * @return a Quarkus RawString
     */
    public io.quarkus.qute.RawString getRenderIfErrors() {
        if (formFieldWithErrors != null && formFieldWithErrors.hasErrors()) {
            String listOfErrors = formFieldWithErrors.getErrors().stream().map(error -> "field: " + error.getFieldName() + " error: " + error.getMessage() + "<br>").collect(Collectors.joining());
            String r = "<div class=\"alert alert-danger\">" + listOfErrors + "</div>";
            return new RawString(r);
        } else {
            return new RawString("");
        }
    }

    public boolean hasErrors() {
        if (formFieldWithErrors != null) {
            return formFieldWithErrors.hasErrors();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Form{" +
                "actionURI='" + actionURI + '\'' +
                ", formFieldWithErrors=" + formFieldWithErrors +
                ", fieldMapper=" + fieldMapper +
                ", fieldMapper.isEmpty=" + fieldMapper.isEmpty() +
                '}';
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @TemplateExtension(matchName = ANY)
    public static boolean fieldHasError(Form form, String fieldName) {
        if (form.formFieldWithErrors != null) {
            return form.formFieldWithErrors.getErrors().stream().anyMatch(e -> e.getFieldName().equals(fieldName));
        } else {
            return false;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @TemplateExtension(matchName = ANY)
    public static io.quarkus.qute.RawString fieldValue(Form form, String fieldName) {
        if (form.fieldMapper != null) {
            System.out.println("fieldMapper for fieldName " + fieldName);
            return form.fieldMapper.getValue(fieldName).map(v -> new RawString(v)).orElse(new RawString(""));
        } else {
            System.out.println("err form.fieldMapper is null");
            return new RawString("");
        }
    }
}
