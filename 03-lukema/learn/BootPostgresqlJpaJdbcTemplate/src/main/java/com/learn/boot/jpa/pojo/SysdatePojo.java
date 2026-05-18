package com.learn.boot.jpa.pojo;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * sysdate or sysDate does not matter because @ConstructorResult will be used for column name to pojo property mapping.
 * The order of @ColumnResult must match the constructor argument order.
 *
 */
public class SysdatePojo {

    /**
     * @JsonProperty is needed by RestTemplate tests
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date sysdate;

    /**
     * @JsonProperty is needed by RestTemplate tests
     */
    @JsonProperty
    private String note;

    /**
     * Needed by @ConstructorResult
     */
    public SysdatePojo(Date sysdate, String note) {
        this.sysdate = sysdate;
        this.note = note;
    }

    /**
     * Needed by RestTemplate tests
     */
    public SysdatePojo() {
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getSysdate() {
        return sysdate;
    }

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }

    @Override
    public String toString() {
        return "SysdatePojo [sysdate=" + sysdate + ", note=" + note + "]";
    }

}
