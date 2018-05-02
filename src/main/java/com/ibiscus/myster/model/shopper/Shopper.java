package com.ibiscus.myster.model.shopper;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ibiscus.myster.model.security.AbstractProfile;

@Entity(name = "shopper")
public class Shopper extends AbstractProfile {

    Shopper() {
        super(0, 0);
    }

    public Shopper(long id, long userId) {
        super(id, userId);
    }

}
