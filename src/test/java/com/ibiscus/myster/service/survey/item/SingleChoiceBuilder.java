package com.ibiscus.myster.service.survey.item;

import java.util.ArrayList;
import java.util.List;

import com.ibiscus.myster.model.survey.item.Choice;
import com.ibiscus.myster.model.survey.item.SingleChoice;

public class SingleChoiceBuilder {

    private long id;

    private long categoryId;

    private int position;

    private String title = "titulo";

    private String description = "description";

    private List<Choice> choices = new ArrayList<Choice>();

    public SingleChoiceBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public SingleChoiceBuilder withCategoryId(long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public SingleChoiceBuilder withChoices(List<Choice> choices) {
        this.choices = choices;
        return this;
    }

    public SingleChoice build() {
        return new SingleChoice(id, categoryId, position, title, description, choices);
    }
}
