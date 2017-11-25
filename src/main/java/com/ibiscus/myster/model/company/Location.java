package com.ibiscus.myster.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "location")
public class Location {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "address", length = 1000, nullable = false)
    private String address;

    Location() {
    }

    public Location(long id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public long getId() {
        return id;
    }

}
