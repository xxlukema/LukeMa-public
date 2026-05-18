package com.learn.pojo;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CurrentDatePojo {

    /**
     * @JsonProperty is needed by RestTemplate tests
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date date;

    /**
     * @JsonProperty is needed by RestTemplate tests
     */
    @JsonProperty
    private String note;

    /**
     * Needed by @ConstructorResult
     */
    public CurrentDatePojo(Date date, String note) {
        this.date = date;
        this.note = note;
    }

    /**
     * Needed by RestTemplate tests
     */
    public CurrentDatePojo() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CurrentDate [date=" + date + ", note=" + note + "]";
    }
}
