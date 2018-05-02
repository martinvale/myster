package com.ibiscus.myster.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;

    @Column(name = "first_name", length = 70)
    private String firstName;

    @Column(name = "last_name", length = 70)
    private String lastName;

    @Column(name = "external_id", length = 30)
    private String externalId;

    private boolean enabled;

    User() {
    }

    public User(long id, String username, String password, String firstName, String lastName,
                String externalId, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.externalId = externalId;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getExternalId() {
        return externalId;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
