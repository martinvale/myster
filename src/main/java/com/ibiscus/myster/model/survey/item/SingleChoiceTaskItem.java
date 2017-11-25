package com.ibiscus.myster.model.survey.item;

import com.ibiscus.myster.model.survey.SurveyTaskItem;
import com.ibiscus.myster.web.ui.Visitor;

import java.util.Optional;

public class SingleChoiceTaskItem extends SurveyTaskItem {

    private final SingleChoice singleChoice;

    public SingleChoiceTaskItem(SingleChoice singleChoice, Optional<String> itemValue) {
        super(itemValue);
        this.singleChoice = singleChoice;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public SingleChoice getSingleChoice() {
        return singleChoice;
    }
}
