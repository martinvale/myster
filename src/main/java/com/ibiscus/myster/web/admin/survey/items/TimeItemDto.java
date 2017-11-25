package com.ibiscus.myster.web.admin.survey.items;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.SurveyItem;
import com.ibiscus.myster.model.survey.item.TimeItem;

public class TimeItemDto extends SurveyItemDto {

    public TimeItemDto(long surveyId) {
        super(surveyId);
    }

    public TimeItemDto(SurveyItem surveyItem) {
        super(surveyItem);
    }

    @Override
    public AbstractSurveyItem getSurveyItem(long id, long categoryId, int position, String title, String description) {
        return new TimeItem(id, categoryId, position, title, description);
    }
}
