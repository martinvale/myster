package com.ibiscus.myster.web.admin.survey.items;

import com.ibiscus.myster.web.admin.FormWindow;
import com.vaadin.data.Binder;
import com.vaadin.ui.UI;

public abstract class SurveyItemFormWindow extends FormWindow<SurveyItemDto> {

    private static final long serialVersionUID = 1L;

    public SurveyItemFormWindow() {
        super("Datos del item", new SurveyItemForm(
                new Binder<SurveyItemDto>(SurveyItemDto.class)));
    }

    public void bind(SurveyItemDto surveyItemDto) {
        getForm().setBean(surveyItemDto);
        UI.getCurrent().addWindow(this);
    }

    public void bindNew(SurveyItemDto surveyItemDto) {
        getForm().setBean(surveyItemDto);
        UI.getCurrent().addWindow(this);
    }

}
