package com.ibiscus.myster.model.survey.category;

import com.ibiscus.myster.model.survey.item.SurveyItemDto;

import java.util.List;

public class CategoryDto {

    private final String name;
    private final List<SurveyItemDto> items;

    public CategoryDto(String name, List<SurveyItemDto> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<SurveyItemDto> getItems() {
        return items;
    }

    public boolean isComplete() {
        return items.stream().allMatch(surveyItemDto -> surveyItemDto.isFilled());
    }
}
