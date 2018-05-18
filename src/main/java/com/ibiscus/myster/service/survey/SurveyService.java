package com.ibiscus.myster.service.survey;

import com.ibiscus.myster.model.survey.*;
import com.ibiscus.myster.web.admin.survey.SurveyDto;
import org.springframework.stereotype.Service;

import com.ibiscus.myster.repository.survey.SurveyRepository;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository pollRepository) {
        this.surveyRepository = pollRepository;
    }

    public SurveyDto get(long id) {
        Survey survey = surveyRepository.findOne(id);
        return new SurveyDto(survey);
    }

    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }

    public void delete(Survey survey) {
        surveyRepository.delete(survey);
    }

    public List<SurveyDto> findAll() {
        List<Survey> surveys = newArrayList(surveyRepository.findAll());
        return surveys.stream().map(survey -> new SurveyDto(survey)).collect(Collectors.toList());
    }

}
