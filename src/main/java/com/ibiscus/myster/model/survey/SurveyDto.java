package com.ibiscus.myster.model.survey;

import com.ibiscus.myster.model.survey.category.CategoryDto;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SurveyDto {

    private final long assignmentId;
    private final TaskDescription taskDescription;

    private Date visitDate;
    private int inHour;
    private int inMinute;
    private int outHour;
    private int outMinute;

    private final List<CategoryDto> categories = newArrayList();

    public SurveyDto(long assignmentId, TaskDescription taskDescription, List<CategoryDto> categories, Date visitDate,
                     LocalTime inTime, LocalTime outTime) {
        this.assignmentId = assignmentId;
        this.taskDescription = taskDescription;
        this.visitDate = visitDate;
        this.inHour = inTime.getHour();
        this.inMinute = inTime.getMinute();
        this.outHour = outTime.getHour();
        this.outMinute = outTime.getMinute();
        this.categories.addAll(categories);
    }

    public List<CategoryDto> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public TaskDescription getTaskDescription() {
        return taskDescription;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public int getInHour() {
        return inHour;
    }

    public int getInMinute() {
        return inMinute;
    }

    public int getOutHour() {
        return outHour;
    }

    public int getOutMinute() {
        return outMinute;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public boolean isComplete() {
        return categories.stream().allMatch(categoryDto -> categoryDto.isComplete());
    }
}
