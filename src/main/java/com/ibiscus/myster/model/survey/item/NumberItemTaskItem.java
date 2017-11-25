package com.ibiscus.myster.model.survey.item;

import com.ibiscus.myster.model.survey.SurveyTaskItem;
import com.ibiscus.myster.web.ui.Visitor;

import java.util.Optional;

public class NumberItemTaskItem extends SurveyTaskItem {

    private final NumberItem numberItem;

    public NumberItemTaskItem(NumberItem numberItem, Optional<String> itemValue) {
        super(itemValue);
        this.numberItem = numberItem;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public NumberItem getNumberItem() {
        return numberItem;
    }
}
