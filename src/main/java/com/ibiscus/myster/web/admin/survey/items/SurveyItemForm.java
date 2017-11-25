package com.ibiscus.myster.web.admin.survey.items;

import com.ibiscus.myster.web.admin.Form;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.TextField;

class SurveyItemForm extends Form<SurveyItemDto> {

    private static final long serialVersionUID = 1L;

    SurveyItemForm(Binder<SurveyItemDto> binder) {
        super(binder);

        TextField title = new TextField("Titulo");
        title.setSizeFull();
        addComponent(title);

        TextField description = new TextField("Descripcion");
        description.setSizeFull();
        addComponent(description);

        TextField position = new TextField("Posicion");
        addComponent(position);

        binder.forField(title).bind("title");
        binder.forField(description).bind("description");
        binder.forField(position)
                .withConverter(new StringToIntegerConverter("La posicion debe ser un numero"))
                .bind("position");
    }

}
