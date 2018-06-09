package com.ibiscus.myster.model.company;

import javax.persistence.*;

@Entity(name = "state")
public class State {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    State() {}

    public State(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
