package com.ibiscus.myster.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "country")
public class Country {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    Country() {}

    public Country(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
