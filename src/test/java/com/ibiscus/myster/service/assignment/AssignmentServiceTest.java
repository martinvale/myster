package com.ibiscus.myster.service.assignment;

import com.ibiscus.myster.configuration.ServiceConfig;
import com.ibiscus.myster.configuration.TestConfig;
import com.ibiscus.myster.model.survey.Assignment;
import com.ibiscus.myster.model.survey.data.Response;
import com.ibiscus.myster.repository.assignment.AssignmentRepository;
import com.ibiscus.myster.repository.survey.data.ResponseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.ibiscus.myster.service.assignment.CompletedSurveyBuilder.newCompletedSurveyBuilder;
import static com.ibiscus.myster.service.assignment.CompletedSurveyItemBuilder.newCompletedSurveyItemBuilder;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, TestConfig.class})
public class AssignmentServiceTest {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Captor
    private ArgumentCaptor<Assignment> assignmentArgument;

    @Test
    public void save() throws Exception {
        long assignmentId = getAssignmentId();
        CompletedSurvey survey = getCompletedSurvey();

        assignmentService.save(assignmentId, survey);

        assertThatAssignmentIsUpdate(assignmentId);
        int savedItemsCount = Long.valueOf(survey.getCompletedSurveyItems().stream()
                .filter(CompletedSurveyItem::hasContent)
                .count()).intValue();
        verify(responseRepository, times(savedItemsCount)).save(any(Response.class));
    }

    private void assertThatAssignmentIsUpdate(long assignmentId) {
        verify(assignmentRepository).save(assignmentArgument.capture());
        Assignment savedAssignment = assignmentArgument.getValue();
        assertEquals(assignmentId, savedAssignment.getId());
    }

    private long getAssignmentId() {
        long assignmentId = 10;
        Assignment assignment = new Assignment.Builder()
                .withId(assignmentId)
                .build();
        when(assignmentRepository.findOne(assignmentId)).thenReturn(assignment);
        return assignmentId;
    }

    private CompletedSurvey getCompletedSurvey() {
        List<CompletedSurveyItem> items = getCompletedSurveyItems();
        CompletedSurveyBuilder completedSurveyBuilder = newCompletedSurveyBuilder();
        items.stream().forEach(completedSurveyBuilder::addItem);
        return completedSurveyBuilder.build();
    }

    private List<CompletedSurveyItem> getCompletedSurveyItems() {
        List<CompletedSurveyItem> items = newArrayList();
        items.add(newCompletedSurveyItemBuilder().build());
        items.add(newCompletedSurveyItemBuilder()
                .withFileResponse(true)
                .withValue("")
                .withFiles(newArrayList())
                .build());
        return items;
    }

}