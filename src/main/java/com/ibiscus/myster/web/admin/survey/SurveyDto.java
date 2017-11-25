package com.ibiscus.myster.web.admin.survey;

import java.util.Optional;

import com.ibiscus.myster.model.survey.Survey;

public class SurveyDto {

    private Optional<Long> id;
    private String name;
    private boolean enabled;

    public SurveyDto() {
        this.id = Optional.empty();
    }

    public SurveyDto(Survey survey) {
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
