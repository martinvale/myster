package com.ibiscus.myster.model.shopper;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ibiscus.myster.model.security.AbstractProfile;

@Entity
public class Shopper extends AbstractProfile {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    Shopper() {
        super(0, 0);
    }

    public Shopper(long id, long userId, String lastName, String firstName) {
        super(id, userId);
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String getDisplayName() {
        return lastName + ", " + firstName;
    }

}
