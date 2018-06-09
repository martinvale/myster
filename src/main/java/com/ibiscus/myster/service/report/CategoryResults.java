package com.ibiscus.myster.service.report;

import java.util.List;

public class CategoryResults {

    private final String name;
    private final List<SurveyItemResults> surveyItemResults;

    public CategoryResults(String name, List<SurveyItemResults> surveyItemResults) {
        this.name = name;
        this.surveyItemResults = surveyItemResults;
    }

    public String getName() {
        return name;
    }

    public List<SurveyItemResults> getSurveyItemResults() {
        return surveyItemResults;
    }
}
