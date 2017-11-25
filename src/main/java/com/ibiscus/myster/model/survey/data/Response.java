package com.ibiscus.myster.model.survey.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ibiscus.myster.model.survey.item.SingleChoice;

@Entity(name = "response")
public class Response {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "assignment_id")
    private long assignmentId;

    @Column(name = "survey_item_id")
    private long surveyItemId;

    @Column(name = "value", length = 2000, nullable = false)
    private String value;

    Response() {}

    public Response(long assignmentId, long surveyItemId, String value) {
        this.assignmentId = assignmentId;
        this.surveyItemId = surveyItemId;
        this.value = value;
    }

    public Response(long id, long assignmentId, long surveyItemId, String value) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.surveyItemId = surveyItemId;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public long getSurveyItemId() {
        return surveyItemId;
    }

    public String getValue() {
        return value;
    }
}
