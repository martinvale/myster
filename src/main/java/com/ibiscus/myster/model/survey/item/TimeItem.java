package com.ibiscus.myster.model.survey.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "TimeItem")
@DiscriminatorValue("TIME")
public class TimeItem extends AbstractSurveyItem {

    TimeItem() {
        super();
    }

    public TimeItem(long id, long categoryId, int position, String title, String description) {
        super(id, categoryId, position, title, description, ItemType.TIME);
    }

}
