package com.blazesoft.workshopapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blazesoft.workshopapp.AnalysisModel.Analysis;
import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.activities.ActivityMaker;
import com.blazesoft.workshopapp.activities.CompletedWorkshops;
import com.blazesoft.workshopapp.activities.DashBoard;
import com.blazesoft.workshopapp.activities.RejectedWoshop;
import com.blazesoft.workshopapp.activities.SuspendedWorkshop;
import com.blazesoft.workshopapp.activities.dashboardsReview;
import com.blazesoft.workshopapp.activities.full_approved_workshop;
import com.blazesoft.workshopapp.adapters.AllWorkshopAdpter;
import com.blazesoft.workshopapp.adapters.WorkshopAdapter;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.datastore.LocalDatabase;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;


public class HomeFragment extends Fragment {
    ListView workshopList;
    CardView Createworkshop,Review,workshopinprogress,Rejectedworkshop,suspendedWorkshop,Completed;
    TextView Notification;
    ViewPager viewPager;
    RelativeLayout loadinworkshops;
    SearchView searchView;
    List<Workshop>workshops=new ArrayList<>();
    FloatingActionButton fab;
    List<SliceValue> pieData = new ArrayList<>();
    List<Analysis> analyses=new ArrayList<>();
    List<SliceValue> pieData2 = new ArrayList<>();
    List<PointValue> values = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dashboard, container, false);
        loadinworkshops = view.findViewById(R.id.loadingBusiness);
        Createworkshop=view.findViewById(R.id.Createworkshopsa);
        Review =view.findViewById(R.id.Review);
        workshopinprogress=view.findViewById(R.id.workshopinprogress);
        Rejectedworkshop=view.findViewById(R.id.Rejectedworkshop);
        suspendedWorkshop=view.findViewById(R.id.suspendedWorkshop);
        Completed=view.findViewById(R.id.Completed);
        Notification=view.findViewById(R.id.approval_statements);
        workshopList = view.findViewById(R.id.Approval_list);
        getFacilitator();

        Createworkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),ActivityMaker.class);
                startActivity(i);
            }
        });
        Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),dashboardsReview.class);
                startActivity(i);
            }
        });
        workshopinprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),full_approved_workshop.class);
                startActivity(i);
            }
        });
        Rejectedworkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),RejectedWoshop.class);
                startActivity(i);
            }
        });

        suspendedWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),SuspendedWorkshop.class);
                startActivity(i);
            }
        });

        Completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),CompletedWorkshops.class);
                startActivity(i);
            }
        });
        return view;
    }
    public void getFacilitator() {
        JSONObject headers = new JSONObject();
        String url = URL.GET_COUNT;
        headers = GLobalHeaders.getGlobalHeaders(getActivity());
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
                                Analysis analysis = Analysis.getFacilitatorFrom(jsonArray.getJSONObject(i).toString());
                                analyses.add(analysis);
                            }
                        }  for (int i = 0; i <analyses.size(); i++) {
                            pieData.add(new SliceValue(analyses.get(i).getCompleted(), Color.BLUE).setLabel("Completed"));
                            pieData.add(new SliceValue(analyses.get(i).getRejected(), Color.GRAY).setLabel("Rejected"));
                            pieData.add(new SliceValue(analyses.get(i).getSuspended(), Color.RED).setLabel("Suspended"));
                            pieData.add(new SliceValue(analyses.get(i).getApproved(), Color.MAGENTA).setLabel("Approved"));
                            pieData2.add(new SliceValue(analyses.get(i).getApproved()+analyses.get(i).getSuspended()+analyses.get(i).getRejected(), Color.BLUE).setLabel("In Progess"));
                            pieData2.add(new SliceValue(analyses.get(i).getCompleted(), Color.GREEN).setLabel("Completed"));
                        }
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
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
}