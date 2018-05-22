package com.ibiscus.myster.service.survey.item.choice;

import java.util.List;

import com.ibiscus.myster.model.survey.item.Choice;
import com.ibiscus.myster.repository.survey.item.ChoiceRepository;

public class ChoiceService {

    private final ChoiceRepository choiceRepository;

    public ChoiceService(ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    public Choice get(long choiceId) {
        return choiceRepository.findOne(choiceId);
    }

    public Choice save(Choice choice) {
        return choiceRepository.save(choice);
    }

    public List<Choice> findByItemOption(long itemOptionId) {
        return choiceRepository.findBySurveyItemId(itemOptionId);
    }

}
