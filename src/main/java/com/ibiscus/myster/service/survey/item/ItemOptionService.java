package com.ibiscus.myster.service.survey.item;

import java.util.List;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.SurveyItem;
import com.ibiscus.myster.model.survey.item.SingleChoice;
import com.ibiscus.myster.repository.survey.item.ItemOptionRepository;

public class ItemOptionService {

    private final ItemOptionRepository itemOptionRepository;

    public ItemOptionService(ItemOptionRepository itemOptionRepository) {
        this.itemOptionRepository = itemOptionRepository;
    }

    public SurveyItem get(long itemOptionId) {
        return itemOptionRepository.findOne(itemOptionId);
    }

    public AbstractSurveyItem save(AbstractSurveyItem surveyItem) {
        return itemOptionRepository.save(surveyItem);
    }

    public List<AbstractSurveyItem> findByCategoryId(long surveyId) {
        return itemOptionRepository.findByCategoryIdOrderByPositionAsc(surveyId);
    }

    public void delete(SurveyItem surveyItem) {
        itemOptionRepository.delete((SingleChoice) surveyItem);
    }

}
