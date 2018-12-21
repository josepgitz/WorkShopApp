package com.blazesoft.workshopapp.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blazesoft.workshopapp.AnalysisModel.Analysis;
import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.adapters.CosultantAdapter;
import com.blazesoft.workshopapp.adapters.FacilitatorSpinnerAdapter;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.models.Consultants;
import com.blazesoft.workshopapp.models.ConsultantsList;
import com.blazesoft.workshopapp.models.Facilitator;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

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

public class DashBoard extends AppCompatActivity {
    List<SliceValue> pieData = new ArrayList<>();
    List<SliceValue> pieData2 = new ArrayList<>();
    List<PointValue> values = new ArrayList<>();
    List<Analysis> analyses=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        values.add(new PointValue(0, 2));
        values.add(new PointValue(1, 4));
        values.add(new PointValue(2, 3));
        values.add(new PointValue(3, 4));

        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>(1);
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        LineChartView chart = new LineChartView(DashBoard.this);
        chart.setLineChartData(data);
    //    layout.addView(chart);
        getFacilitator();
        PieChartView pieChartView = findViewById(R.id.chart);
        PieChartView pieChartView1 = findViewById(R.id.chart1);

getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        PieChartData pieChartData = new PieChartData(pieData);
        PieChartData pieChartData1 = new PieChartData(pieData2);
        pieChartData1.setHasLabels(true).setValueLabelTextSize(9);
        pieChartData.setHasLabels(true).setValueLabelTextSize(9);
        pieChartData1.setHasCenterCircle(true).setCenterText1("Completed Versus InProgress").setCenterText1FontSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("Workshop Progress").setCenterText1FontSize(10);
        pieChartView.setPieChartData(pieChartData);
        pieChartView1.setPieChartData(pieChartData1);


    }
    public void getFacilitator() {
        // loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_COUNT;
        headers = GLobalHeaders.getGlobalHeaders(DashBoard.this);
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
                        //  loadinworkshops.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.length()>0){

                                Analysis analysis = Analysis.getFacilitatorFrom(jsonArray.getJSONObject(i).toString());
                                analyses.add(analysis);
                            }

                        }  for (int i = 0; i <analyses.size() ; i++) {
                            pieData.add(new SliceValue(analyses.get(i).getCompleted(), Color.BLUE).setLabel("Completed"));
                            pieData.add(new SliceValue(analyses.get(i).getRejected(), Color.GRAY).setLabel("Rejected"));
                            pieData.add(new SliceValue(analyses.get(i).getSuspended(), Color.RED).setLabel("Suspended"));
                            pieData.add(new SliceValue(analyses.get(i).getApproved(), Color.MAGENTA).setLabel("Approved"));
                            pieData2.add(new SliceValue(analyses.get(i).getApproved()+analyses.get(i).getSuspended()+analyses.get(i).getRejected(),
                                    Color.BLUE).setLabel("In Progess"));
                            pieData2.add(new SliceValue(analyses.get(i).getCompleted(), Color.GREEN).setLabel("Completed"));
                        }
                       // FacilitatorSpinnerAdapter facilitatorSpinnerAdapter = new FacilitatorSpinnerAdapter( DashBoard.this,facilitators);
                     //   facilitator.setAdapter(facilitatorSpinnerAdapter);
                      //  facilitatorSpinnerAdapter.notifyDataSetChanged();
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
