package com.ibiscus.myster.web.admin.survey;

import com.ibiscus.myster.model.survey.Survey;
import com.ibiscus.myster.repository.shopper.ShopperRepository;
import com.ibiscus.myster.web.admin.FormWindow;
import com.vaadin.data.Binder;
import com.vaadin.ui.UI;

public abstract class SurveyAssignmentWindow extends FormWindow<SurveyAssignment> {

    private static final long serialVersionUID = 1L;

    public SurveyAssignmentWindow(String surveyName, ShopperRepository shopperRepository) {
        super("Datos de la encuesta", new AssignSurveyForm(
                new Binder<SurveyAssignment>(SurveyAssignment.class), surveyName, shopperRepository));
    }

    public void bind(Survey survey) {
        getForm().setBean(new SurveyAssignment(survey));
        UI.getCurrent().addWindow(this);
    }

    public void bindNew() {
        getForm().setBean(new SurveyAssignment(null));
        UI.getCurrent().addWindow(this);
    }
}
