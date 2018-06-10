package com.ibiscus.myster.configuration;

import com.ibiscus.myster.repository.assignment.AssignmentRepository;
import com.ibiscus.myster.repository.category.CategoryRepository;
import com.ibiscus.myster.repository.security.UserRepository;
import com.ibiscus.myster.repository.shopper.ShopperRepository;
import com.ibiscus.myster.repository.survey.SurveyRepository;
import com.ibiscus.myster.repository.survey.data.ResponseRepository;
import com.ibiscus.myster.repository.survey.item.ChoiceRepository;
import com.ibiscus.myster.repository.survey.item.SurveyItemRepository;
import com.ibiscus.myster.service.assignment.AssignmentService;
import com.ibiscus.myster.service.communication.MailSender;
import com.ibiscus.myster.service.report.ReportService;
import com.ibiscus.myster.service.survey.SurveyService;
import com.ibiscus.myster.service.survey.data.DatastoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ServiceConfig {

    @Bean
    public SurveyService getSurveyService(SurveyRepository surveyRepository) {
        return new SurveyService(surveyRepository);
    }

    @Bean
    public AssignmentService getAssignmentService(AssignmentRepository assignmentRepository, CategoryRepository categoryRepository,
                                                  SurveyItemRepository surveyItemRepository, ResponseRepository responseRepository,
                                                  UserRepository userRepository, ShopperRepository shopperRepository,
                                                  MailSender mailSender, DatastoreService datastoreService) {
        return new AssignmentService(assignmentRepository, categoryRepository, surveyItemRepository, responseRepository,
                userRepository, shopperRepository, mailSender, datastoreService);
    }

    @Bean
    public ReportService getReportService(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                          CategoryRepository categoryRepository,
                                          SurveyItemRepository surveyItemRepository,
                                          ChoiceRepository choiceRepository) {
        return new ReportService(namedParameterJdbcTemplate, categoryRepository, surveyItemRepository,
                choiceRepository);
    }
}
