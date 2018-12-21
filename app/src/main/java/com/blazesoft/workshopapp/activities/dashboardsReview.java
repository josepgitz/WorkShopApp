package com.blazesoft.workshopapp.activities;

import android.app.DownloadManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.adapters.ConsultantsSpinnerAdapter;
import com.blazesoft.workshopapp.adapters.FacilitatorSpinnerAdapter;
import com.blazesoft.workshopapp.adapters.WorkshopAdapter;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.models.Consultants;
import com.blazesoft.workshopapp.models.Facilitator;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;

public class dashboardsReview extends AppCompatActivity {
    ListView workshopList;
    TextView Notification,approval_statements;
    ViewPager viewPager;
    Spinner facilitator;
    RelativeLayout loadinworkshops;
    SearchView searchView;
    List<Facilitator>spinnerData=new ArrayList<>();
    List<Workshop> workshops=new ArrayList<>();
    List<Facilitator> facilitators=new ArrayList<>();
    List<Consultants> consultants=new ArrayList<>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboards_review);
        loadinworkshops = findViewById(R.id.loadingBusiness);
        facilitator=findViewById(R.id.facilitat);
        Notification=findViewById(R.id.approval_statements);
        approval_statements=findViewById(R.id.approval_statements);
        //     getBusinesses("");
        getPendinApprovalWorkshop();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getFacilitator();
        getConsultants();
//        getActionBar().setHomeButtonEnabled(true);
        workshopList = findViewById(R.id.Approval_list);
        viewPager = dashboardsReview.this.findViewById(R.id.pager);
    }

    public void getPendinApprovalWorkshop() {
        loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_PENDING_WORKSHOP;
        headers = GLobalHeaders.getGlobalHeaders(dashboardsReview.this);
        Log.e("globalheaders",headers.toString());
        NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                Log.e("Logged", "Please check Your Network");
            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }
            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                try {
                    JSONObject jsonObject = new JSONObject(remoteResponse.getMessage().toString());
                    String resultStatus = jsonObject.getString("status");

                    if (resultStatus.equals("200")) {
                        loadinworkshops.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.length()>0){
                                Workshop workshop = Workshop.getworkFrom(jsonArray.getJSONObject(i).toString());
                                Log.d("some Infornation", workshop.getLocation());
                                workshops.add(workshop);
                            }
                        }
                        WorkshopAdapter workshopAdapter = new WorkshopAdapter( (AppCompatActivity) dashboardsReview.this,workshops,facilitators,consultants);
                        workshopList.setAdapter(workshopAdapter);
                        workshopAdapter.notifyDataSetChanged();
                    }else if(resultStatus.equals("201")){

                        approval_statements.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }
        });
    }

    public void getConsultants() {
        //     loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_APPROVED_CONSULTANTS;
        headers = GLobalHeaders.getGlobalHeaders(dashboardsReview.this);
        Log.e("globalheaders",headers.toString());
        NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                Log.e("Logged", "Please check Your Network");
            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {


                try {
                    JSONObject jsonObject = new JSONObject(remoteResponse.getMessage().toString());
                    String resultStatus = jsonObject.getString("status");

                    if (resultStatus.equals("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.length()>0){

                                Consultants consultant = Consultants.getConsultantFrom(jsonArray.getJSONObject(i).toString());
                                consultants.add(consultant);
                            }

                        }
                        ConsultantsSpinnerAdapter consultantsSpinnerAdapter = new ConsultantsSpinnerAdapter( dashboardsReview.this,consultants);
                        facilitator.setAdapter(consultantsSpinnerAdapter);
                        consultantsSpinnerAdapter.notifyDataSetChanged();
                        // workshopAdapter.setViewPager(viewPager);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }
        });
    }


    public void getFacilitator() {
        loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_FACILITATOR;
        headers = GLobalHeaders.getGlobalHeaders(dashboardsReview.this);
        Log.e("globalheaders",headers.toString());
        NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                Log.e("Logged", "Please check Your Network");
            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {


                try {
                    JSONObject jsonObject = new JSONObject(remoteResponse.getMessage().toString());
                    String resultStatus = jsonObject.getString("status");

                    if (resultStatus.equals("200")) {
                        loadinworkshops.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.length()>0){

                                Facilitator facilitator = Facilitator.getFacilitatorFrom(jsonArray.getJSONObject(i).toString());
                                facilitators.add(facilitator);
                            }

                        }
                        FacilitatorSpinnerAdapter facilitatorSpinnerAdapter = new FacilitatorSpinnerAdapter( dashboardsReview.this,facilitators);
                        facilitator.setAdapter(facilitatorSpinnerAdapter);
                        facilitatorSpinnerAdapter.notifyDataSetChanged();
                        // workshopAdapter.setViewPager(viewPager);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                finish();
                break;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_name) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
