package com.learn.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Employee")
@SequenceGenerator(name = "employee_sequence", sequenceName = "employee_id_seq")
//@TableGenerator(name = "TableIdGenerator_Book", table = "Id_Book", pkColumnName = "pk_name", pkColumnValue = "id", valueColumnName = "next_value", allocationSize = 1, initialValue = 1)
public class Employee
    implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.TABLE, generator = "TableIdGenerator_Book")
    private Integer id;

    private String name;

    private Float weight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", weight=" + weight + "]";
    }

}
