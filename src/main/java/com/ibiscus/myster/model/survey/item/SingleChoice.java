package com.ibiscus.myster.model.survey.item;

import com.ibiscus.myster.web.ui.Visitor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity(name = "SingleChoice")
@DiscriminatorValue("SINGLE_CHOICE")
public class SingleChoice extends AbstractSurveyItem {

    @OneToMany(mappedBy = "itemOptionId", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Choice> choices;

    SingleChoice() {
        super();
    }

    public SingleChoice(long id, long categoryId, int position, String title, String description, List<Choice> choices) {
        super(id, categoryId, position, title, description, ItemType.SINGLE_CHOICE);
        this.choices = new ArrayList<Choice>(choices);
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public Choice getChoiceByValue(String value) {
        return choices.stream().filter(c -> c.getValue().equals(value)).findFirst().get();
    }

}
