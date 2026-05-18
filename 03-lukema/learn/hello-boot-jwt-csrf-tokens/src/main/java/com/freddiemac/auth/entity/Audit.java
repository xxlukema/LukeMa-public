package com.freddiemac.auth.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class Audit {

    /** insertTimestamp */
    @Column(name = "insert_timestamp", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime inserTimestamp;

    /** lastUpdateTimestamp */
    @Column(name = "last_update_timestamp", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdateTimestamp;
}
