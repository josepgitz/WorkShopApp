package com.blazesoft.workshopapp.AnalysisModel;

import com.blazesoft.workshopapp.models.Facilitator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Analysis {


@Expose Float pending;
@Expose Float approved;
@Expose Float completed;
@Expose Float rejected;
@Expose Float sentBack;
@Expose Float suspended;
@Expose Float waiting;

    public Float getPending() {
        return pending;
    }

    public void setPending(Float pending) {
        this.pending = pending;
    }

    public Float getApproved() {
        return approved;
    }

    public void setApproved(Float approved) {
        this.approved = approved;
    }

    public Float getCompleted() {
        return completed;
    }

    public void setCompleted(Float completed) {
        this.completed = completed;
    }

    public Float getRejected() {
        return rejected;
    }

    public void setRejected(Float rejected) {
        this.rejected = rejected;
    }

    public Float getSentBack() {
        return sentBack;
    }

    public void setSentBack(Float sentBack) {
        this.sentBack = sentBack;
    }

    public Float getSuspended() {
        return suspended;
    }

    public void setSuspended(Float suspended) {
        this.suspended = suspended;
    }

    public Float getWaiting() {
        return waiting;
    }

    public void setWaiting(Float waiting) {
        this.waiting = waiting;
    }

    public static Analysis getFacilitatorFrom(String analysis){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Analysis analysis1=gson.fromJson(analysis,Analysis.class);
        return analysis1;


    }
}
