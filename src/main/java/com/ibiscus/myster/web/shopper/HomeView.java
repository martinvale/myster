package com.ibiscus.myster.web.shopper;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Optional;

import com.ibiscus.myster.service.survey.data.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibiscus.myster.service.assignment.AssignmentDescriptor;
import com.ibiscus.myster.service.assignment.AssignmentService;
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

@SpringView(name = "home")
public class HomeView implements View {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AssignmentService assigmentService;

    @Autowired
    private ResponseService responseService;

    private Grid<AssignmentDescriptor> grid = new Grid<AssignmentDescriptor>(AssignmentDescriptor.class);

    private List<AssignmentDescriptor> assignments = newArrayList();

    @SuppressWarnings("serial")
    public Component getViewComponent() {
        assignments = newArrayList(assigmentService.findDescriptorsByShopperId(1));
        final ListDataProvider<AssignmentDescriptor> dataProvider = new ListDataProvider<AssignmentDescriptor>(assignments);
        VerticalLayout pageLayout = new VerticalLayout();

        Button navigateButton = new Button("Completar");
        navigateButton.addClickListener(new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                Optional<AssignmentDescriptor> selectedAssignment = grid.getSelectionModel().getFirstSelectedItem();
                if (selectedAssignment.isPresent()) {
                    UI.getCurrent().getNavigator().navigateTo("assignment/fill/"
                            + selectedAssignment.get().getAssignmentId());
                }
            }

        });

        Button sendButton = new Button("Enviar");
        sendButton.addClickListener(new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                Optional<AssignmentDescriptor> selectedAssignment = grid.getSelectionModel().getFirstSelectedItem();
                if (selectedAssignment.isPresent() && selectedAssignment.get().isComplete()) {
                    responseService.send(selectedAssignment.get().getAssignmentId());
                    dataProvider.getItems().remove(selectedAssignment.get());
                    dataProvider.refreshAll();
                }
            }

        });

        HorizontalLayout toolbarLayout = new HorizontalLayout();
        toolbarLayout.addComponent(navigateButton);
        toolbarLayout.addComponent(sendButton);
        pageLayout.addComponent(toolbarLayout);

        grid.setSizeFull();
        grid.setColumns("company", "address", "complete");
        grid.getColumn("company")
            .setCaption("Cliente")
            .setWidth(200);
        grid.getColumn("address")
                .setCaption("Direccion");
        grid.getColumn("complete")
                .setCaption("Esta completa")
                .setWidth(200);

        grid.setDataProvider(dataProvider);
        pageLayout.addComponent(grid);

        return pageLayout;
    }

}
