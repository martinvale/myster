package com.ibiscus.myster.service.report;

import java.util.Optional;

import static com.ibiscus.myster.service.report.MonthInterval.currentMonthInterval;

public class ReportCriteriaBuilder {

    private Long surveyId;
    private MonthInterval interval = currentMonthInterval();
    private Optional<String> code = Optional.empty();
    private Optional<String> name = Optional.empty();
    private Optional<Long> stateId = Optional.empty();

    public ReportCriteriaBuilder(Long surveyId) {
        this.surveyId = surveyId;
    }

    public ReportCriteriaBuilder usingCriteria(ReportCriteria criteria) {
        this.interval = criteria.getMonthInterval();
        this.code = criteria.getCode();
        this.name = criteria.getName();
        this.stateId = criteria.getStateId();
        return this;
    }

    public ReportCriteriaBuilder withInterval(MonthInterval value) {
        this.interval = value;
        return this;
    }

    public ReportCriteriaBuilder withCode(String value) {
        this.code = Optional.of(value);
        return this;
    }

    public ReportCriteriaBuilder withName(String value) {
        this.name = Optional.of(value);
        return this;
    }

    public ReportCriteriaBuilder withStateId(Long value) {
        this.stateId = Optional.of(value);
        return this;
    }

    public ReportCriteria build() {
        return new ReportCriteria(surveyId, interval, code, name, stateId);
    }

    public static ReportCriteriaBuilder newReportCriteriaBuilder(Long surveyId) {
        return new ReportCriteriaBuilder(surveyId);
    }
}
