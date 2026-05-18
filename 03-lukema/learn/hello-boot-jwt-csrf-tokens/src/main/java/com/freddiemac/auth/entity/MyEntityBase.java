package com.freddiemac.auth.entity;


import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public abstract class MyEntityBase
    implements Serializable {
    private static final long serialVersionUID = 0L;

    public abstract Long getId();

    public abstract void setId(Long id);

    @Embedded
    private Audit audit = new Audit();
}
