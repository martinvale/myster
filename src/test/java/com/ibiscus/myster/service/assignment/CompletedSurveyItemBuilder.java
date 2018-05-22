package com.ibiscus.myster.service.assignment;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public class CompletedSurveyItemBuilder {

    private long surveyItemId = nextInt(0, 1000);
    private String value = "100";
    private boolean fileResponse = false;
    private List<MultipartFile> files;

    public CompletedSurveyItemBuilder withSurveyItemId(long surveyItemId) {
        this.surveyItemId = surveyItemId;
        return this;
    }

    public CompletedSurveyItemBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public CompletedSurveyItemBuilder withFileResponse(boolean value) {
        this.fileResponse = value;
        return this;
    }

    public CompletedSurveyItemBuilder withFiles(List<MultipartFile> value) {
        this.files = value;
        return this;
    }

    public CompletedSurveyItem build() {
        CompletedSurveyItem completedSurveyItem = new CompletedSurveyItem();
        completedSurveyItem.setSurveyItemId(surveyItemId);
        completedSurveyItem.setValue(value);
        completedSurveyItem.setFilesResponse(fileResponse);
        completedSurveyItem.setFiles(files);
        return completedSurveyItem;
    }

    public static final CompletedSurveyItemBuilder newCompletedSurveyItemBuilder() {
        return new CompletedSurveyItemBuilder();
    }
}
