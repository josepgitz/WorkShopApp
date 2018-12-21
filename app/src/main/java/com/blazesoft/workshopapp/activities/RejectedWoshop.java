package com.blazesoft.workshopapp.activities;

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

public class RejectedWoshop extends AppCompatActivity {
    ListView rejectedWorkshop;
    SuspendedWorkshopAdpter suspendedWorkshopAdpter;
    ViewPager viewPager;
    RelativeLayout loadinworkshops;
    Button Search;
    TextView approval_statements;
    SearchView searchView;
    ArrayList<Workshop> workshops=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_woshop);
        loadinworkshops = findViewById(R.id.rejectedWorkshopLoading);
        rejectedWorkshop = findViewById(R.id.rejectedWorkshop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        approval_statements=findViewById(R.id.NotFpound);
        getRejectedWorkshop();
    }

    public void getRejectedWorkshop() {
        loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_REJECTED;
        headers = GLobalHeaders.getGlobalHeaders(RejectedWoshop.this);
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
                        suspendedWorkshopAdpter = new SuspendedWorkshopAdpter( (AppCompatActivity) RejectedWoshop.this,workshops);
                        suspendedWorkshopAdpter.notifyDataSetChanged();
                        rejectedWorkshop.setAdapter(suspendedWorkshopAdpter);

                    }else if (resultStatus.equals("201")){
                        String message = jsonObject.getString("message");
                        rejectedWorkshop.setVisibility(View.GONE);
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
