package com.ibiscus.myster.model.survey.item;

import com.ibiscus.myster.model.survey.SurveyTaskItem;
import com.ibiscus.myster.web.ui.Visitor;

import java.util.Optional;

public class FileTaskItem extends SurveyTaskItem {

    private final FileItem file;

    public FileTaskItem(FileItem fileItem, Optional<String> itemValue) {
        super(itemValue);
        this.file = fileItem;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public FileItem getFile() {
        return file;
    }
}
