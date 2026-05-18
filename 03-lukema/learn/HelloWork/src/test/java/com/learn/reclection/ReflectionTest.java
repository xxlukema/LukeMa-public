package com.learn.reclection;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ReflectionTest {

    @Test
    public void testMain() {
        log.info("Begin Test.");

        MyPojo myPojo = new MyPojo();
        myPojo.setAddress(",With comma, Inside,");
        myPojo.setCity("Suit,land");

        log.info(myPojo.toString());

        myPojo = fixAllCommas(myPojo);

        log.info(myPojo.toString());

        log.info("End Test.");
    }

    private <T> T fixAllCommas(T obj) {
        if (obj == null) {
            return null;
        }

        Method[] methods = obj.getClass().getMethods();
        for (Method getter : methods) {
            if (Modifier.isPublic(getter.getModifiers()) && getter.getReturnType().equals(String.class) && getter.getParameterCount() == 0
                    && getter.getName().matches("^get[A-Z].*")) {

                String field = getter.getName().substring(3);
                String setterName = "set" + field;

                for (Method setter : methods) {
                    if (setter.getName().equals(setterName) && setter.getParameterCount() == 1 && setter.getParameterTypes()[0].equals(String.class)) {

                        try {
                            String value = (String) getter.invoke(obj);

                            value = value.replaceAll(",", "%2C");

                            setter.invoke(obj, value);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            log.error("Invoking exception", e);
                        }

                        break;
                    }
                }

            }
        }

        return obj;
    }

}
