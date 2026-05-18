package com.learn.jsf.validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


public class ValidatorBase
   implements Validator
{
   public void throwValidatorException(String message)
      throws ValidatorException
   {
      FacesMessage facesMessage = new FacesMessage();
      facesMessage.setSummary(message);
      facesMessage.setDetail(facesMessage.getSummary());
      facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ValidatorException(facesMessage);
   }

   public void validate(FacesContext context, UIComponent component, Object value)
      throws ValidatorException
   {
      if (context == null || component == null)
      {
         throw new NullPointerException();
      }

      if (!(component instanceof UIInput))
      {
         return;
      }
   }
}
