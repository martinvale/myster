package com.ibiscus.myster.service.survey.data;

import com.ibiscus.myster.model.shopper.Shopper;
import com.ibiscus.myster.model.survey.Assignment;
import com.ibiscus.myster.model.survey.SurveyTask2;
import com.ibiscus.myster.model.survey.TaskCategory;
import com.ibiscus.myster.model.survey.data.Response;
import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.SurveyValue;
import com.ibiscus.myster.repository.assignment.AssignmentRepository;
import com.ibiscus.myster.repository.shopper.ShopperRepository;
import com.ibiscus.myster.repository.survey.data.ResponseRepository;
import com.ibiscus.myster.repository.survey.item.ItemOptionRepository;
import com.ibiscus.myster.service.communication.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ShopperRepository shopperRepository;

    @Autowired
    private ItemOptionRepository itemOptionRepository;

    @Autowired
    private MailSender mailSender;

    private final String siteUrl = "http://localhost:8080/";

    public void fill(long assignmentId, SurveyTask2 surveyTask, List<SurveyValue> values) {
        Assignment assignment = assignmentRepository.findOne(assignmentId);
        Assignment filledAssignment = new Assignment(assignmentId, assignment.getSurvey(), assignment.getShopperId(),
                assignment.getLocation(), Date.valueOf(surveyTask.getVisitDate()),
                Time.valueOf(LocalTime.of(surveyTask.getInHour(), surveyTask.getInMinute())),
                Time.valueOf(LocalTime.of(surveyTask.getOutHour(), surveyTask.getOutMinute())));
        for (SurveyValue surveyValue : values) {
            String value = surveyValue.getValue();

            Optional<Response> responseValue = Optional.ofNullable(
                    responseRepository.findByAssignment(assignment.getId(), surveyValue.getSurveyItemId()));
            Response response;
            if (responseValue.isPresent()) {
                AbstractSurveyItem surveyItem = itemOptionRepository.findOne(surveyValue.getSurveyItemId());
                /*if (surveyItem instanceof FileItem) {
                    value = datastoreService.save(value);
                }*/
                response = new Response(responseValue.get().getId(), assignmentId, surveyValue.getSurveyItemId(),
                        value);
            } else {
                response = new Response(assignmentId, surveyValue.getSurveyItemId(), value);
            }
            responseRepository.save(response);
        }
        int itemCount = 0;
        for (TaskCategory taskCategory : surveyTask.getTaskCategories()) {
            itemCount += taskCategory.getItems().size();
        }
        if (values.size() == itemCount - 1) {
            filledAssignment.finished();
        } else {
            filledAssignment.started();
        }
        assignmentRepository.save(filledAssignment);
    }

    public void send(long assignmentId) {
        Assignment assignment = assignmentRepository.findOne(assignmentId);
        if (!assignment.isFinished()) {
            throw new RuntimeException("The assigment " + assignmentId + " is incomplete, cannot send to the reviewer");
        }
        String message = getSurveyCompleteMessage(assignment);
        //mailSender.sendMail("martinvalletta@gmail.com", "Encuesta completa", message);
        assignment.sent();
        assignmentRepository.save(assignment);
    }

    private String getSurveyCompleteMessage(Assignment assignment) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Shopper shopper = shopperRepository.findOne(assignment.getShopperId());
        StringBuilder builder = new StringBuilder("Se completo la siguiente encuesta: ")
                .append(assignment.getSurvey().getName())
                .append("<br/><br/>")
                .append("Shooper: ")
                .append(shopper.getDisplayName())
                .append("<br/>")
                .append("Punto de venta: ")
                .append(assignment.getLocation().getAddress())
                .append("<br/>")
                .append("Fecha de la visita: ")
                .append(dateFormat.format(assignment.getVisitDate()))
                .append("<br/><br/>")
                .append("Se puede ver la encuesta completada en el siguiente link: ")
                .append(siteUrl)
                .append("shopper/#!assignment/view/")
                .append(assignment.getId());
        return builder.toString();
    }
}
