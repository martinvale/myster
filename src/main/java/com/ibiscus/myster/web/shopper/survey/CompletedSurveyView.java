package com.ibiscus.myster.web.shopper.survey;

import com.ibiscus.myster.model.survey.SurveyTask2;
import com.ibiscus.myster.model.survey.item.SurveyValue;
import com.ibiscus.myster.service.assignment.AssignmentService;
import com.ibiscus.myster.service.survey.data.DatastoreService;
import com.ibiscus.myster.service.survey.data.ResponseService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringView(name = "assignment/view")
public class CompletedSurveyView implements View {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AssignmentService assigmentService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private DatastoreService datastoreService;

    private VerticalLayout pageLayout;

    @SuppressWarnings("serial")
    public void enter(ViewChangeEvent event) {
        long assignmentId = Long.valueOf(event.getParameters());
        SurveyTask2 surveyTask = assigmentService.getTask(assignmentId);
        pageLayout.addComponent(new FillSurveyForm(datastoreService, surveyTask, true) {

            @Override
            public void onSave(SurveyTask2 surveyTask, List<SurveyValue> surveyValues) {
                responseService.fill(assignmentId, surveyTask, surveyValues);
                UI.getCurrent().getNavigator().navigateTo("home");
            }

            @Override
            public void onFinish() {
                responseService.send(assignmentId);
                UI.getCurrent().getNavigator().navigateTo("home");
            }
        });
    }

    public Component getViewComponent() {
        pageLayout = new VerticalLayout();
        return pageLayout;
    }

}
