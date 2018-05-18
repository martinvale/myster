package com.ibiscus.myster.configuration;

import com.ibiscus.myster.repository.survey.SurveyRepository;
import com.ibiscus.myster.repository.survey.data.ResponseRepository;
import com.ibiscus.myster.repository.survey.item.ItemOptionRepository;
import com.ibiscus.myster.service.survey.SurveyService;
import com.ibiscus.myster.service.survey.data.DatastoreService;
import com.ibiscus.myster.service.survey.data.GoogleDatastoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public SurveyService getSurveyService(SurveyRepository surveyRepository) {
        return new SurveyService(surveyRepository);
    }

}
