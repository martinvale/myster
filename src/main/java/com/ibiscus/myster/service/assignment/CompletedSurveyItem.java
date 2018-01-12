package com.ibiscus.myster.service.assignment;

import org.springframework.web.multipart.MultipartFile;

public class CompletedSurveyItem {

    private long surveyItemId;

    private String value;

    private MultipartFile file;

    public long getSurveyItemId() {
        return surveyItemId;
    }

    public void setSurveyItemId(long surveyItemId) {
        this.surveyItemId = surveyItemId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
