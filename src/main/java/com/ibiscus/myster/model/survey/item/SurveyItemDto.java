package com.ibiscus.myster.model.survey.item;

public class SurveyItemDto {

    private final long id;
    private final String type;
    private final String title;
    private final String description;
    private final String value;
    private final int index;
    private final boolean filled;

    public SurveyItemDto(long id, String type, String title, String description, String value, int index,
                         boolean filled) {
        this.id = id;
        this.type = type.toLowerCase();
        this.title = title;
        this.description = description;
        this.value = value;
        this.index = index;
        this.filled = filled;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public boolean isFilled() {
        return filled;
    }
}
