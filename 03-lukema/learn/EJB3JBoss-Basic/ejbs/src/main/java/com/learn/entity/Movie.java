package com.learn.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "Movies")
//@SequenceGenerator(name = "movie_sequence", sequenceName = "movie_id_seq")
@TableGenerator(name = "TableIdGenerator_Movie", table = "Id_Movie", pkColumnName = "pk_name", pkColumnValue = "id", valueColumnName = "next_value", allocationSize=1, initialValue=1)
public class Movie
    implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TableIdGenerator_Movie")
    private Integer           id;

    @Column(name = "movie_title", length = 20)
    private String            title;

    private String            director;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date              createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date              updateDate;

    @Override
    public String toString() {
        return "Book: " + getId() + ". Title " + getTitle() + ". Director " + getDirector() + ".";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirector() {
        return director;
    }
}
