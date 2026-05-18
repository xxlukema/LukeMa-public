package com.learn.jsf.validator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("stockSymbolValidator")
public class StockSymbolValidator
   extends ValidatorBase
{
   public static final Pattern REGEXP_SYMBOL = Pattern.compile("^[\\^]{0,1}[a-zA-Z0-9]{1,5}[\\.]{0,1}[A-Za-z0-9]{0,3}$");

   private static final String ERROR_MSG     = "Invalid stock symbol.";

   public void validate(FacesContext context, UIComponent component, Object value)
      throws ValidatorException
   {
      super.validate(context, component, value);

      String symbol = value.toString();
      Matcher matcher = REGEXP_SYMBOL.matcher(symbol);

      if (!matcher.matches() || symbol.length() == 0 || symbol.length() > 9)
      {
         super.throwValidatorException(ERROR_MSG);
      }

      int dotPos = symbol.indexOf('.');
      if (dotPos > -1)
      {
         if (dotPos == symbol.length() - 1 || dotPos == 0 || symbol.startsWith("^."))
         {
            super.throwValidatorException(ERROR_MSG);
         }
      }
   }

}
