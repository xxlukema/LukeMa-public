package com.learn.rest.element;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class PropertySummary {

    private PropertyAddress address;

    @JsonProperty("home_id")
    private String homeId;

    private String owner;

    private double value;

}
