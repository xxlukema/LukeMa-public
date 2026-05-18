package com.learn.batch;


import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonOut {

    @Id
    private Integer personId;
    private String lastname;
    private String firstname;

}
