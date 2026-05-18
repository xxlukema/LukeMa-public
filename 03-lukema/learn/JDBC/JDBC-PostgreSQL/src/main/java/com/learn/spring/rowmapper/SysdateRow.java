package com.learn.spring.rowmapper;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@Data
public class SysdateRow
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date date;

}
