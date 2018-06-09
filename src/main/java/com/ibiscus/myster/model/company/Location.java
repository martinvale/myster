package com.ibiscus.myster.model.company;

import javax.persistence.*;

@Entity(name = "location")
public class Location {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "address", length = 1000, nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    Location() {
    }

    public Location(String address, State state) {
        this.address = address;
        this.state = state;
    }

    public String getAddress() {
        return address;
    }


    public State getState() {
        return state;
    }
}
