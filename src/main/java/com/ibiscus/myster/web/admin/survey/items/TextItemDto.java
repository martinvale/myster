package com.ibiscus.myster.web.admin.survey.items;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.SurveyItem;
import com.ibiscus.myster.model.survey.item.TextItem;

public class TextItemDto extends SurveyItemDto {

    public TextItemDto(long surveyId) {
        super(surveyId);
    }

    public TextItemDto(SurveyItem surveyItem) {
        super(surveyItem);
    }

    @Override
    public AbstractSurveyItem getSurveyItem(long id, long categoryId, int position, String title, String description) {
        return new TextItem(id, categoryId, position, title, description);
    }
}
