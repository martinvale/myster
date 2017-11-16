package com.ibiscus.myster.service.assignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ibiscus.myster.model.company.Location;
import com.ibiscus.myster.model.security.User;
import com.ibiscus.myster.model.shopper.Shopper;
import com.ibiscus.myster.model.survey.*;
import com.ibiscus.myster.model.survey.category.Category;
import com.ibiscus.myster.model.survey.data.Response;
import com.ibiscus.myster.model.survey.item.*;
import com.ibiscus.myster.repository.category.CategoryRepository;
import com.ibiscus.myster.repository.security.UserRepository;
import com.ibiscus.myster.repository.survey.data.ResponseRepository;
import com.ibiscus.myster.repository.survey.item.ItemOptionRepository;
import com.ibiscus.myster.service.communication.MailSender;
import com.ibiscus.myster.web.admin.survey.SurveyAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibiscus.myster.repository.assignment.AssignmentRepository;

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

    public SurveyTask getTask(long id) {
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
                } else if (item instanceof File) {
                    taskItems.add(new FileTaskItem((File) item, Optional.ofNullable(value)));
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
        return new SurveyTask(taskDescription, taskCategories, visitDate, inTime, outTime);
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
