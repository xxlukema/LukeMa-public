package com.learn.jsf.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator extends ValidatorBase {
	private static final Pattern REGEXP_SYMBOL = Pattern
			.compile(".+@.+\\.[a-z]+");

	private static final String ERROR_MSG = "Invalid email.";

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		super.validate(context, component, value);

		String symbol = value.toString();
		Matcher matcher = REGEXP_SYMBOL.matcher(symbol);

		if (!matcher.matches()) {
			super.throwValidatorException(ERROR_MSG);
		}
	}

}
