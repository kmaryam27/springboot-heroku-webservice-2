package com.galvanize.simplegitarapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String guitarModel;

    public User(@NotNull String name, String guitarModel) {
        this.name = name;
        this.guitarModel = guitarModel;
    }
    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuitarModel() {
        return guitarModel;
    }

    public void setGuitarModel(String guitarModel) {
        this.guitarModel = guitarModel;
    }
}
