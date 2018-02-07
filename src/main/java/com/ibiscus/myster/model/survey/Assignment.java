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

    public static enum STATE {
        PENDING, STARTED, FINISHED, SENT
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

    public Assignment(long id, Survey survey, long shopperId, Location location, float payRate, Date visitDate,
                      Time inTime, Time outTime) {
        this.id = id;
        this.survey = survey;
        this.shopperId = shopperId;
        this.location = location;
        this.payRate = payRate;
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

    public void sent() {
        state = STATE.SENT;
    }

    public boolean isFinished() {
        return state == STATE.FINISHED;
    }

    public boolean isStarted() {
        return state == STATE.STARTED;
    }

    public boolean isSent() {
        return state == STATE.SENT;
    }
}
