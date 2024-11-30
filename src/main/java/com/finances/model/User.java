package com.finances.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Пользователь приложения
 */
@Data
@Entity
@Table(name = "tbl_user")
public class User {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    /**
     * Имя, которое видит пользователь
     */
    @Column(name = "name")
    private String name;

    public User() {}

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof User user))
            return false;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
