package com.ibiscus.myster.service.report;

import java.util.List;

public class SurveyItemResults {

    private final String title;
    private final List<ChoiceResults> choiceResults;

    public SurveyItemResults(String title, List<ChoiceResults> choiceResults) {
        this.title = title;
        this.choiceResults = choiceResults;
    }

    public String getTitle() {
        return title;
    }

    public List<ChoiceResults> getChoiceResults() {
        return choiceResults;
    }
}
