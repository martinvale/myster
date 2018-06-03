package com.ibiscus.myster.service.report;

import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDate;

public class MonthInterval {

    private static final Format FORMATTER = new DecimalFormat("00");

    private final int year;
    private final int month;

    public MonthInterval(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public LocalDate getInitialDate() {
        return LocalDate.of(year, month, 1);
    }

    public LocalDate getFinalDate() {
        return LocalDate.of(year, month, 1).plusMonths(1);
    }

    public MonthInterval getPreviousMonthInterval() {
        LocalDate previousMonth = LocalDate.of(year, month, 1).minusMonths(1);
        return new MonthInterval(previousMonth.getYear(), previousMonth.getMonthValue());
    }

    public static MonthInterval currentMonthInterval() {
        LocalDate now = LocalDate.now();
        return new MonthInterval(now.getYear(), now.getMonthValue());
    }

    public static MonthInterval parse(String phase) {
        String[] dateParts = phase.split("-");
        return new MonthInterval(Integer.valueOf(dateParts[1]), Integer.valueOf(dateParts[0]));
    }

    @Override
    public String toString() {
        return new StringBuilder(FORMATTER.format(month))
                .append("-")
                .append(year)
                .toString();
    }
}
