package com.blazesoft.workshopapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Facilitator {
    
@Expose String      id;
@Expose String      country_id;
@Expose String      fname;
@Expose String      lname;
@Expose String      phone;
@Expose String      id_number;
@Expose String      email;
@Expose String      email_verified_at;
@Expose String      approval_status;
@Expose String      created_at;
@Expose String      updated_at;
@Expose String      deleted_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(String approval_status) {
        this.approval_status = approval_status;
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
    public static Facilitator getFacilitatorFrom(String facilitator){
        Gson gson= new  GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Facilitator facilitator1=gson.fromJson(facilitator,Facilitator.class);
        return facilitator1;


    }
}
