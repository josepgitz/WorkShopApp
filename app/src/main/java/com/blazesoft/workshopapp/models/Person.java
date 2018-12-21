package com.blazesoft.workshopapp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.blazesoft.workshopapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;


public class Person {

    @Expose  private int id;
    @Expose  private String name;
    @Expose  private String mobile;
    @Expose  private String email;
    @Expose  private String country_dial;
    @Expose  private String country_code;
    @Expose  private String status;
    @Expose  private String verified;
    @Expose  private String  id_number;
    @Expose  private String  created_through;
    @Expose  private String  created_at;
    @Expose  private String  updated_at;
    @Expose  private String  deleted_at;
    @Expose   private String  profile_photo;
private  byte [] profileByte;
private int defaultImage= R.drawable.ic_launcher_foreground;
    public Bitmap getProfileByte() {
        return getBitmapFromFromBytes(profileByte);
    }

    public void setProfileByte(byte [] profileByte) {
        this.profileByte = profileByte;
    }
    private Bitmap getBitmapFromFromBytes(byte [] bitmap){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        if(bitmap==null){
            return null;
        }
        return BitmapFactory.decodeByteArray(bitmap,0,bitmap.length,options);
    }
    private void setProfile(Drawable drawable){



    }
    public int getDefault(){

        return  defaultImage;

    }
    public String  getPerson(){
      Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      return gson.toJson(this);
    }
    public static  Person  getPerson(String personInfo){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(personInfo,Person.class);
    }
    public String getCountry_dial() {
        return country_dial;
    }

    public void setCountry_dial(String country_dial) {
        this.country_dial = country_dial;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getCreated_through() {
        return created_through;
    }

    public void setCreated_through(String created_through) {
        this.created_through = created_through;
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

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    //--------------------------------------------------------------------
    public Person(){

        }

    //setters
    //--------------------------------------------------------------------
    public void setId(int id){
        this.id = id;
    }

    //--------------------------------------------------------------------
    public void setName(String name){
        this.name = name;
    }

    //--------------------------------------------------------------------
    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    //--------------------------------------------------------------------
    public void setEmail(String email){
        this.email = email;
    }

    //--------------------------------------------------------------------
    public void setCountryCode(String country_code){
        this.country_code = country_code;
    }

    //--------------------------------------------------------------------
    public void setCountryDial(String country_dial){
        this.country_dial = country_dial;
    }

    //--------------------------------------------------------------------


    //getters
    //---------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------
    public String getName(){
        return this.name;
    }

    //---------------------------------------------------------------------
    public String getMobile(){
        return this.mobile;
    }
    //---------------------------------------------------------------------
    public String getEmail(){
        return this.email;
    }

    //---------------------------------------------------------------------
    public String getCountryCode(){
        return this.country_code;
    }

    //---------------------------------------------------------------------
    public String getCountryDial(){
        return this.country_dial;
    }

    //---------------------------------------------------------------------

}
