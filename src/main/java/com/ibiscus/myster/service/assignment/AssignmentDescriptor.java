package com.ibiscus.myster.service.assignment;

public class AssignmentDescriptor {

    private long assignmentId;
    private String client;
    private String address;
    private boolean complete;

    public AssignmentDescriptor(long assignmentId, String client, String address, boolean complete) {
        this.assignmentId = assignmentId;
        this.client = client;
        this.address = address;
        this.complete = complete;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public String getClient() {
        return client;
    }

    public String getAddress() {
        return address;
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssignmentDescriptor that = (AssignmentDescriptor) o;

        return assignmentId == that.assignmentId;
    }

    @Override
    public int hashCode() {
        return (int) (assignmentId ^ (assignmentId >>> 32));
    }
}
