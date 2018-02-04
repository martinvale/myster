package com.ibiscus.myster.service.assignment;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.ibiscus.myster.model.company.Location;
import com.ibiscus.myster.model.security.User;
import com.ibiscus.myster.model.survey.*;
import com.ibiscus.myster.model.survey.category.Category;
import com.ibiscus.myster.model.survey.category.CategoryDto;
import com.ibiscus.myster.model.survey.data.Response;
import com.ibiscus.myster.model.survey.item.*;
import com.ibiscus.myster.repository.category.CategoryRepository;
import com.ibiscus.myster.repository.security.UserRepository;
import com.ibiscus.myster.repository.survey.data.ResponseRepository;
import com.ibiscus.myster.repository.survey.item.ItemOptionRepository;
import com.ibiscus.myster.service.communication.MailSender;
import com.ibiscus.myster.service.survey.data.DatastoreService;
import com.ibiscus.myster.web.admin.survey.SurveyAssignment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibiscus.myster.repository.assignment.AssignmentRepository;
import org.springframework.web.multipart.MultipartFile;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemOptionRepository itemOptionRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private DatastoreService datastoreService;

    private final String siteUrl = "http://localhost:8080/";

    public List<AssignmentDescriptor> findDescriptorsByShopperId(long shopperId) {
        List<AssignmentDescriptor> assignmentDescriptors = new ArrayList<AssignmentDescriptor>();
        List<Assignment> assignments = assignmentRepository.findByShopperId(shopperId);
        for (Assignment assignment : assignments) {
            if (!assignment.isSent()) {
                Location location = assignment.getLocation();
                assignmentDescriptors.add(new AssignmentDescriptor(assignment.getId(), "Carrefour",
                        location.getAddress(), Assignment.STATE.FINISHED.equals(assignment.getState())));
            }
        }
        return assignmentDescriptors;
    }

    public SurveyTask2 getTask(long id) {
        Assignment assignment = get(id);
        Survey survey = assignment.getSurvey();
        List<TaskCategory> taskCategories = newArrayList();
        List<Category> categories = categoryRepository.findBySurveyId(survey.getId());
        for (Category category : categories) {
            List<AbstractSurveyItem> items = itemOptionRepository.findByCategoryIdOrderByPositionAsc(category.getId());
            List<SurveyTaskItem> taskItems = newArrayList();
            for (SurveyItem item : items) {
                Optional<Response> response = Optional.ofNullable(
                        responseRepository.findByAssignment(assignment.getId(), item.getId()));
                String value = null;
                if (response.isPresent()) {
                    value = response.get().getValue();
                }
                if (item instanceof SingleChoice) {
                    taskItems.add(new SingleChoiceTaskItem((SingleChoice) item, Optional.ofNullable(value)));
                } else if (item instanceof TextItem) {
                    taskItems.add(new TextItemTaskItem((TextItem) item, Optional.ofNullable(value)));
                } else if (item instanceof NumberItem) {
                    taskItems.add(new NumberItemTaskItem((NumberItem) item, Optional.ofNullable(value)));
                } else if (item instanceof TimeItem) {
                    taskItems.add(new TimeItemTaskItem((TimeItem) item, Optional.ofNullable(value)));
                } else if (item instanceof FileItem) {
                    taskItems.add(new FileTaskItem((FileItem) item, Optional.ofNullable(value)));
                }
            }
            taskCategories.add(new TaskCategory(category.getName(), taskItems));
        }
        TaskDescription taskDescription = new TaskDescription(survey.getName(), survey.getName(),
                assignment.getLocation().getAddress(), assignment.getPayRate());
        LocalDate visitDate = LocalDate.now();
        if (assignment.getVisitDate() != null) {
            visitDate = assignment.getVisitDate().toLocalDate();
        }
        LocalTime inTime = LocalTime.now();
        if (assignment.getInTime() != null) {
            inTime = assignment.getInTime().toLocalTime();
        }
        LocalTime outTime = LocalTime.now();
        if (assignment.getOutTime() != null) {
            outTime = assignment.getOutTime().toLocalTime();
        }
        return new SurveyTask2(taskDescription, taskCategories, visitDate, inTime, outTime);
    }

    public SurveyTask getSurveyTask(long assignmentId) {
        Assignment assignment = get(assignmentId);
        Survey survey = assignment.getSurvey();
        List<CategoryDto> taskCategories = newArrayList();
        List<Category> categories = categoryRepository.findBySurveyId(survey.getId());
        int index = 0;
        for (Category category : categories) {
            List<AbstractSurveyItem> items = itemOptionRepository.findByCategoryIdOrderByPositionAsc(category.getId());
            List<SurveyItemDto> taskItems = newArrayList();
            for (SurveyItem item : items) {
                Optional<Response> response = Optional.ofNullable(
                        responseRepository.findByAssignment(assignment.getId(), item.getId()));
                String value = null;
                if (response.isPresent()) {
                    value = response.get().getValue();
                }
                if (item instanceof SingleChoice) {
                    List<ChoiceDto> choicesDtos = ((SingleChoice) item).getChoices()
                            .stream()
                            .map(choice -> new ChoiceDto(choice.getDescription(), choice.getValue()))
                            .collect(Collectors.toList());
                    taskItems.add(new SingleChoiceDto(item.getId(), item.getClass().getSimpleName(), item.getTitle(),
                            item.getDescription(), value, index++, response.isPresent(), choicesDtos));
                } else if (item instanceof FileItem) {
                    taskItems.add(new FilesDto(item.getId(), item.getClass().getSimpleName(), item.getTitle(),
                            item.getDescription(), value, index++, response.isPresent()));
                } else {
                    taskItems.add(new SurveyItemDto(item.getId(), item.getClass().getSimpleName(), item.getTitle(),
                            item.getDescription(), value, index++, response.isPresent()));
                }
            }
            taskCategories.add(new CategoryDto(category.getName(), taskItems));
        }
        TaskDescription taskDescription = new TaskDescription(survey.getName(), survey.getName(),
                assignment.getLocation().getAddress(), assignment.getPayRate());
        java.util.Date visitDate = new java.util.Date();
        if (assignment.getVisitDate() != null) {
            visitDate = new java.util.Date(assignment.getVisitDate().getTime());
        }
        LocalTime inTime = LocalTime.now();
        if (assignment.getInTime() != null) {
            inTime = assignment.getInTime().toLocalTime();
        }
        LocalTime outTime = LocalTime.now();
        if (assignment.getOutTime() != null) {
            outTime = assignment.getOutTime().toLocalTime();
        }
        return new SurveyTask(assignmentId, taskDescription, taskCategories, visitDate, inTime, outTime);
    }

    public Assignment get(long id) {
        return assignmentRepository.findOne(id);
    }

    public void assign(SurveyAssignment surveyAssignment) {
        Assignment assignment = new Assignment(surveyAssignment.getSurvey(), surveyAssignment.getShopper().getId(),
                surveyAssignment.getLocation());
        assignmentRepository.save(assignment);
        User user = userRepository.findOne(surveyAssignment.getShopper().getUserId());
        String bodyMessage = getAssignmentBodyMessage(assignment, surveyAssignment.getSurvey(),
                surveyAssignment.getLocation());
        mailSender.sendMail(user.getUsername(), "Asignacion de encuesta", bodyMessage);
    }

    public void save(long assignmentId, CompletedSurvey completedSurvey) {
        Assignment assignment = assignmentRepository.findOne(assignmentId);
        Assignment filledAssignment = new Assignment(assignment.getId(), assignment.getSurvey(), assignment.getShopperId(),
                assignment.getLocation(), new Date(completedSurvey.getVisitDate().getTime()),
                Time.valueOf(LocalTime.of(completedSurvey.getInHour(), completedSurvey.getInMinute())),
                Time.valueOf(LocalTime.of(completedSurvey.getOutHour(), completedSurvey.getOutMinute())));
        assignmentRepository.save(filledAssignment);
        completedSurvey.getCompletedSurveyItems().stream()
                .filter(completedSurveyItem -> !StringUtils.isBlank(completedSurveyItem.getValue()) || completedSurveyItem.getFiles() != null)
                .forEach(completedSurveyItem -> save(assignmentId, completedSurveyItem));
    }

    private void save(long assignmentId, CompletedSurveyItem completedSurveyItem) {

        Optional<Response> responseValue = Optional.ofNullable(
                responseRepository.findByAssignment(assignmentId,
                        completedSurveyItem.getSurveyItemId()));
        if (completedSurveyItem.isFilesResponse()) {
            StringJoiner fileValues = new StringJoiner(",");
            newArrayList(completedSurveyItem.getValue().split(","))
                .stream()
                .filter(s -> !completedSurveyItem.getValidValues().contains(s))
                .forEach(s -> datastoreService.delete(s));
            completedSurveyItem.getValidValues().stream().forEach(s -> fileValues.add(s));
            completedSurveyItem.getFiles().stream()
                .filter(file -> !file.isEmpty())
                .forEach(file -> fileValues.add(datastoreService.save("shopncheck/" + assignmentId + "/", file)));
            completedSurveyItem.setValue(fileValues.toString());
        }

        Response response;
        if (responseValue.isPresent()) {
            response = new Response(responseValue.get().getId(), assignmentId, completedSurveyItem.getSurveyItemId(),
                    completedSurveyItem.getValue());
        } else {
            response = new Response(assignmentId, completedSurveyItem.getSurveyItemId(), completedSurveyItem.getValue());
        }
        responseRepository.save(response);
    }

    private String getAssignmentBodyMessage(Assignment assignment, Survey survey, Location location) {
        StringBuilder builder = new StringBuilder("Se le ha asignado la siguiente encuesta: ")
                .append(survey.getName())
                .append("<br/><br/>")
                .append("Punto de venta: ")
                .append(location.getAddress())
                .append("<br/><br/>")
                .append("Se puede ver la encuesta completada en el siguiente link: ")
                .append(siteUrl)
                .append("shopper/#!assignment/fill/")
                .append(assignment.getId());
        return builder.toString();
    }
}
