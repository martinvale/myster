package com.ibiscus.myster.model.survey.item;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class FilesDto extends SurveyItemDto {

    public FilesDto(long id, String type, String title, String description, String value, int index, boolean filled) {
        super(id, type, title, description, value, index, filled);
    }

    public List<String> getFiles() {
        if (isNotBlank(getValue())) {
            return newArrayList(getValue().split(","));
        }
        return Collections.emptyList();
    }
}
