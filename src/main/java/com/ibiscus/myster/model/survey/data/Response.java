package com.ibiscus.myster.model.survey.data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "response")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 20)
public abstract class Response {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "assignment_id")
    private long assignmentId;

    @Column(name = "survey_item_id")
    private long surveyItemId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "modification_date")
    private Date modificationDate;

    Response() {}

    public Response(long assignmentId, long surveyItemId) {
        this.assignmentId = assignmentId;
        this.surveyItemId = surveyItemId;
        this.creationDate = new Date();
        this.modificationDate = new Date();
    }

    public Response(long id, long assignmentId, long surveyItemId) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.surveyItemId = surveyItemId;
        this.modificationDate = new Date();
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

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public abstract String getValue();
}
