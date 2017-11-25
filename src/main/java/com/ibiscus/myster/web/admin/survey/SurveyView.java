package com.ibiscus.myster.web.admin.survey;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Optional;

import com.ibiscus.myster.repository.shopper.ShopperRepository;
import com.ibiscus.myster.service.assignment.AssignmentService;
import com.ibiscus.myster.service.shopper.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibiscus.myster.model.survey.Survey;
import com.ibiscus.myster.service.survey.SurveyService;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.SingleSelectionModel;

@SpringView(name = "survey")
public class SurveyView implements View {

    private static final long serialVersionUID = 1L;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ShopperRepository shopperRepository;

    private Grid<Survey> grid = new Grid<Survey>(Survey.class);

    private List<Survey> surveys = newArrayList();

    private SurveyFormWindow formWindow;

    @SuppressWarnings("serial")
    @Override
    public Component getViewComponent() {
        surveys = newArrayList(surveyService.findAll());
        final ListDataProvider<Survey> dataProvider = new ListDataProvider<Survey>(surveys);
        VerticalLayout pageLayout = new VerticalLayout();

        formWindow = new SurveyFormWindow() {

            @Override
            public boolean onSave(SurveyDto surveyDto) {
                Survey survey = surveyDto.toSurvey();
                surveyService.save(survey);
                if (surveyDto.isNew()) {
                    surveys.add(survey);
                    dataProvider.refreshAll();
                } else {
                    dataProvider.refreshItem(survey);
                }
                return true;
            }

        };

        Button newButton = new Button("Nueva");
        newButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                formWindow.bindNew();
            }

        });
        Button editButton = new Button("Editar");
        editButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Optional<Survey> selectedSurvey = ((SingleSelectionModel<Survey>) grid.
                        getSelectionModel()).getSelectedItem();
                if (selectedSurvey.isPresent()) {
                    formWindow.bind(selectedSurvey.get());
                }
            }

        });
        Button navigateButton = new Button("Editar items");
        navigateButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Optional<Survey> selectedSurvey = grid.getSelectionModel().getFirstSelectedItem();
                if (selectedSurvey.isPresent()) {
                    UI.getCurrent().getNavigator().navigateTo("categories/"
                            + selectedSurvey.get().getId());
                }
            }

        });
        Button assignButton = new Button("Asignar");
        assignButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Optional<Survey> selectedSurvey = grid.getSelectionModel().getFirstSelectedItem();
                if (selectedSurvey.isPresent()) {
                    Survey survey = selectedSurvey.get();
                    SurveyAssignmentWindow surveyAssignmentWindow = new SurveyAssignmentWindow(survey.getName(), shopperRepository) {

                        @Override
                        public boolean onSave(SurveyAssignment surveyAssignment) {
                            assignmentService.assign(surveyAssignment);
                            return true;
                        }

                    };
                    surveyAssignmentWindow.bind(survey);
                }
            }

        });
        HorizontalLayout toolbarLayout = new HorizontalLayout();
        toolbarLayout.addComponent(newButton);
        toolbarLayout.addComponent(editButton);
        toolbarLayout.addComponent(navigateButton);
        toolbarLayout.addComponent(assignButton);
        pageLayout.addComponent(toolbarLayout);

        grid.setSizeFull();
        grid.setColumns("name", "enabled");
        grid.setDataProvider(dataProvider);
        pageLayout.addComponent(grid);

        return pageLayout;
    }

}
