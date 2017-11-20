package com.ibiscus.myster.service.survey.item;

import org.apache.commons.text.RandomStringGenerator;

import com.ibiscus.myster.model.survey.item.Choice;

public class ChoiceBuilder {

    private long id;

    private long itemOptionId;

    private String description = new RandomStringGenerator.Builder()
        .withinRange('a', 'z').build().generate(10);

    private String value = new RandomStringGenerator.Builder()
        .withinRange('a', 'z').build().generate(5);

    public ChoiceBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ChoiceBuilder withItemOptionId(long itemOptionId) {
        this.itemOptionId = itemOptionId;
        return this;
    }

    public ChoiceBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ChoiceBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public Choice build() {
        return new Choice(id, itemOptionId, description, value);
    }
}
