package com.ibiscus.myster.model.survey;

public class TaskDescription {

    private final long assignmentId;
    private final String client;
    private final String address;
    private final float payRate;
    private final boolean complete;

    public TaskDescription(long assignmentId, String client, String address, float payRate, boolean complete) {
        this.assignmentId = assignmentId;
        this.client = client;
        this.address = address;
        this.payRate = payRate;
        this.complete = complete;
    }

    public String getClient() {
        return client;
    }

    public String getAddress() {
        return address;
    }

    public float getPayRate() {
        return payRate;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public boolean isComplete() {
        return complete;
    }
}
