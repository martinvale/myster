package com.ibiscus.myster.web.admin.survey.items.choice;

import com.ibiscus.myster.web.admin.Form;
import com.vaadin.data.Binder;
import com.vaadin.ui.TextField;

public class ChoiceForm extends Form<ChoiceDto> {

    private static final long serialVersionUID = 1L;

    private TextField description = new TextField("Descripcion");
    private TextField value = new TextField("Valor");

    public ChoiceForm(Binder<ChoiceDto> binder) {
        super(binder);
        binder.forField(description).bind("description");
        binder.forField(value).bind("value");

        description.setWidth(400, Unit.PIXELS);
        addComponent(description);
        addComponent(value);
    }

}
