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

