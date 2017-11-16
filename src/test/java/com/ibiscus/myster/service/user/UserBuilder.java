package com.ibiscus.myster.service.user;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;

import com.ibiscus.myster.model.security.User;

public class UserBuilder {

    private long id;

    private String username = new RandomStringGenerator.Builder()
        .withinRange('a', 'z').build().generate(5);

    private String password = new RandomStringGenerator.Builder()
        .withinRange('a', 'z').build().generate(5);

    private boolean enabled = RandomUtils.nextBoolean();

    public UserBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public User build() {
        return new User(id, username, password, enabled);
    }
}
