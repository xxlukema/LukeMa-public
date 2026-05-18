package com.learn.aaa;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class EnumTest {

    @Test
    public void testEnum() {
        log.debug(() -> "Begin test.");

        log.debug("One: {}, One.name(): {}, One.value: {}", MyEnum.One, MyEnum.One.name(), MyEnum.One.value);
        log.debug("TWO: {}, TWO.name(): {}, TWO.value: {}", MyEnum.TWO, MyEnum.TWO.name(), MyEnum.TWO.value);
        log.debug("NEW_ENUM: {}, NEW_ENUM.name(): {}, NEW_ENUM.value: {}", MyEnum.NEW_ENUM, MyEnum.NEW_ENUM.name(), MyEnum.NEW_ENUM.value);

        log.debug(() -> "End test.");
    }

}


enum MyEnum {
    One("One"), TWO("Two"), NEW_ENUM("NewEnum2");

    MyEnum(String value) {
        this.value = value;
    }

    String value;
}
