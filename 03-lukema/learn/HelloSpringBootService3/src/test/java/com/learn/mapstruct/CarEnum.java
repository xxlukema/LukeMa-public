package com.learn.mapstruct;


public enum CarEnum {
    CarEnumOne("one"), CarEnumTwo("two"), CarEnumThree("3"), CarEnumForCarOnly("For Car Only");

    CarEnum(String value) {
        this.value = value;
    }

    String value;
}
