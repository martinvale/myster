package com.ibiscus.myster.service.survey;

import com.ibiscus.myster.model.survey.Survey;
import com.ibiscus.myster.model.survey.SurveyTask;

public interface SurveyService {

    Iterable<Survey> findAll();
    Survey get(long id);
    Survey save(Survey survey);
    void delete(Survey survey);

}
