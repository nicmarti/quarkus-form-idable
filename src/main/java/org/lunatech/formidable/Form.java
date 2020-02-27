package org.lunatech.formidable;


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

    // Please keep those methods as protected.
    // We don't want those methods as part of the Qute ValueResolver that might be generated
    // if our user import this Form as part of there Qute template
    protected String getActionURI() {
        return actionURI;
    }

    // Please keep those methods as protected.
    // We don't want those methods as part of the Qute ValueResolver that might be generated
    // if our user import this Form as part of there Qute template
    protected FormFieldWithErrors getFormFieldWithErrors() {
        return formFieldWithErrors;
    }

    // Please keep those methods as protected.
    // We don't want those methods as part of the Qute ValueResolver that might be generated
    // if our user import this Form as part of there Qute template
    protected FieldMapper getFieldMapper() {
        return fieldMapper;
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

}
