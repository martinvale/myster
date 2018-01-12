package com.ibiscus.myster.web.admin.survey.items;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.FileItem;
import com.ibiscus.myster.model.survey.item.SurveyItem;

public class FileItemDto extends SurveyItemDto {

    public FileItemDto(long surveyId) {
        super(surveyId);
    }

    public FileItemDto(SurveyItem surveyItem) {
        super(surveyItem);
    }

    @Override
    public AbstractSurveyItem getSurveyItem(long id, long categoryId, int position, String title, String description) {
        return new FileItem(id, categoryId, position, title, description);
    }
}
