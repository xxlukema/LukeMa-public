package com.learn.jsf.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("usernameValidator")
public class UsernameValidator extends ValidatorBase {
	private static final Pattern REGEXP_SYMBOL = Pattern.compile(".{1,20}");

	private static final String ERROR_MSG = "Invalid username.";

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		super.validate(context, component, value);

		String username = value.toString();

		if (username.indexOf(' ') > 0) {
			super.throwValidatorException("Username can not contain space.");
		}

		Matcher matcher = REGEXP_SYMBOL.matcher(username);

		if (!matcher.matches()) {
			super.throwValidatorException(ERROR_MSG);
		}
	}

}
