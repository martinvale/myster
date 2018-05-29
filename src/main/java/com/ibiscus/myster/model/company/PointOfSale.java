package com.ibiscus.myster.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "point_of_sale")
public class PointOfSale {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "address", length = 1000, nullable = false)
    private String address;

    PointOfSale() {
    }

    public PointOfSale(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
