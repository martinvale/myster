package com.ibiscus.myster.web.admin.survey.items.choice;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Optional;

import com.ibiscus.myster.service.survey.item.ItemOptionService;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibiscus.myster.model.survey.item.Choice;
import com.ibiscus.myster.model.survey.item.SurveyItem;
import com.ibiscus.myster.service.survey.item.choice.ChoiceService;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.SingleSelectionModel;

@SpringView(name = "choices")
public class ChoiceView implements View {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private ItemOptionService itemOptionService;

    private Grid<Choice> grid = new Grid<Choice>(Choice.class);

    private ChoiceFormWindow formWindow;

    private List<Choice> choices = newArrayList();

    private long itemOptionId;

    private VerticalLayout pageLayout;

    @SuppressWarnings("serial")
    @Override
    public void enter(ViewChangeEvent event) {
        itemOptionId = Long.valueOf(event.getParameters());
        final SurveyItem surveyItem = itemOptionService.get(itemOptionId);

        choices = newArrayList(choiceService.findByItemOption(itemOptionId));
        final ListDataProvider<Choice> dataProvider = new ListDataProvider<Choice>(choices);
        formWindow = new ChoiceFormWindow() {

            @Override
            public boolean onSave(ChoiceDto choiceDto) {
                Choice choice = choiceDto.toChoice();
                choiceService.save(choice);
                if (choiceDto.isNew()) {
                    choices.add(choice);
                    dataProvider.refreshAll();
                } else {
                    dataProvider.refreshItem(choice);
                }
                return true;
            }
        };

        Button backButton = new Button("Volver");
        backButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("items/" + surveyItem.getCategoryId());
            }

        });
        pageLayout.addComponent(backButton);

        Button newButton = new Button("Nueva");
        newButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                formWindow.bindNew(itemOptionId);
            }

        });
        pageLayout.addComponent(newButton);

        Button editButton = new Button("Editar");
        editButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Optional<Choice> selectedChoice = ((SingleSelectionModel<Choice>) grid.
                        getSelectionModel()).getSelectedItem();
                if (selectedChoice.isPresent()) {
                    formWindow.bind(selectedChoice.get());
                }
            }

        });

        HorizontalLayout toolbarLayout = new HorizontalLayout();
        toolbarLayout.addComponent(newButton);
        toolbarLayout.addComponent(editButton);
        pageLayout.addComponent(toolbarLayout);

        grid.setSizeFull();
        grid.setColumns("description", "value");
        grid.getColumn("description")
            .setCaption("Descripcion");
        grid.getColumn("value")
            .setCaption("Valor")
            .setWidth(100);
        grid.setDataProvider(dataProvider);
        pageLayout.addComponent(grid);
    }

    @Override
    public Component getViewComponent() {
        pageLayout = new VerticalLayout();

        return pageLayout;
    }
}