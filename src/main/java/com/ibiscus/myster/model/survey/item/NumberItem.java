package com.ibiscus.myster.model.survey.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "NumberItem")
@DiscriminatorValue("NUMBER")
public class NumberItem extends AbstractSurveyItem {

    NumberItem() {
        super();
    }

    public NumberItem(long id, long categoryId, int position, String title, String description) {
        super(id, categoryId, position, title, description, ItemType.NUMBER);
    }

}
