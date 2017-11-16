package com.ibiscus.myster.web.ui;

import com.ibiscus.myster.model.survey.item.*;

public interface Visitor {

    void visit(SingleChoiceTaskItem singleChoiceTaskItem);
    void visit(TextItemTaskItem textItemTaskItem);
    void visit(NumberItemTaskItem numberItemTaskItem);
    void visit(TimeItemTaskItem timeItemTaskItem);
    void visit(FileTaskItem fileTaskItem);

}
