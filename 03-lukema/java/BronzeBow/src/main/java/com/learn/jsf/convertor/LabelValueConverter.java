package com.learn.jsf.convertor;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.learn.jsf.bean.LabelValue;


@FacesConverter(forClass = LabelValue.class)
public class LabelValueConverter
    implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return LabelValue.toMe(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
