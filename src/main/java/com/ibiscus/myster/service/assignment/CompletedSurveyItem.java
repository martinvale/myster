package com.ibiscus.myster.service.assignment;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class CompletedSurveyItem {

    private long surveyItemId;

    private String value;

    private boolean filesResponse;

    private List<MultipartFile> files;

    private List<String> validValues = newArrayList();

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

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<String> getValidValues() {
        return validValues;
    }

    public void setValidValues(List<String> validValues) {
        this.validValues = validValues;
    }

    public boolean isFilesResponse() {
        return filesResponse;
    }

    public void setFilesResponse(boolean value) {
        filesResponse = value;
    }
}
