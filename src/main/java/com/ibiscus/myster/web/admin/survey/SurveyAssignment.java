package com.ibiscus.myster.web.admin.survey;

import com.ibiscus.myster.model.company.Location;
import com.ibiscus.myster.model.shopper.Shopper;
import com.ibiscus.myster.model.survey.Survey;

public class SurveyAssignment {

    private final Survey survey;

    private Shopper shopper;

    private Location location;

    public SurveyAssignment(Survey survey) {
        this.survey = survey;
    }

    public Shopper getShopper() {
        return shopper;
    }

    public void setShopper(Shopper shopper) {
        this.shopper = shopper;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Survey getSurvey() {
        return survey;
    }
}
