package com.ibiscus.myster.model.survey;

import java.util.List;

public class TaskCategory {

    private final String name;
    private final List<SurveyTaskItem> items;

    public TaskCategory(String name, List<SurveyTaskItem> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<SurveyTaskItem> getItems() {
        return items;
    }

    public boolean isComplete() {
        return items.stream().allMatch(surveyTaskItem -> surveyTaskItem.getValue().isPresent());
    }
}
