package com.ibiscus.myster.service.report;

import java.util.Optional;

public class ReportCriteria {

    private final Long surveyId;
    private final MonthInterval monthInterval;
    private final Optional<String> code;
    private final Optional<String> name;
    private final Optional<Long> stateId;

    public ReportCriteria(Long surveyId, MonthInterval monthInterval, Optional<String> code, Optional<String> name,
                          Optional<Long> stateId) {
        this.surveyId = surveyId;
        this.monthInterval = monthInterval;
        this.code = code;
        this.name = name;
        this.stateId = stateId;
    }

    public MonthInterval getMonthInterval() {
        return monthInterval;
    }

    public Optional<String> getCode() {
        return code;
    }

    public Optional<String> getName() {
        return name;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public Optional<Long> getStateId() {
        return stateId;
    }
}
