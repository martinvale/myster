package com.ibiscus.myster.model.survey.item;

import java.util.List;

public class SingleChoiceDto extends SurveyItemDto {

    private final List<ChoiceDto> choices;

    public SingleChoiceDto(long id, String type, String title, String description, String value, int index,
                           boolean filled, List<ChoiceDto> choices) {
        super(id, type, title, description, value, index, filled);
        this.choices = choices;
    }

    public List<ChoiceDto> getChoices() {
        return choices;
    }
}
