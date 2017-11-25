package com.ibiscus.myster.model.survey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "survey")
public class Survey {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private boolean enabled;

    Survey() {
    }

    public Survey(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public Survey(long id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Survey other = (Survey) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
