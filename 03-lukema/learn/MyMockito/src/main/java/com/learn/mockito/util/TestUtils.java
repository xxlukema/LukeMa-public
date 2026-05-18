package com.learn.mockito.util;


import org.mockito.internal.util.reflection.FieldSetter;


public class TestUtils {

    public static void setField(Object testObj, String fieldName, Object fieldValue)
        throws NoSuchFieldException, SecurityException {
        FieldSetter.setField(testObj, testObj.getClass().getDeclaredField(fieldName), fieldValue);
    }

}
