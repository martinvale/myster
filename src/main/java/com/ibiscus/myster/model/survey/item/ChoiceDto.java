package com.ibiscus.myster.model.survey.item;

public class ChoiceDto {

    private String description;
    private Long value;

    public ChoiceDto(String description, Long value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public Long getValue() {
        return value;
    }

}
