package com.ibiscus.myster.model.survey.item;

import com.ibiscus.myster.model.survey.SurveyTaskItem;
import com.ibiscus.myster.web.ui.Visitor;

import java.util.Optional;

public class TimeItemTaskItem extends SurveyTaskItem {

    private final TimeItem timeItem;

    public TimeItemTaskItem(TimeItem timeItem, Optional<String> itemValue) {
        super(itemValue);
        this.timeItem = timeItem;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public TimeItem getTimeItem() {
        return timeItem;
    }
}
