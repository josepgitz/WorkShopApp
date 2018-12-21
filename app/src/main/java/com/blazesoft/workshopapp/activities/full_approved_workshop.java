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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.adapters.RegistrationAdapter;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class full_approved_workshop extends AppCompatActivity {
    ListView approvedworkshopList;
    RegistrationAdapter registrationAdapter;
    ViewPager viewPager;
    RelativeLayout loadinworkshops;
    TextView approval_statements;
    SearchView searchView;
    List<Workshop> workshops=new ArrayList<>();
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_approved_workshop);
        loadinworkshops = findViewById(R.id.approvedworkshoploading);
        getFullyApprovalWorkshop();
        approvedworkshopList = findViewById(R.id.fully_Approval_list);
        approval_statements=findViewById(R.id.approval_statements);
        viewPager = full_approved_workshop.this.findViewById(R.id.pager);
        approvedworkshopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(full_approved_workshop.this,"TOAST FOR LIST",Toast.LENGTH_LONG).show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registrationAdapter = new RegistrationAdapter( (AppCompatActivity) full_approved_workshop.this,workshops);
        approvedworkshopList.setAdapter(registrationAdapter);
    }

    ///function to load fully approved workshop
    public void getFullyApprovalWorkshop() {
        loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_APPROVED_WORKSHOP;
        headers = GLobalHeaders.getGlobalHeaders(full_approved_workshop.this);
        Log.e("globalheaders",headers.toString());
        NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                // Log.e("Logged", e.getMessage());
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
                            Workshop workshop = Workshop.getworkFrom(jsonArray.getJSONObject(i).toString());
                            Log.d("some Infornation", workshop.getLocation());
                            workshops.add(workshop);
                        }

                        registrationAdapter.notifyDataSetChanged();
                        // workshopAdapter.setViewPager(viewPager);


                    }else if (resultStatus.equals("200")){
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

        return super.onOptionsItemSelected(item);
    }
}
