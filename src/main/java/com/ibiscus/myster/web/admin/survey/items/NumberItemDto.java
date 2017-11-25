package com.ibiscus.myster.web.admin.survey.items;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.NumberItem;
import com.ibiscus.myster.model.survey.item.SurveyItem;

public class NumberItemDto extends SurveyItemDto {

    public NumberItemDto(long surveyId) {
        super(surveyId);
    }

    public NumberItemDto(SurveyItem surveyItem) {
        super(surveyItem);
    }

    @Override
    public AbstractSurveyItem getSurveyItem(long id, long categoryId, int position, String title, String description) {
        return new NumberItem(id, categoryId, position, title, description);
    }
}
