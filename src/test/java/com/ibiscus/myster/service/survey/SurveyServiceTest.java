package com.ibiscus.myster.service.survey;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.ibiscus.myster.configuration.ServiceConfig;
import com.ibiscus.myster.repository.survey.SurveyRepository;
import com.ibiscus.myster.web.admin.survey.SurveyDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.ibiscus.myster.model.survey.Survey;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class SurveyServiceTest {

    @Autowired
    private SurveyService surveyService;

    @MockBean
    private SurveyRepository surveyRepository;

    @Test
    public void get() {
        Survey survey = new SurveyBuilder().build();
        when(surveyRepository.findOne(survey.getId())).thenReturn(survey);

        SurveyDto surveyDto = surveyService.get(survey.getId());

        assertEquals(survey.getName(), surveyDto.getName());
        assertEquals(survey.isEnabled(), surveyDto.isEnabled());
    }

    @Test
    public void findAll() {
        List<Survey> surveys = getSurveys();
        when(surveyRepository.findAll()).thenReturn(surveys);

        List<SurveyDto> surveyDtos = surveyService.findAll();

        assertEquals(2, surveyDtos.size());
        Predicate<SurveyDto> surveyDtoFilter = new SurveysChecker(surveys);
        assertEquals(2, surveyDtos.stream()
                        .filter(surveyDtoFilter)
                        .count());
    }

    private List<Survey> getSurveys() {
        Survey survey1 = new SurveyBuilder().build();
        Survey survey2 = new SurveyBuilder().build();
        return ImmutableList.of(survey1, survey2);
    }

    @Test
    public void save() {
        Survey survey = new SurveyBuilder().build();

        surveyService.save(survey);

        verify(surveyRepository).save(survey);
    }

    @Test
    public void delete() {
        Survey survey = new SurveyBuilder().build();

        surveyService.delete(survey);

        verify(surveyRepository).delete(survey);
    }

    private class SurveysChecker implements Predicate<SurveyDto> {

        private final List<Survey> surveys;

        private SurveysChecker(List<Survey> surveys) {
            this.surveys = surveys;
        }

        @Override
        public boolean test(SurveyDto surveyDto) {
            return surveys.stream()
                    .filter(survey -> survey.getName().equals(surveyDto.getName()))
                    .findAny()
                    .isPresent();
        }
    }
}
