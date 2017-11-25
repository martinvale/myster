package com.ibiscus.myster.model.survey;

import com.ibiscus.myster.web.ui.Visitable;

import java.util.Optional;

public abstract class SurveyTaskItem implements Visitable {

    private final Optional<String> itemValue;

    public SurveyTaskItem(Optional<String> itemValue) {
        this.itemValue = itemValue;
    }

    public Optional<String> getValue() {
        return itemValue;
    }

}
