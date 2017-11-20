package com.ibiscus.myster.repository.survey.item;

import java.util.List;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import org.springframework.data.repository.CrudRepository;

public interface ItemOptionRepository extends CrudRepository<AbstractSurveyItem, Long> {

    List<AbstractSurveyItem> findByCategoryIdOrderByPositionAsc(long surveyId);

}
