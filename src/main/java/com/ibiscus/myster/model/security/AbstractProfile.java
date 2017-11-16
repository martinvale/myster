package com.ibiscus.myster.model.security;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractProfile implements Profile {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_id")
    private long userId;

    protected AbstractProfile(long id ,long userId) {
        this.id = id;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }
}
