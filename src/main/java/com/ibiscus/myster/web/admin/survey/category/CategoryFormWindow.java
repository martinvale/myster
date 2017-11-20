package com.ibiscus.myster.web.admin.survey.category;

import com.ibiscus.myster.web.admin.FormWindow;
import com.vaadin.data.Binder;
import com.vaadin.ui.UI;

public abstract class CategoryFormWindow extends FormWindow<CategoryDto> {

    private static final long serialVersionUID = 1L;

    public CategoryFormWindow() {
        super("Datos de la categoria", new CategoryForm(
                new Binder<CategoryDto>(CategoryDto.class)));
    }

    public void bind(CategoryDto categoryDto) {
        getForm().setBean(categoryDto);
        UI.getCurrent().addWindow(this);
    }

    public void bindNew(CategoryDto categoryDto) {
        getForm().setBean(categoryDto);
        UI.getCurrent().addWindow(this);
    }

}
