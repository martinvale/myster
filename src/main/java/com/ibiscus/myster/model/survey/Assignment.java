package com.ibiscus.myster.model.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ibiscus.myster.model.company.Location;

import java.sql.Date;
import java.sql.Time;

@Entity(name = "assignment")
public class Assignment {

    public enum STATE {
        PENDING, STARTED, FINISHED, CLOSED
    }

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(name = "shopper_id")
    private long shopperId;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "pay_rate")
    private float payRate;

    @Enumerated(EnumType.STRING)
    private STATE state = STATE.PENDING;

    @Column(name = "visit_date")
    private Date visitDate;

    @Column(name = "in_time")
    private Time inTime;

    @Column(name = "out_time")
    private Time outTime;

    Assignment() {
    }

    public Assignment(Survey survey, long shopperId, Location location) {
        this.survey = survey;
        this.shopperId = shopperId;
        this.location = location;
    }

    public Assignment(long id, Survey survey, long shopperId, Location location, float payRate, STATE state,
                      Date visitDate, Time inTime, Time outTime) {
        this.id = id;
        this.survey = survey;
        this.shopperId = shopperId;
        this.location = location;
        this.payRate = payRate;
        this.state = state;
        this.visitDate = visitDate;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public long getId() {
        return id;
    }

    public long getShopperId() {
        return shopperId;
    }

    public Survey getSurvey() {
        return survey;
    }

    public Location getLocation() {
        return location;
    }

    public float getPayRate() {
        return payRate;
    }

    public STATE getState() {
        return state;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public Time getInTime() {
        return inTime;
    }

    public Time getOutTime() {
        return outTime;
    }

    public void finished() {
        state = STATE.FINISHED;
    }

    public void started() {
        state = STATE.STARTED;
    }

    public void close() {
        state = STATE.CLOSED;
    }

    public boolean isFinished() {
        return state == STATE.FINISHED;
    }

    public boolean isStarted() {
        return state == STATE.STARTED;
    }

    public boolean isClosed() {
        return state == STATE.CLOSED;
    }

    public static final class Builder {

        private long id;
        private Survey survey;
        private long shopperId;
        private Location location;
        private float payRate;
        private STATE state = STATE.PENDING;
        private Date visitDate;
        private Time inTime;
        private Time outTime;

        public Builder withAssignment(Assignment value) {
            id = value.getId();
            survey = value.getSurvey();
            shopperId = value.getShopperId();
            location = value.getLocation();
            payRate = value.getPayRate();
            state = value.getState();
            visitDate = value.getVisitDate();
            inTime = value.getInTime();
            outTime = value.getOutTime();
            return this;
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withState(STATE value) {
            state = value;
            return this;
        }

        public Builder withVisitDate(Date value) {
            visitDate = value;
            return this;
        }

        public Builder withInTime(Time value) {
            inTime = value;
            return this;
        }

        public Builder withOutTime(Time value) {
            outTime = value;
            return this;
        }

        public Assignment build() {
            return new Assignment(id, survey, shopperId, location, payRate, state, visitDate,
                    inTime, outTime);
        }

        public static Builder newAssignmentBuilder() {
            return new Builder();
        }
    }
}
