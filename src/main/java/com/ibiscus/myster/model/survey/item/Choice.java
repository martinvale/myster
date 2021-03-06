package com.ibiscus.myster.model.survey.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "choice")
public class Choice {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "survey_item_id")
    private long surveyItemId;

    @Column(name = "description", length = 2000, nullable = false)
    private String description;

    @Column(name = "value", length = 100, nullable = false)
    private Integer value;

    Choice() {
    }

    public Choice(long id, long surveyItemId, String description, Integer value) {
        this.id = id;
        this.surveyItemId = surveyItemId;
        this.description = description;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public long getSurveyItemId() {
        return surveyItemId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getValue() {
        return value;
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
        Choice other = (Choice) obj;
        if (id != other.id)
            return false;
        return true;
    }

}