package com.ibiscus.myster.web.shopper.survey;

import com.ibiscus.myster.model.survey.Survey;

import java.util.Optional;

public class FilledSurvey {

    private Optional<Long> id;
    private String name;
    private boolean enabled;

    public FilledSurvey() {
        this.id = Optional.empty();
    }

    public FilledSurvey(Survey survey) {
        this.id = Optional.of(survey.getId());
        this.name = survey.getName();
        this.enabled = survey.isEnabled();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isNew() {
        return !id.isPresent();
    }

    public Survey toSurvey() {
        return new Survey(id.orElse(0L), name, enabled);
    }
}
