package com.ibiscus.myster.service.assignment;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class CompletedSurvey {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date visitDate;

    private int inHour;
    private int inMinute;

    private int outHour;
    private int outMinute;

    private List<CompletedSurveyItem> completedSurveyItems;

    public List<CompletedSurveyItem> getCompletedSurveyItems() {
        return completedSurveyItems;
    }

    public void setCompletedSurveyItems(List<CompletedSurveyItem> completedSurveyItems) {
        this.completedSurveyItems = completedSurveyItems;
    }

    public int getInHour() {
        return inHour;
    }

    public void setInHour(int inHour) {
        this.inHour = inHour;
    }

    public int getInMinute() {
        return inMinute;
    }

    public void setInMinute(int inMinute) {
        this.inMinute = inMinute;
    }

    public int getOutHour() {
        return outHour;
    }

    public void setOutHour(int outHour) {
        this.outHour = outHour;
    }

    public int getOutMinute() {
        return outMinute;
    }

    public void setOutMinute(int outMinute) {
        this.outMinute = outMinute;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}
