package com.coffee.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //indicate see by public or members
    private String type;

    private String body;
}
