package com.ibiscus.myster.web.admin.survey;

import com.ibiscus.myster.web.admin.Form;
import com.vaadin.data.Binder;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

public class SurveyForm extends Form<SurveyDto> {

    private static final long serialVersionUID = 1L;

    private TextField name = new TextField("Nombre");
    private CheckBox enabled = new CheckBox("Habilitado");

    public SurveyForm(Binder<SurveyDto> binder) {
        super(binder);
        binder.forField(name).bind("name");
        binder.forField(enabled).bind("enabled");

        name.setWidth(400, Unit.PIXELS);
        addComponent(name);
        addComponent(enabled);
    }

}
