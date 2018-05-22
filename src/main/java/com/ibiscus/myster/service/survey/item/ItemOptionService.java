package com.ibiscus.myster.service.survey.item;

import java.util.List;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.SurveyItem;
import com.ibiscus.myster.model.survey.item.SingleChoice;
import com.ibiscus.myster.repository.survey.item.SurveyItemRepository;

public class ItemOptionService {

    private final SurveyItemRepository surveyItemRepository;

    public ItemOptionService(SurveyItemRepository surveyItemRepository) {
        this.surveyItemRepository = surveyItemRepository;
    }

    public SurveyItem get(long itemOptionId) {
        return surveyItemRepository.findOne(itemOptionId);
    }

    public AbstractSurveyItem save(AbstractSurveyItem surveyItem) {
        return surveyItemRepository.save(surveyItem);
    }

    public List<AbstractSurveyItem> findByCategoryId(long surveyId) {
        return surveyItemRepository.findByCategoryIdOrderByPositionAsc(surveyId);
    }

    public void delete(SurveyItem surveyItem) {
        surveyItemRepository.delete((SingleChoice) surveyItem);
    }

}
