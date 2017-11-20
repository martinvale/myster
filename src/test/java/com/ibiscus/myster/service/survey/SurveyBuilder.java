package com.ibiscus.myster.service.survey;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;

import com.ibiscus.myster.model.survey.Survey;

public class SurveyBuilder {

    private long id;

    private String name = new RandomStringGenerator.Builder()
            .withinRange('a', 'z').build().generate(5);

    private boolean enabled = RandomUtils.nextBoolean();

    public SurveyBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public SurveyBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public SurveyBuilder withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Survey build() {
        return new Survey(id, name, enabled);
    }
}
