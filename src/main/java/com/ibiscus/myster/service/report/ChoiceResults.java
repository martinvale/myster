package com.ibiscus.myster.service.report;

import java.text.DecimalFormat;
import java.text.Format;

public class ChoiceResults {

    private static final Format FORMATTER = new DecimalFormat("##");

    private final String description;
    private final Float value;
    private final Integer count;

    public ChoiceResults(String description) {
        this(description, 0F, 0);
    }

    public ChoiceResults(String description, Float value, Integer count) {
        this.description = description;
        this.value = value;
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return FORMATTER.format(value);
    }

    public Integer getCount() {
        return count;
    }
}
