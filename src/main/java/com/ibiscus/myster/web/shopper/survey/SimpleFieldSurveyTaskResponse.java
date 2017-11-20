package com.ibiscus.myster.web.shopper.survey;

import com.vaadin.data.HasValue;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextArea;

import java.util.Optional;

public class SimpleFieldSurveyTaskResponse extends SurveyTaskResponse {

    private final HasValue component;

    public SimpleFieldSurveyTaskResponse(long surveyItemId, HasValue component) {
        super(surveyItemId);
        this.component = component;
    }

    @Override
    public Optional<String> getValue() {
        return component.getOptionalValue();
    }
}
