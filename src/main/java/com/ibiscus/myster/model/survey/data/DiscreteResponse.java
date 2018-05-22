package com.ibiscus.myster.model.survey.data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "DiscreteResponse")
@DiscriminatorValue("DISCRETE")
public class DiscreteResponse extends Response {

    @Column(name = "choice_id")
    private long choiceId;

    DiscreteResponse() {}

    public DiscreteResponse(long assignmentId, long surveyItemId, long choiceId) {
        super(assignmentId, surveyItemId);
        this.choiceId = choiceId;
    }

    public DiscreteResponse(long id, long assignmentId, long surveyItemId, long choiceId) {
        super(id, assignmentId, surveyItemId);
        this.choiceId = choiceId;
    }

    @Override
    public String getValue() {
        return String.valueOf(choiceId);
    }
}
