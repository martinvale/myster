package com.ibiscus.myster.service.assignment;

import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class CompletedSurveyBuilder {

    private int inHour = nextInt(0, 24);
    private int inMinute = nextInt(0, 61);
    private int outHour = nextInt(0, 24);
    private int outMinute = nextInt(0, 61);
    private Date visit = new Date();
    private List<CompletedSurveyItem> items = newArrayList();

    public CompletedSurveyBuilder addItem(CompletedSurveyItem item) {
        items.add(item);
        return this;
    }

    public CompletedSurvey build() {
        CompletedSurvey completedSurvey = new CompletedSurvey();
        completedSurvey.setVisitDate(visit);
        completedSurvey.setInHour(inHour);
        completedSurvey.setInMinute(inMinute);
        completedSurvey.setOutHour(outHour);
        completedSurvey.setOutMinute(outMinute);
        completedSurvey.setCompletedSurveyItems(items);
        return completedSurvey;
    }

    public static final CompletedSurveyBuilder newCompletedSurveyBuilder() {
        return new CompletedSurveyBuilder();
    }
}
