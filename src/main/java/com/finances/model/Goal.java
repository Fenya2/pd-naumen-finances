package com.finances.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Цель
 */
@Entity
@Data
@Table(name = "tbl_goal")
public class Goal
{
    public Goal() {}

    public Goal(User user, String name, double amount, Date date) {
        this.owner = user;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class)
    private User owner;

    @Column(unique = true)
    private String name;

    private double amount;

    private Date date;

    @OneToOne(targetEntity = Account.class)
    private Account account;
}
