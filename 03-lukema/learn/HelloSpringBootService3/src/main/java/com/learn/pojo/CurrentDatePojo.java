package com.learn.pojo;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor  /** Needed by RestTemplate tests */
@AllArgsConstructor /** Needed by @ConstructorResult */
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

}
