package com.learn.rest.element;


import com.fasterxml.jackson.annotation.JsonProperty;


public class PropertySummary {

    private PropertyAddress address;

    @JsonProperty("home_id")
    private String homeId;

    private String owner;

    private double value;

    public PropertyAddress getAddress() {
        return address;
    }

    public void setAddress(PropertyAddress address) {
        this.address = address;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
