package com.learn.jpmc.kunhacker3;


public class Contact {

    private String name;
    private long nationalId;

    public Contact(String name, long nationalId) {
        super();
        this.name = name;
        this.nationalId = nationalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNationalId() {
        return nationalId;
    }

    public void setNationalId(long nationalId) {
        this.nationalId = nationalId;
    }

}
