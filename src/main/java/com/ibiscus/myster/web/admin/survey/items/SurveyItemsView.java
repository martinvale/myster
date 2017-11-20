package com.ibiscus.myster.web.admin.survey.items;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Optional;

import com.ibiscus.myster.model.survey.category.Category;
import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.repository.category.CategoryRepository;
import com.ibiscus.myster.service.survey.item.ItemOptionService;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibiscus.myster.model.survey.item.SurveyItem;
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

@SpringView(name = "items")
public class SurveyItemsView implements View {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ItemOptionService itemOptionService;

    @Autowired
    private CategoryRepository categoryRepository;

    private final SurveyItemDtoFactory surveyItemDtoFactory = new SurveyItemDtoFactory();

    private Grid<SurveyItem> grid = new Grid<>(SurveyItem.class);

    private SurveyItemFormWindow surveyItemWindow;

    private List<SurveyItem> items;

    private long categoryId;

    private VerticalLayout pageLayout;

    @SuppressWarnings("serial")
    @Override
    public void enter(ViewChangeEvent event) {
        categoryId = Long.valueOf(event.getParameters());
        final Category category = categoryRepository.findOne(categoryId);

        items = newArrayList(itemOptionService.findByCategoryId(categoryId));
        final ListDataProvider<SurveyItem> dataProvider = new ListDataProvider<>(items);
        surveyItemWindow = new SurveyItemFormWindow() {

            @Override
            public boolean onSave(SurveyItemDto surveyItemDto) {
                AbstractSurveyItem surveyItem = itemOptionService.save(surveyItemDto.toSurveyItem());
                if (surveyItemDto.isNew()) {
                    items.add(surveyItem);
                    dataProvider.refreshAll();
                } else {
                    dataProvider.refreshItem(surveyItem);
                }
                return true;
            }
        };

        Button backButton = new Button("Volver");
        backButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("categories/" + category.getSurveyId());
            }

        });
        pageLayout.addComponent(backButton);

        Button newSingleChoiceButton = new Button("Nueva opcion simple");
        newSingleChoiceButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                surveyItemWindow.bindNew(new SingleChoiceDto(categoryId));
            }

        });
        Button newTextItemButton = new Button("Nuevo item de texto");
        newTextItemButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                surveyItemWindow.bindNew(new TextItemDto(categoryId));
            }

        });
        Button newNumberItemButton = new Button("Nuevo item de numero");
        newNumberItemButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                surveyItemWindow.bindNew(new NumberItemDto(categoryId));
            }

        });
        Button newTimeItemButton = new Button("Nuevo item de tiempo");
        newTimeItemButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                surveyItemWindow.bindNew(new TimeItemDto(categoryId));
            }

        });
        Button editButton = new Button("Editar");
        editButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Optional<SurveyItem> selectedSurveyItem = ((SingleSelectionModel<SurveyItem>) grid.
                        getSelectionModel()).getSelectedItem();
                if (selectedSurveyItem.isPresent()) {
                    surveyItemWindow.bind(surveyItemDtoFactory.getSurveyItemDto(selectedSurveyItem.get()));
                }
            }

        });
        Button navigateButton = new Button("Editar items");
        navigateButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Optional<SurveyItem> selectedItem = grid.getSelectionModel().getFirstSelectedItem();
                if (selectedItem.isPresent()) {
                    UI.getCurrent().getNavigator().navigateTo("choices/"
                            + selectedItem.get().getId());
                }
            }

        });
        HorizontalLayout toolbarLayout = new HorizontalLayout();
        toolbarLayout.addComponent(newSingleChoiceButton);
        toolbarLayout.addComponent(newTextItemButton);
        toolbarLayout.addComponent(newNumberItemButton);
        toolbarLayout.addComponent(newTimeItemButton);
        toolbarLayout.addComponent(editButton);
        toolbarLayout.addComponent(navigateButton);
        pageLayout.addComponent(toolbarLayout);

        grid.setSizeFull();
        grid.setColumns("title");
        grid.setDataProvider(dataProvider);
        pageLayout.addComponent(grid);
    }

    @Override
    public Component getViewComponent() {
        pageLayout = new VerticalLayout();

        return pageLayout;
    }

}
