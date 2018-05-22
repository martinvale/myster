package com.ibiscus.myster.model.survey.item;

import javax.persistence.*;

@Entity(name = "survey_item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 20)
public abstract class AbstractSurveyItem implements SurveyItem {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "category_id")
    private long categoryId;

    @Column
    private int position;

    @Column(length = 2000, nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    AbstractSurveyItem() {
    }

    protected AbstractSurveyItem(long id, long categoryId, int position, String title, String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.position = position;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSurveyItem that = (AbstractSurveyItem) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
