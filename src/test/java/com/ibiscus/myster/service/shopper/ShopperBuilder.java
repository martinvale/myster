package com.ibiscus.myster.service.shopper;

import org.apache.commons.text.RandomStringGenerator;

import com.ibiscus.myster.model.shopper.Shopper;

public class ShopperBuilder {

    private long id;

    private long userId;

    private String firstName = new RandomStringGenerator.Builder()
        .withinRange('a', 'z').build().generate(5);

    private String lastName = new RandomStringGenerator.Builder()
        .withinRange('a', 'z').build().generate(5);

    public ShopperBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ShopperBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public ShopperBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ShopperBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Shopper build() {
        return new Shopper(id, userId, lastName, firstName);
    }
}
