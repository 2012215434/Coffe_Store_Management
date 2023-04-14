package com.coffee.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String surName;

    private String phone;

    private String email;

    private Date dateJoined;

    private Double spendToDate;

    private Double accountBalance;

    private String address;

    private Integer userType;

    private Double latitude;

    private Double longitude;

    private Integer easting;

    private Integer northing;

    @Column(columnDefinition = "VARCHAR(45) DEFAULT '888888'")
    @ColumnDefault("888888")
    private String password;

}
