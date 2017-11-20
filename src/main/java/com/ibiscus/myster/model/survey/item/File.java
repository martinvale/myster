package com.ibiscus.myster.model.survey.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "File")
@DiscriminatorValue("FILE")
public class File extends AbstractSurveyItem {

    File() {
        super();
    }

    public File(long id, long categoryId, int position, String title, String description) {
        super(id, categoryId, position, title, description, ItemType.FILE);
    }
}
