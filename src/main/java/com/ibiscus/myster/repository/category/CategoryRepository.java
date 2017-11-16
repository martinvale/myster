package com.ibiscus.myster.repository.category;

import com.ibiscus.myster.model.survey.category.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findBySurveyId(long surveyId);

}
