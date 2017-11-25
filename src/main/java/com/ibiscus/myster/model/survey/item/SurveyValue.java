package com.ibiscus.myster.model.survey.item;

public class SurveyValue {

    private final long surveyItemId;
    private final String value;

    public SurveyValue(long surveyItemId, String value) {
        this.surveyItemId = surveyItemId;
        this.value = value;
    }

    public long getSurveyItemId() {
        return surveyItemId;
    }

    public String getValue() {
        return value;
    }

}
