package com.ibiscus.myster.web.admin.survey.items.choice;

import java.util.Optional;

import com.ibiscus.myster.model.survey.item.Choice;

public class ChoiceDto {

    private Optional<Long> id;
    private long itemId;
    private String description;
    private String value;

    public ChoiceDto(long itemId) {
        this.id = Optional.empty();
        this.itemId = itemId;
    }

    public ChoiceDto(Choice choice) {
        this.id = Optional.of(choice.getId());
        this.itemId = choice.getSurveyItemId();
        this.description = choice.getDescription();
        this.setValue(choice.getValue());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isNew() {
        return !id.isPresent();
    }

    public Choice toChoice() {
        return new Choice(id.orElse(0L), itemId, description, value);
    }

}
