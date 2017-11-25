package com.ibiscus.myster.web.admin.survey;

import com.ibiscus.myster.model.survey.Survey;
import com.ibiscus.myster.web.admin.FormWindow;
import com.vaadin.data.Binder;
import com.vaadin.ui.UI;

public abstract class SurveyFormWindow extends FormWindow<SurveyDto> {

    private static final long serialVersionUID = 1L;

    public SurveyFormWindow() {
        super("Datos de la encuesta", new SurveyForm(
                new Binder<SurveyDto>(SurveyDto.class)));
    }

    public void bind(Survey survey) {
        getForm().setBean(new SurveyDto(survey));
        UI.getCurrent().addWindow(this);
    }

    public void bindNew() {
        getForm().setBean(new SurveyDto());
        UI.getCurrent().addWindow(this);
    }
}
