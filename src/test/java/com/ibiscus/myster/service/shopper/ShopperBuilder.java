package com.ibiscus.myster.service.shopper;

import org.apache.commons.text.RandomStringGenerator;

import com.ibiscus.myster.model.shopper.Shopper;

public class ShopperBuilder {

    private long id;

    private long userId;

    public ShopperBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ShopperBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public Shopper build() {
        return new Shopper(id, userId);
    }
}
