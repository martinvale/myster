package com.ibiscus.myster.model.survey.data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "OpenResponse")
@DiscriminatorValue("OPEN")
public class OpenResponse extends Response {

    @Column(name = "value", length = 2000, nullable = false)
    private String value;

    OpenResponse() {}

    public OpenResponse(long assignmentId, long surveyItemId, String value) {
        super(assignmentId, surveyItemId);
        this.value = value;
    }

    public OpenResponse(long id, long assignmentId, long surveyItemId, String value) {
        super(id, assignmentId, surveyItemId);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
