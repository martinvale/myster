package com.ibiscus.myster.web.admin.survey.category;

import com.ibiscus.myster.web.admin.Form;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.TextField;

public class CategoryForm extends Form<CategoryDto> {

    private static final long serialVersionUID = 1L;

    public CategoryForm(Binder<CategoryDto> binder) {
        super(binder);

        TextField name = new TextField("Nombre");
        name.setSizeFull();
        addComponent(name);

        TextField position = new TextField("Posicion");
        position.setSizeFull();
        addComponent(position);

        binder.forField(name).bind("name");
        binder.forField(position)
                .withConverter(new StringToIntegerConverter("La posicion debe ser un numero"))
                .bind("position");
    }

}
