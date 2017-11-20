package com.ibiscus.myster.web.admin.survey.items;

import com.ibiscus.myster.model.survey.item.*;

public class SurveyItemDtoFactory {

    public SurveyItemDto getSurveyItemDto(SurveyItem surveyItem) {
        if (surveyItem instanceof SingleChoice) {
            return new SingleChoiceDto(surveyItem);
        } else if (surveyItem instanceof TextItem) {
            return new TextItemDto(surveyItem);
        } else if (surveyItem instanceof NumberItem) {
            return new NumberItemDto(surveyItem);
        } else if (surveyItem instanceof TimeItem) {
            return new TimeItemDto(surveyItem);
        } else if (surveyItem instanceof File) {
            return new FileItemDto(surveyItem);
        }
        throw new IllegalArgumentException("Cannot create DTO object for class: " + surveyItem.getClass().getName());
    }

}
