package com.ibiscus.myster.service.survey;

import com.ibiscus.myster.model.survey.item.SurveyItem;

public class ResponseTask {

    private SurveyItem surveyItem;
    private String value;

    public ResponseTask(SurveyItem surveyItem, String value) {
        this.surveyItem = surveyItem;
        this.value = value;
    }

    public SurveyItem getSurveyItem() {
        return surveyItem;
    }

    public String getValue() {
        return value;
    }
}
