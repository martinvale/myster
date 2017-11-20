package com.ibiscus.myster.model.survey.item;

public interface SurveyItem {

    long getId();

    long getCategoryId();

    int getPosition();

    String getTitle();

    String getDescription();
}
