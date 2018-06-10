package com.ibiscus.myster.model.survey.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "TextItem")
@DiscriminatorValue("TEXT")
public class TextItem extends AbstractSurveyItem {

    TextItem() {
        super();
    }

    public TextItem(long id, long categoryId, int position, String title, String description) {
        super(id, categoryId, position, title, description);
    }

}
