package com.ibiscus.myster.model.company;

import javax.persistence.*;

@Entity(name = "point_of_sale")
public class PointOfSale {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    PointOfSale() {
    }

    public PointOfSale(long id, String name, String code, Location location) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Location getLocation() {
        return location;
    }
}
