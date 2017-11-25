package com.ibiscus.myster.web.admin.survey.category;

import com.ibiscus.myster.model.survey.category.Category;

import java.util.Optional;

public class CategoryDto {

    private Optional<Long> id;
    private long surveyId;
    private String name;
    private int position;

    public CategoryDto(long surveyId) {
        this.id = Optional.empty();
        this.surveyId = surveyId;
    }

    public CategoryDto(Category category) {
        this.id = Optional.of(category.getId());
        this.surveyId = category.getSurveyId();
        this.name = category.getName();
        this.position = category.getPosition();
    }

    protected Optional<Long> getId() {
        return id;
    }

    protected long getSurveyId() {
        return surveyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isNew() {
        return !id.isPresent();
    }

    public Category toCategory() {
        return new Category(id.orElse(0L), surveyId, name, position);
    }
}
