package com.learn.boot.jdbc.pojo;


import java.io.Serializable;
import java.util.Date;


public class SysDateRow
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date sysDate;

    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    @Override
    public String toString() {
        return "SysDateRow [sysDate=" + sysDate + ", note=" + note + "]";
    }

}
