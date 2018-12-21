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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.adapters.SuspendedWorkshopAdpter;
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

public class WorkshopAwaitingApproval extends AppCompatActivity {
    ListView approvedworkshopList;
    SuspendedWorkshopAdpter suspendedWorkshopAdpter;
    ViewPager viewPager;
    RelativeLayout loadinworkshops;
    Button Search;
    TextView approval_statements;
    SearchView searchView;
    List<Workshop> workshops=new ArrayList<>();
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_awaiting_approval);
        loadinworkshops = findViewById(R.id.awaiting_approvalListLoading);
        approvedworkshopList = findViewById(R.id.awaitingapprovalList);

        approval_statements=findViewById(R.id.awaiting_approval);
        viewPager = WorkshopAwaitingApproval.this.findViewById(R.id.pager);
        getSuspendedWorkshop();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        suspendedWorkshopAdpter = new SuspendedWorkshopAdpter( (AppCompatActivity) WorkshopAwaitingApproval.this,workshops);
        approvedworkshopList.setAdapter(suspendedWorkshopAdpter);
    }

    public void getSuspendedWorkshop() {
        loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.WORKSHOP_AWAITING_APPROVAL;
        headers = GLobalHeaders.getGlobalHeaders(WorkshopAwaitingApproval.this);
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

                        suspendedWorkshopAdpter.notifyDataSetChanged();
                        // workshopAdapter.setViewPager(viewPager);


                    }else if (resultStatus.equals("201")){
                        String message = jsonObject.getString("message");
                        approvedworkshopList.setVisibility(View.GONE);
                        loadinworkshops.setVisibility(View.GONE);
                        approval_statements.setVisibility(View.VISIBLE);
                        approval_statements.setText(message);


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
