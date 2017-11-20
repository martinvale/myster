package com.ibiscus.myster.model.survey.item;

import com.ibiscus.myster.model.survey.SurveyTaskItem;
import com.ibiscus.myster.web.ui.Visitor;

import java.util.Optional;

public class FileTaskItem extends SurveyTaskItem {

    private final File file;

    public FileTaskItem(File file, Optional<String> itemValue) {
        super(itemValue);
        this.file = file;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public File getFile() {
        return file;
    }
}
