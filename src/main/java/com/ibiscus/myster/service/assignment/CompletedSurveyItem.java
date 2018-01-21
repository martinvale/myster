package com.ibiscus.myster.service.assignment;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CompletedSurveyItem {

    private long surveyItemId;

    private String value;

    private List<MultipartFile> files;

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

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFile(List<MultipartFile> files) {
        this.files = files;
    }
}
