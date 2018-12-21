package com.blazesoft.workshopapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blazesoft.workshopapp.MainActivity;
import com.blazesoft.workshopapp.datastore.LocalDatabase;

public class ActivityControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String userToken= LocalDatabase.getToken(getApplicationContext());
        String userRole= LocalDatabase.getUserRoles(ActivityControl.this);
        if(userToken.equals(LocalDatabase.NOT_LOGGED_IN)){
            Intent i =new Intent(ActivityControl.this, MainActivity.class);
            startActivity(i);
        }else if(!(userRole.equals(LocalDatabase.NO_USER_ROLE))){

            Intent i =new Intent(ActivityControl.this, ApprovalActivity.class);
            startActivity(i);

        }else{

            Intent i =new Intent(ActivityControl.this, MainActivity.class);
            startActivity(i);

        }
       finish();
        super.onCreate(savedInstanceState);
    }
}
