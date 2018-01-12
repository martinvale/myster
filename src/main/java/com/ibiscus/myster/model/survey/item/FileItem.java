package com.ibiscus.myster.model.survey.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "FileItem")
@DiscriminatorValue("FILE")
public class FileItem extends AbstractSurveyItem {

    FileItem() {
        super();
    }

    public FileItem(long id, long categoryId, int position, String title, String description) {
        super(id, categoryId, position, title, description, ItemType.FILE);
    }
}
