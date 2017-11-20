package com.ibiscus.myster.web.admin.survey.category;

import com.ibiscus.myster.model.survey.category.Category;
import com.ibiscus.myster.repository.category.CategoryRepository;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@SpringView(name = "categories")
public class CategoryView implements View {

    private static final long serialVersionUID = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    private Grid<Category> grid = new Grid<>(Category.class);

    private CategoryFormWindow categoryWindow;

    private List<Category> items;

    private long surveyId;

    private VerticalLayout pageLayout;

    @SuppressWarnings("serial")
    @Override
    public void enter(ViewChangeEvent event) {
        surveyId = Long.valueOf(event.getParameters());

        items = categoryRepository.findBySurveyId(surveyId);
        final ListDataProvider<Category> dataProvider = new ListDataProvider<>(items);
        categoryWindow = new CategoryFormWindow() {

            @Override
            public boolean onSave(CategoryDto categoryDto) {
                Category category = categoryRepository.save(categoryDto.toCategory());
                if (categoryDto.isNew()) {
                    items.add(category);
                    dataProvider.refreshAll();
                } else {
                    dataProvider.refreshItem(category);
                }
                return true;
            }
        };

        Button backButton = new Button("Volver");
        backButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("survey");
            }

        });
        pageLayout.addComponent(backButton);

        Button newButton = new Button("Nueva");
        newButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                categoryWindow.bindNew(new CategoryDto(surveyId));
            }

        });

        Button editButton = new Button("Editar");
        editButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Optional<Category> selectedCategory = ((SingleSelectionModel<Category>) grid.
                        getSelectionModel()).getSelectedItem();
                if (selectedCategory.isPresent()) {
                    categoryWindow.bind(new CategoryDto(selectedCategory.get()));
                }
            }

        });
        Button navigateButton = new Button("Editar items");
        navigateButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Optional<Category> selectedCategory = grid.getSelectionModel().getFirstSelectedItem();
                if (selectedCategory.isPresent()) {
                    UI.getCurrent().getNavigator().navigateTo("items/"
                            + selectedCategory.get().getId());
                }
            }

        });
        HorizontalLayout toolbarLayout = new HorizontalLayout();
        toolbarLayout.addComponent(newButton);
        toolbarLayout.addComponent(editButton);
        toolbarLayout.addComponent(navigateButton);
        pageLayout.addComponent(toolbarLayout);

        grid.setSizeFull();
        grid.setColumns("name");
        grid.setDataProvider(dataProvider);
        pageLayout.addComponent(grid);
    }

    @Override
    public Component getViewComponent() {
        pageLayout = new VerticalLayout();

        return pageLayout;
    }

}
