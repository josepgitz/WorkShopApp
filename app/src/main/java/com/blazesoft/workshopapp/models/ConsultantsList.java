package com.blazesoft.workshopapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ConsultantsList {
    @Expose
    List<Consultants> results=new ArrayList<>();

    public List<Consultants> getconsultants() {
        return results;
    }

    public static List<Consultants> getconsultantsFrom(String consultants){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ConsultantsList consultants1=gson.fromJson(consultants,ConsultantsList.class);
        return consultants1.getconsultants();
    }


}