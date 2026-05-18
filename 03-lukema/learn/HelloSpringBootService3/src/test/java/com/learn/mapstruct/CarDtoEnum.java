package com.learn.mapstruct;


public enum CarDtoEnum {
    CarDtoEnumOne("one"), CarDtoEnumTwo("two"), CarDtoEnumThree("3"), CarDtoEnumSpecialForCarDto("Special for CarDto");

    CarDtoEnum(String value) {
        this.value = value;
    }

    String value;
}
