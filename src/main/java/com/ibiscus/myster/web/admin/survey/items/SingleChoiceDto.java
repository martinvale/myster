package com.ibiscus.myster.web.admin.survey.items;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.Choice;
import com.ibiscus.myster.model.survey.item.SingleChoice;
import com.ibiscus.myster.model.survey.item.SurveyItem;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SingleChoiceDto extends SurveyItemDto {

    private List<Choice> choices;

    public SingleChoiceDto(long surveyId) {
        super(surveyId);
        this.choices = newArrayList();
    }

    public SingleChoiceDto(SurveyItem surveyItem) {
        super(surveyItem);
        this.choices = ((SingleChoice) surveyItem).getChoices();
    }

    @Override
    public AbstractSurveyItem getSurveyItem(long id, long categoryId, int position, String title, String description) {
        return new SingleChoice(id, categoryId, position, title, description, choices);
    }

}
