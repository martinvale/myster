package com.ibiscus.myster.model.survey.item;

import com.ibiscus.myster.model.survey.SurveyTaskItem;
import com.ibiscus.myster.web.ui.Visitor;

import java.util.Optional;

public class TextItemTaskItem extends SurveyTaskItem {

    private final TextItem textItem;

    public TextItemTaskItem(TextItem textItem, Optional<String> itemValue) {
        super(itemValue);
        this.textItem = textItem;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public TextItem getTextItem() {
        return textItem;
    }
}
