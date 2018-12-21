package com.blazesoft.workshopapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class UserRoles {
@Expose  List<Role>user_roles=new ArrayList<>();

    public List<Role> getUser_roles() {
        return user_roles;
    }

 public static UserRoles getUserRolesFrom(String roles){
            Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            UserRoles userRoles=gson.fromJson(roles,UserRoles.class);
            return userRoles;
    }
}
