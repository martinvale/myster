package com.ibiscus.myster.model.survey.item;

public class ChoiceDto {

    private String description;
    private String value;

    public ChoiceDto(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

}
