package com.ibiscus.myster.web.shopper.survey;

import com.ibiscus.myster.model.survey.item.Choice;
import com.vaadin.ui.RadioButtonGroup;

import java.util.Optional;

public class SingleChoiceSurveyTaskResponse extends SurveyTaskResponse {

    private final RadioButtonGroup<Choice> component;

    public SingleChoiceSurveyTaskResponse(long surveyItemId, RadioButtonGroup<Choice> component) {
        super(surveyItemId);
        this.component = component;
    }

    @Override
    public Optional<String> getValue() {
        return component.getSelectedItem().map(choice -> String.valueOf(choice.getValue()));
    }
}
