package com.ibiscus.myster.web.admin.survey;

import com.ibiscus.myster.model.company.PointOfSale;
import com.ibiscus.myster.model.shopper.Shopper;
import com.ibiscus.myster.model.survey.Survey;

public class SurveyAssignment {

    private final Survey survey;

    private Shopper shopper;

    private PointOfSale pointOfSale;

    public SurveyAssignment(Survey survey) {
        this.survey = survey;
    }

    public Shopper getShopper() {
        return shopper;
    }

    public void setShopper(Shopper shopper) {
        this.shopper = shopper;
    }

    public PointOfSale getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Survey getSurvey() {
        return survey;
    }
}
