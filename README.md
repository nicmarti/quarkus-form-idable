# Quarkus Form-idable

This project is a Quarkus extension to add support for HTTP form to the [Qute](https://quarkus.io/guides/qute-reference) template engine.

# Controller : how to create a new Form

You can easily create a Form in a new action like this : 

```java
    @GET
    @Path("/new")
    public TemplateInstance prepareNew() {
        return newTimeEntry.data("zeForm", new Form("/times/new"));
    }
```    

In your Qute template you can then use the Form object to render a Form :

```html

            <form id="zeForm" action="{zeForm.actionURI}" method="POST" class="form">

                {form.getRenderIfErrors ?: ''}

                <div class="form-group">
                    {#renderInputText form=zeForm fieldName="description" label="Task description :" placeholder="Enter task description" helpText="Required. Be as descriptive as possible. Explain what you worked on today."}{/renderInputText}
                </div>
              <button type="submit" class="btn btn-success"><i class="icon ion-md-archive" size="small"></i> Save new entry</button>
            </form>
...
              
```              

Here is the code of the renderInput tag (located under src/main/resources/templates/tags folder) 

```html

{#if form.hasErrors}
{#if form.isFieldInvalid(fieldName)}
<label for="{fieldName}" class="text-danger"><i class="icon ion-md-clipboard"></i> {label}</label>
<input id="{fieldName}"
       name="{fieldName}"
       type="text"
       class="form-control is-invalid"
       aria-describedby={fieldName}Help"
       placeholder="{placeholder}"
       value="{form.fieldValue(fieldName)}"
>
<small id="{fieldName}Help" class="form-text text-muted">{helpText}</small>
{#else}
<label for="{fieldName}" class="text-success"><i class="icon ion-md-clipboard"></i> {label}</label>
<input id="{fieldName}"
       name="{fieldName}"
       type="text"
       class="form-control is-valid"
       aria-describedby={fieldName}Help"
       placeholder="{placeholder}"
       value="{form.fieldValue(fieldName)}">
<small id="{fieldName}Help" class="form-text text-muted">{helpText}</small>
{/if}
{#else}
<label for="{fieldName}" class=""><i class="icon ion-md-clipboard"></i> {label}</label>
<input id="{fieldName}"
       name="{fieldName}"
       type="text"
       class="form-control"
       aria-describedby={fieldName}Help"
       placeholder="{placeholder}"
       value="{form.fieldValue(fieldName)}">
<small id="{fieldName}Help" class="form-text text-muted">{helpText}</small>
{/if}
```

# How to implement Form validation and re-render if the Form is not valid ?

The following code in our Controller is supposed to save the TimeEntry bean. 
Before, it will try to validate the bean.

```java 

    @POST
    @Path("/new")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response save(@org.jboss.resteasy.annotations.Form TimeEntryDTO timeEntryDTO) {
        Either<FormFieldWithErrors, TimeEntry> validTimeEntryOrError = validation.validate(timeEntryDTO);

        return validTimeEntryOrError.fold(formErrors -> {
            logger.warn("Unable to persist a TimeEntry. Reason : " + formErrors.getErrorMessage());
            Object htmlContent = newTimeEntry.data("zeForm", new Form("/times/new", timeEntryDTO ,formErrors));
            return Response.status(400, formErrors.getErrorMessage()).entity(htmlContent).build();
        }, newTimeEntry -> {
            timeEntryService.persist(newTimeEntry);
            return Response.seeOther(URI.create("/times")).build();
        });

    }
```

See also https://github.com/nicmarti/quarkus-timekeeper-demo



