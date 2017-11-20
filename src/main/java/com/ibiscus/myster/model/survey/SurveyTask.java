package com.ibiscus.myster.model.survey;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SurveyTask {

    private final TaskDescription taskDescription;

    private LocalDate visitDate;
    private int inHour;
    private int inMinute;
    private int outHour;
    private int outMinute;

    private final List<TaskCategory> taskCategories = newArrayList();

    public SurveyTask(TaskDescription taskDescription, List<TaskCategory> taskCategories, LocalDate visitDate,
                      LocalTime inTime, LocalTime outTime) {
        this.taskDescription = taskDescription;
        this.visitDate = visitDate;
        this.inHour = inTime.getHour();
        this.inMinute = inTime.getMinute();
        this.outHour = outTime.getHour();
        this.outMinute = outTime.getMinute();
        this.taskCategories.addAll(taskCategories);
    }

    public List<TaskCategory> getTaskCategories() {
        return Collections.unmodifiableList(taskCategories);
    }

    public TaskDescription getTaskDescription() {
        return taskDescription;
    }

    public boolean isComplete() {
        return taskCategories.stream().allMatch(taskCategory -> taskCategory.isComplete());
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public int getInHour() {
        return inHour;
    }

    public void setInHour(int inHour) {
        this.inHour = inHour;
    }

    public int getInMinute() {
        return inMinute;
    }

    public void setInMinute(int inMinute) {
        this.inMinute = inMinute;
    }

    public int getOutHour() {
        return outHour;
    }

    public void setOutHour(int outHour) {
        this.outHour = outHour;
    }

    public int getOutMinute() {
        return outMinute;
    }

    public void setOutMinute(int outMinute) {
        this.outMinute = outMinute;
    }
}
