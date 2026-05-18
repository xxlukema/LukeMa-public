package com.learn.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

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
    private LocalDateTime insertTimestamp;

    /** lastUpdateTimestamp */
    @Column(name = "last_update_timestamp", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdateTimestamp;
}
