package com.ibiscus.myster.web.shopper.survey;

import com.ibiscus.myster.model.survey.item.*;
import com.ibiscus.myster.service.survey.data.DatastoreService;
import com.ibiscus.myster.web.ui.Visitor;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SurveyTaskItemRenderer implements Visitor {

    private final DatastoreService datastoreService;
    private final Layout container;
    private final List<SurveyTaskResponse> taskItems = newArrayList();

    SurveyTaskItemRenderer(DatastoreService datastoreService, Layout container) {
        this.datastoreService = datastoreService;
        this.container = container;
    }

    @Override
    public void visit(SingleChoiceTaskItem singleChoiceTaskItem) {
        VerticalLayout surveyItemContainer = new VerticalLayout();
        surveyItemContainer.setMargin(new MarginInfo(false));
        surveyItemContainer.addStyleName("survey-item");
        container.addComponent(surveyItemContainer);

        SingleChoice singleChoice = singleChoiceTaskItem.getSingleChoice();
        ListDataProvider<Choice> values = DataProvider.ofCollection(singleChoice.getChoices());
        RadioButtonGroup<Choice> radioButton = new RadioButtonGroup<>(singleChoice.getTitle(), values);
        radioButton.setRequiredIndicatorVisible(true);
        if (singleChoiceTaskItem.getValue().isPresent()) {
            radioButton.setSelectedItem(singleChoice.getChoiceByValue(singleChoiceTaskItem.getValue().get()));
        }
        radioButton.setItemCaptionGenerator(item -> item.getDescription());
        surveyItemContainer.addComponent(radioButton);

        Label description = new Label(singleChoice.getDescription());
        description.setStyleName("description");
        surveyItemContainer.addComponent(description);

        taskItems.add(new SingleChoiceSurveyTaskResponse(singleChoice.getId(), radioButton));
    }

    @Override
    public void visit(TextItemTaskItem textItemTaskItem) {
        VerticalLayout surveyItemContainer = new VerticalLayout();
        surveyItemContainer.setMargin(new MarginInfo(false));
        surveyItemContainer.addStyleName("survey-item");
        container.addComponent(surveyItemContainer);

        TextItem textItem = textItemTaskItem.getTextItem();
        TextArea textField = new TextArea(textItem.getTitle());
        if (textItemTaskItem.getValue().isPresent()) {
            textField.setValue(textItemTaskItem.getValue().get());
        }
        textField.setWidth(600, Sizeable.Unit.PIXELS);
        surveyItemContainer.addComponent(textField);

        Label description = new Label(textItem.getDescription());
        description.setStyleName("description");
        surveyItemContainer.addComponent(description);

        taskItems.add(new SimpleFieldSurveyTaskResponse(textItem.getId(), textField));
    }

    @Override
    public void visit(NumberItemTaskItem numberItemTaskItem) {
        VerticalLayout surveyItemContainer = new VerticalLayout();
        surveyItemContainer.setMargin(new MarginInfo(false));
        surveyItemContainer.addStyleName("survey-item");
        container.addComponent(surveyItemContainer);

        NumberItem numberItem = numberItemTaskItem.getNumberItem();
        TextField textField = new TextField(numberItem.getTitle());
        if (numberItemTaskItem.getValue().isPresent()) {
            textField.setValue(numberItemTaskItem.getValue().get());
        }
        textField.setWidth(600, Sizeable.Unit.PIXELS);
        surveyItemContainer.addComponent(textField);

        Label description = new Label(numberItem.getDescription());
        description.setStyleName("description");
        surveyItemContainer.addComponent(description);

        taskItems.add(new SimpleFieldSurveyTaskResponse(numberItem.getId(), textField));
    }

    @Override
    public void visit(TimeItemTaskItem timeItemTaskItem) {
        VerticalLayout surveyItemContainer = new VerticalLayout();
        surveyItemContainer.setMargin(new MarginInfo(false));
        surveyItemContainer.addStyleName("survey-item");
        container.addComponent(surveyItemContainer);

        TimeItem timeItem = timeItemTaskItem.getTimeItem();
        List<String> minutes = newArrayList();
        for (int i = 0; i < 60; i++) {
            minutes.add(String.valueOf(i));
        }
        ComboBox<String> timeField = new ComboBox<>(timeItem.getTitle(), minutes);
        timeField.setItemCaptionGenerator(item -> item + " minutos");
        if (timeItemTaskItem.getValue().isPresent()) {
            timeField.setValue(timeItemTaskItem.getValue().get());
        }
        timeField.setWidth(600, Sizeable.Unit.PIXELS);
        surveyItemContainer.addComponent(timeField);

        Label description = new Label(timeItem.getDescription());
        description.setStyleName("description");
        surveyItemContainer.addComponent(description);

        taskItems.add(new SimpleFieldSurveyTaskResponse(timeItem.getId(), timeField));
    }

    @Override
    public void visit(FileTaskItem fileTaskItem) {
        VerticalLayout surveyItemContainer = new VerticalLayout();
        surveyItemContainer.setMargin(new MarginInfo(false));
        surveyItemContainer.addStyleName("survey-item");
        container.addComponent(surveyItemContainer);

        FileItem fileItem = fileTaskItem.getFile();
        surveyItemContainer.addComponent(new Label(fileItem.getTitle()));
        Label description = new Label(fileItem.getDescription());
        description.setStyleName("description");
        surveyItemContainer.addComponent(description);

        Image image = new Image();
        image.setHeight(150, Sizeable.Unit.PIXELS);
        if (fileTaskItem.getValue().isPresent()) {
            java.io.File fileImage = datastoreService.get(fileTaskItem.getValue().get());
            image.setSource(new FileResource(fileImage));
        }
        ImageUploader imageUploader = new ImageUploader(image);
        Upload uploadField = new Upload(null, imageUploader);
        uploadField.addSucceededListener(imageUploader);
        uploadField.setButtonCaption("Subir ahora");
        surveyItemContainer.addComponent(uploadField);

        surveyItemContainer.addComponent(image);

        taskItems.add(new SimpleFieldSurveyTaskResponse(fileItem.getId(), imageUploader));
    }

    public List<SurveyTaskResponse> getTaskItems() {
        return Collections.unmodifiableList(taskItems);
    }
}
