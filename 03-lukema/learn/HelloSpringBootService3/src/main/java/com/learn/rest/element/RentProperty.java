package com.learn.rest.element;


import java.util.Date;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class RentProperty {

    @Id
    private Long id;

    @NotNull(message = "Rent property summary is required.")
    @Size(min = 2, max = 40, message = "Summary field length: 2-40 chars")
    private String summary;

    @NotNull(message = "Rent property details are required.")
    @Size(min = 2, max = 40, message = "Details field length: 2-100 chars")
    private String details;

    private Date dateCreated = new Date();
    private Date dateUpdated = new Date();

}
