package com.learn.rest.element;


import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public String toString() {
        return "RentProperty [id=" + id + ", summary=" + summary + ", details=" + details + ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated + "]";
    }

}
