package com.ibiscus.myster.web.shopper.survey;

import java.util.Optional;

public class FileSurveyTaskResponse extends SurveyTaskResponse {

    private final Optional<String> filename;

    public FileSurveyTaskResponse(long surveyItemId, Optional<String> filename) {
        super(surveyItemId);
        this.filename = filename;
    }

    @Override
    public Optional<String> getValue() {
        return filename;
    }
}
