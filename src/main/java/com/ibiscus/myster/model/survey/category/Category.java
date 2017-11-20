package com.ibiscus.myster.model.survey.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "survey_id")
    private long surveyId;

    private String name;

    private int position;

    Category() {
    }

    public Category(long surveyId, String name, int position) {
        this.surveyId = surveyId;
        this.name = name;
        this.position = position;
    }

    public Category(long id, long surveyId, String name, int position) {
        this.id = id;
        this.surveyId = surveyId;
        this.name = name;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
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
        Category other = (Category) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
