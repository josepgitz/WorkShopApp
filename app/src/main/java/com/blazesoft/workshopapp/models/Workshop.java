package com.blazesoft.workshopapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Workshop {

@Expose String    id;
@Expose String   user_id;
@Expose String   code;
@Expose String   name;
@Expose String   award_code;
@Expose String   account_code;
@Expose String   donor_line;
@Expose String   location_code;
@Expose String   country_code;
@Expose String   start_date;
@Expose String   location;
@Expose String   facilitator_id;
@Expose String   cost;
@Expose String   status;
@Expose String   created_at;
@Expose String   updated_at;
@Expose String   deleted_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAward_code() {
        return award_code;
    }

    public void setAward_code(String award_code) {
        this.award_code = award_code;
    }

    public String getAccount_code() {
        return account_code;
    }

    public void setAccount_code(String account_code) {
        this.account_code = account_code;
    }

    public String getDonor_line() {
        return donor_line;
    }

    public void setDonor_line(String donor_line) {
        this.donor_line = donor_line;
    }

    public String getLocation_code() {
        return location_code;
    }

    public void setLocation_code(String location_code) {
        this.location_code = location_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFacilitator_id() {
        return facilitator_id;
    }

    public void setFacilitator_id(String facilitator_id) {
        this.facilitator_id = facilitator_id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getApproval_status() {
        return status;
    }

    public void setApproval_status(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public static Workshop getworkFrom(String workshop){
  Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  Workshop workshop1=gson.fromJson(workshop,Workshop.class);
 return workshop1;


    }

}
