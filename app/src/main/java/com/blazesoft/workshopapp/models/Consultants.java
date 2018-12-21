package com.blazesoft.workshopapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Consultants {
    
    
    
@Expose String id;
@Expose String user_id;
@Expose String name;
@Expose String email;
@Expose String phone;
@Expose String registration_number;
@Expose String approval_status;
@Expose String created_at;
@Expose String updated_at;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
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

    @Expose String deleted_at;

    public static Consultants getConsultantFrom(String consultant){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Consultants consultants=gson.fromJson(consultant,Consultants.class);
        return consultants;


    }
}
