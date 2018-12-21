package com.blazesoft.workshopapp.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.adapters.CosultantAdapter;
import com.blazesoft.workshopapp.adapters.WorkshopAdapter;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.models.Consultants;
import com.blazesoft.workshopapp.models.ConsultantsList;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PendingConsultants extends AppCompatActivity {
    ListView workshopList;
    TextView Notification,noconsultant;
    ViewPager viewPager;
    RelativeLayout loadinworkshops;
   // RelativeLayout loadinworkshops;
    SearchView searchView;
    List<ConsultantsList> consultantsLists=new ArrayList<>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_consultants);
        loadinworkshops = findViewById(R.id.approvedworkshoploading);
        noconsultant=findViewById(R.id.noconsultant);
        workshopList=findViewById(R.id.ConsultantstobeApproved);
        getPendinApprovalWorkshop();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
    public void getPendinApprovalWorkshop() {
         loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_APPROVED_CONSULTANTS;
        headers = GLobalHeaders.getGlobalHeaders(PendingConsultants.this);
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
                                Consultants consultants=new Consultants();
                            }
                            List<Consultants> consultantsList = ConsultantsList.getconsultantsFrom(jsonObject.toString());


                        CosultantAdapter cosultantAdapter = new CosultantAdapter( (AppCompatActivity) PendingConsultants.this,consultantsList);
                        workshopList.setAdapter(cosultantAdapter);
                        cosultantAdapter.notifyDataSetChanged();
                        // workshopAdapter.setViewPager(viewPager);
                        }

                    }
                    else if(resultStatus.equals("201")){
                        String message = jsonObject.getString("message");
                        loadinworkshops.setVisibility(View.GONE);
                        noconsultant.setVisibility(View.VISIBLE);
                        noconsultant.setText(message);

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


}
