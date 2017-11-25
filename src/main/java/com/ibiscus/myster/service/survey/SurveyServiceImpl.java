package com.ibiscus.myster.service.survey;

import com.ibiscus.myster.model.survey.*;
import com.ibiscus.myster.repository.survey.item.ItemOptionRepository;
import com.ibiscus.myster.repository.survey.data.ResponseRepository;
import org.springframework.stereotype.Service;

import com.ibiscus.myster.repository.survey.SurveyRepository;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    private final ItemOptionRepository itemOptionRepository;

    private final ResponseRepository responseRepository;

    public SurveyServiceImpl(SurveyRepository pollRepository, ItemOptionRepository itemOptionRepository,
                             ResponseRepository responseRepository) {
        this.surveyRepository = pollRepository;
        this.itemOptionRepository = itemOptionRepository;
        this.responseRepository = responseRepository;
    }

    @Override
    public Survey get(long id) {
        return surveyRepository.findOne(id);
    }

    @Override
    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Override
    public void delete(Survey survey) {
        surveyRepository.delete(survey);
    }

    @Override
    public Iterable<Survey> findAll() {
        return surveyRepository.findAll();
    }

}
