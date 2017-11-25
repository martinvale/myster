package com.ibiscus.myster.web.admin.survey.items;

import java.util.Optional;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.SurveyItem;

public abstract class SurveyItemDto {

    private Optional<Long> id;
    private long categoryId;
    private String title;
    private String description;
    private int position;

    public SurveyItemDto(long categoryId) {
        this.id = Optional.empty();
        this.categoryId = categoryId;
    }

    public SurveyItemDto(SurveyItem surveyItem) {
        this.id = Optional.of(surveyItem.getId());
        this.categoryId = surveyItem.getCategoryId();
        this.position = surveyItem.getPosition();
        this.title = surveyItem.getTitle();
        this.description = surveyItem.getDescription();
    }

    protected Optional<Long> getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public AbstractSurveyItem toSurveyItem() {
        return getSurveyItem(id.orElse(0L), categoryId, position, title, description);
    }

    protected abstract AbstractSurveyItem getSurveyItem(long id, long categoryId, int position, String title,
                                                        String description);

}
