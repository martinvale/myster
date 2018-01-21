package com.ibiscus.myster.web.shopper.survey;

import com.ibiscus.myster.model.survey.SurveyTask2;
import com.ibiscus.myster.model.survey.SurveyTaskItem;
import com.ibiscus.myster.model.survey.TaskCategory;
import com.ibiscus.myster.model.survey.TaskDescription;
import com.ibiscus.myster.model.survey.item.*;
import com.ibiscus.myster.service.survey.data.DatastoreService;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class FillSurveyForm extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private final SurveyTaskItemRenderer renderer;

    private final Binder<SurveyTask2> binder = new Binder<SurveyTask2>(SurveyTask2.class);

    public FillSurveyForm(DatastoreService datastoreService, SurveyTask2 surveyTask, boolean readOnly) {
        super();
        setEnabled(!readOnly);
        renderer = new SurveyTaskItemRenderer(datastoreService, this);
        binder.setBean(surveyTask);

        VerticalLayout summaryLayout = new VerticalLayout();
        TaskDescription taskDescription = surveyTask.getTaskDescription();
        summaryLayout.addComponent(new Label("Cliente: " + taskDescription.getClientName()));
        summaryLayout.addComponent(new Label("Direccion: " + taskDescription.getAddress()));
        NumberFormat moneyFormat = new DecimalFormat("#.##");
        summaryLayout.addComponent(new Label("Honorarios: $" + moneyFormat.format(taskDescription.getPayRate())));

        summaryLayout.addComponent(new Label("Fecha"));
        DateField visitDate = new DateField();
        summaryLayout.addComponent(visitDate);
        List<Integer> hours = newArrayList();
        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }
        List<Integer> minutes = newArrayList();
        for (int i = 0; i < 60; i++) {
            minutes.add(i);
        }
        ComboBox inHour = new ComboBox(null, hours);
        inHour.setWidth(100, Unit.PIXELS);
        ComboBox inMinutes = new ComboBox(null, minutes);
        inMinutes.setWidth(100, Unit.PIXELS);
        HorizontalLayout inLayout = new HorizontalLayout();
        summaryLayout.addComponent(new Label("Hora de llegada"));
        inLayout.addComponent(inHour);
        inLayout.addComponent(inMinutes);
        summaryLayout.addComponent(inLayout);
        ComboBox outHour = new ComboBox(null, hours);
        outHour.setWidth(100, Unit.PIXELS);
        ComboBox outMinutes = new ComboBox(null, minutes);
        outMinutes.setWidth(100, Unit.PIXELS);
        HorizontalLayout outLayout = new HorizontalLayout();
        summaryLayout.addComponent(new Label("Hora de salida"));
        outLayout.addComponent(outHour);
        outLayout.addComponent(outMinutes);
        summaryLayout.addComponent(outLayout);

        binder.forField(visitDate)
                .bind("visitDate");
        binder.forField(inHour)
                .bind("inHour");
        binder.forField(inMinutes)
                .bind("inMinute");
        binder.forField(outHour)
                .bind("outHour");
        binder.forField(outMinutes)
                .bind("outMinute");

        Panel assignDescription = new Panel("Resumen del cuestionario", summaryLayout);
        addComponent(assignDescription);

        for (TaskCategory taskCategory : surveyTask.getTaskCategories()) {
            Label categoryLabel = new Label(taskCategory.getName());
            categoryLabel.setStyleName("category");
            addComponent(categoryLabel);
            for (SurveyTaskItem taskItem : taskCategory.getItems()) {
                taskItem.accept(renderer);
            }
        }

        if (!readOnly) {
            HorizontalLayout buttonBar = new HorizontalLayout();
            addComponent(buttonBar);
            Button backButton = new Button("Guardar");
            backButton.addClickListener(e -> saveSurveyTask());
            buttonBar.addComponent(backButton);

            /*Button sendButton = new Button("Enviar");
            sendButton.addClickListener(e -> onFinish());
            buttonBar.addComponent(sendButton);*/
        }
    }

    private void saveSurveyTask() {
        List<SurveyValue> surveyTaskItems = newArrayList();
        for (SurveyTaskResponse taskItem : renderer.getTaskItems()) {
            if (taskItem.getValue().isPresent()) {
                surveyTaskItems.add(new SurveyValue(taskItem.getSurveyItemId(),
                        taskItem.getValue().get()));
            }
        }
        onSave(binder.getBean(), surveyTaskItems);
    }

    public abstract void onSave(SurveyTask2 surveyTask, List<SurveyValue> surveyValues);

    public abstract void onFinish();

}
