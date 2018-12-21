package com.blazesoft.workshopapp.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.blazesoft.workshopapp.fragments.HomeFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private  String [] PAGES_NAME;
    AppCompatActivity appCompatActivity;
    ViewPager viewPager;
    boolean loggedin=false;
    public PageAdapter(ViewPager viewPager, AppCompatActivity appCompatActivity, String[] PAGES_NAME) {
        super(appCompatActivity.getSupportFragmentManager());
        this.appCompatActivity=appCompatActivity;
        this.PAGES_NAME=PAGES_NAME;
        this.viewPager=viewPager;

    }

    public String[] getPAGES_NAME() {
        return PAGES_NAME;
    }

    public void setPAGES_NAME(String[] PAGES_NAME) {
        this.PAGES_NAME = PAGES_NAME;
    }

    public boolean isLoggedIn() {
        return loggedin;
    }

    public void setLoggedIn(boolean loggedin) {
        this.loggedin = loggedin;
    }

    @Override
    public Fragment getItem(int position) {

    HomeFragment homeFragment= new HomeFragment();
    return  homeFragment;
    }

    @Override
    public int getCount() {
        return PAGES_NAME.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return PAGES_NAME[position];
    }
}
