package com.ibiscus.myster.configuration;

import com.ibiscus.myster.repository.assignment.AssignmentRepository;
import com.ibiscus.myster.repository.category.CategoryRepository;
import com.ibiscus.myster.repository.data.CountryRepository;
import com.ibiscus.myster.repository.data.StateRepository;
import com.ibiscus.myster.repository.security.UserRepository;
import com.ibiscus.myster.repository.shopper.ShopperRepository;
import com.ibiscus.myster.repository.survey.SurveyRepository;
import com.ibiscus.myster.repository.survey.data.ResponseRepository;
import com.ibiscus.myster.repository.survey.item.ChoiceRepository;
import com.ibiscus.myster.repository.survey.item.SurveyItemRepository;
import com.ibiscus.myster.service.communication.MailSender;
import com.ibiscus.myster.service.survey.data.DatastoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public SurveyRepository getSurveyRepository() {
        return mock(SurveyRepository.class);
    }

    @Bean
    public SurveyItemRepository getSurveyItemRepository() {
        return mock(SurveyItemRepository.class);
    }

    @Bean
    public AssignmentRepository getAssignmentRepository() {
        return mock(AssignmentRepository.class);
    }

    @Bean
    public CategoryRepository getCategoryRepository() {
        return mock(CategoryRepository.class);
    }

    @Bean
    public ResponseRepository getResponseRepository() {
        return mock(ResponseRepository.class);
    }

    @Bean
    public UserRepository getUserRepository() {
        return mock(UserRepository.class);
    }

    @Bean
    public ChoiceRepository getChoiceRepository() {
        return mock(ChoiceRepository.class);
    }

    @Bean
    public ShopperRepository getShopperRepository() {
        return mock(ShopperRepository.class);
    }

    @Bean
    public CountryRepository getCountryRepository() {
        return mock(CountryRepository.class);
    }

    @Bean
    public StateRepository getStateRepository() {
        return mock(StateRepository.class);
    }

    @Bean
    public MailSender getMailSender() {
        return mock(MailSender.class);
    }

    @Bean
    public DatastoreService getDatastoreService() {
        return mock(DatastoreService.class);
    }

    @Bean
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return mock(NamedParameterJdbcTemplate.class);
    }
}
