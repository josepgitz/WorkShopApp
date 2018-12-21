package com.blazesoft.workshopapp.datastore;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class LocalDatabase {
    private static String AUTH_TOKEN="TOKEN";
    public  static String NOT_LOGGED_IN="NO_TOKEN";
    public static  String FIREBASE_AUTH_TOKEN="FIREBASE_TOKEN";
    public static  String FIREBASE_NO_TOKEN="FIREBASE_TOKEN";
  public  static  String PREFERENCE_NAME="AUTH_PREFERENCE";
    public  static  String USER_ROLES="USER_ROLES_TOKEN";
    public  static String NO_USER_ROLE="NO_USER_PRIVILEGE";


    public  static  void   setToken(Context context, String token ){

        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AUTH_TOKEN,token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(AUTH_TOKEN,NOT_LOGGED_IN);
    }

    public static void ClearToken(Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();


    }
    public static void clearToken(AppCompatActivity appCompatActivity){

        SharedPreferences sharedPreferences=appCompatActivity.getPreferences(Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();


    }
    public static void saveFirebaseToken(Context context, String token){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(FIREBASE_AUTH_TOKEN,token);
        editor.apply();
    }
    public  static  String getFireBaseToken(Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(FIREBASE_AUTH_TOKEN,FIREBASE_AUTH_TOKEN);

    }

    public  static  void   setUserRoles(Context context, String token ){

        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_ROLES,token);
        editor.apply();
    }

    public static String getUserRoles(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ROLES,NO_USER_ROLE);
    }

    public static void ClearUserRoles(Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_ROLES,NO_USER_ROLE);
        editor.apply();


    }
}
