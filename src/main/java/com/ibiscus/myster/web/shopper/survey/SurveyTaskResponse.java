package com.ibiscus.myster.web.shopper.survey;

import java.util.Optional;

public abstract class SurveyTaskResponse {

    private final long surveyItemId;

    public SurveyTaskResponse(long surveyItemId) {
        this.surveyItemId = surveyItemId;
    }

    public long getSurveyItemId() {
        return surveyItemId;
    }

    public abstract Optional<String> getValue();
}
