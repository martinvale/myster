package com.ibiscus.myster.model.survey;

public class TaskDescription {

    private final String clientName;
    private final String surveyCode;
    private final String address;
    private final float payRate;

    public TaskDescription(String clientName, String surveyCode, String address, float payRate) {
        this.clientName = clientName;
        this.surveyCode = surveyCode;
        this.address = address;
        this.payRate = payRate;
    }

    public String getClientName() {
        return clientName;
    }

    public String getSurveyCode() {
        return surveyCode;
    }

    public String getAddress() {
        return address;
    }

    public float getPayRate() {
        return payRate;
    }

}
