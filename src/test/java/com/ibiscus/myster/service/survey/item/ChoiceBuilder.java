package com.ibiscus.myster.service.survey.item;

import org.apache.commons.text.RandomStringGenerator;

import com.ibiscus.myster.model.survey.item.Choice;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public class ChoiceBuilder {

    private long id;

    private long itemOptionId;

    private String description = new RandomStringGenerator.Builder()
        .withinRange('a', 'z').build().generate(10);

    private Integer value = nextInt(0, 101);

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

    public ChoiceBuilder withValue(Integer value) {
        this.value = value;
        return this;
    }

    public Choice build() {
        return new Choice(id, itemOptionId, description, value);
    }
}
