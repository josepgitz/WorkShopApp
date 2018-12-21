package com.blazesoft.workshopapp.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.adapters.WorkshopAdapter;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.dialogs.GeneralDialogBuilder;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityMaker extends AppCompatActivity {
    Spinner facilitator;
    EditText workshopname,location,attendeeNo,budget,projectCode,awardCode,accountCode,donorExpenseline,locationCode,countryCode;
    Calendar myCalendar;
    AppCompatActivity context;
    String dateFormat = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.UK);
    Button btnCreate,btnCancel,btnNext,btnBack;
    DatePickerDialog datePickerDialog;
    ProgressDialog progressDialog;
    private TextView progressText;
    private View progressContainer;
    private ImageView progressWarning;
    private List<Workshop> listData;
    CountryCodePicker dial_code;

    DatePickerDialog.OnDateSetListener date1;
    ProgressBar progress_bar;
    JSONObject jsonObject= new JSONObject();
    TextView date;
    ViewPager viewPager;
    ArrayList<String> CountryName;
    List<Workshop> workshops=new ArrayList<>();
    WorkshopAdapter workshopAdapter;
    //  RelativeLayout loadingBusiness;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker);
        workshopname=findViewById(R.id.workshopname);
        date=findViewById(R.id.date);
        location=findViewById(R.id.location);
     //   dial_code=findViewById(R.id.dial_code);

getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        awardCode=findViewById(R.id.awardcode);
        budget=findViewById(R.id.budget);

        progress_bar=findViewById(R.id.progress_bar);
        btnNext=findViewById(R.id.next);
        btnBack=findViewById(R.id.Backcancel);
        accountCode=findViewById(R.id.accountCode);
        donorExpenseline=findViewById(R.id.donorExpenseLine);
        locationCode=findViewById(R.id.locationcode);
        countryCode=findViewById(R.id.countryCode);
        btnCreate=findViewById(R.id.create);
        btnCancel=findViewById(R.id.Createcancel);
        final View secondLayout = findViewById(R.id.second_activity);
        final View firstLayout = findViewById(R.id.firstActivity);

//        facilitator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String country=   facilitator.getItemAtPosition(facilitator.getSelectedItemPosition()).toString();
//                Toast.makeText(context
//                        ,country,Toast.LENGTH_LONG).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondLayout.setVisibility(View.VISIBLE);
                firstLayout.setVisibility(View.GONE);

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondLayout.setVisibility(View.GONE);
                firstLayout.setVisibility(View.VISIBLE);
            }
        });
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        date.setText(dateString);
        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, Calendar.YEAR);
                myCalendar.set(Calendar.MONTH, Calendar.MONTH);
                myCalendar.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
                updateDate();
            }

        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                                // TODO Auto-generated method stub
                int Day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int Month = myCalendar.get(Calendar.MONTH);
                int Year = myCalendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(ActivityMaker.this, date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog = new DatePickerDialog(ActivityMaker.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDate();
                    }
                }, Day, Month, Year);
                updateDate();
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields()){
                    CreateWorkshop();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clearFields();
            }
        });

    }

    private void updateDate() {
        String dateString = sdf.format(myCalendar.getTimeInMillis());
        date.setText(dateString);
    }
////Function to create a new workshop


    //function to check that the field is not null

    public boolean validateFields(){

        String msg;
        if(TextUtils.isEmpty(workshopname.getText().toString())){
            workshopname.setError("Workshop Name Required");


            return false;

        } if(TextUtils.isEmpty(awardCode.getText().toString())){
            Toast.makeText(ActivityMaker.this,"awardCode is required",Toast.LENGTH_LONG).show();

            return false;
        }
        if(TextUtils.isEmpty(accountCode.getText().toString())){
            Toast.makeText(ActivityMaker.this,"accountCode is required",Toast.LENGTH_LONG).show();

            return false;
        }
        if(TextUtils.isEmpty(donorExpenseline.getText().toString())){
            Toast.makeText(ActivityMaker.this,"donorExpenseline is required",Toast.LENGTH_LONG).show();

            return false;
        }if(TextUtils.isEmpty(locationCode.getText().toString())){
            Toast.makeText(ActivityMaker.this,"locationCode is required",Toast.LENGTH_LONG).show();

            return false;
        }
        if(TextUtils.isEmpty(countryCode.getText().toString())){
            //Toast.makeText(ActivityMaker.this,"countryCode is required",Toast.LENGTH_LONG).show();
            countryCode.setError("Workshop Name Required");
            return false;
        }
        if(TextUtils.isEmpty(date.getText().toString())){
            Toast.makeText(ActivityMaker.this,"date is required",Toast.LENGTH_LONG).show();

            return false;
        }
        if(TextUtils.isEmpty(location.getText().toString())){
            Toast.makeText(ActivityMaker.this,"location is required",Toast.LENGTH_LONG).show();

            return false;
        }
        if(TextUtils.isEmpty(budget.getText().toString())){
            budget.setError("Workshop Name Required");

            return false;
        }
        return true;
    }
    public void CreateWorkshop(){

        ShowProgressBar(true,false,"Creating..");

        JSONObject jsonObject = new JSONObject();

        try {
            if (validateFields()) {
                jsonObject.put("name", workshopname.getText().toString());
                jsonObject.put("award_code", awardCode.getText().toString());
                jsonObject.put("account_code", accountCode.getText().toString());
                jsonObject.put("donor_line", donorExpenseline.getText().toString());
                jsonObject.put("location_code", locationCode.getText().toString());
                jsonObject.put("country_code", countryCode.getText().toString());
                jsonObject.put("start_date", date.getText().toString());
                jsonObject.put("location", location.getText().toString());
                jsonObject.put("cost", budget.getText().toString());
                Log.d("Visit1222", jsonObject.toString());

                NetworkConnection.makeAPostRequest(URL.ADD_WORKSHOP, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(ActivityMaker.this), new OnReceivingResult() {
                    @Override
                    public void onErrorResult(IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                        NetworkConnection.remoteResponseLogger(remoteResponse);

                    }

                    @Override
                    public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                        NetworkConnection.remoteResponseLogger(remoteResponse);

                        String resultStatus = "";
                        String resultData = "";
                        String ticketNumber;
                        JSONObject response = new JSONObject();
                        try {
                            response = new JSONObject(remoteResponse.getMessage());
                            resultStatus = response.getString("status").toString();
                            Log.d("Results", resultStatus);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (resultStatus.equals("200")) {
                            try {
                                //
                                resultData = response.getString("message").toString();
                                progressDialog.dismiss();
                                //        resultData = response.get("reason").toString();
                                Toast.makeText(ActivityMaker.this, "Registered Sucessfully", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(ActivityMaker.this, "Ooops!Registered Failed", Toast.LENGTH_LONG).show();

                                //
                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                        NetworkConnection.remoteResponseLogger(remoteResponse);

                    }

                    @Override
                    public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                        NetworkConnection.remoteResponseLogger(remoteResponse);

                    }

                    @Override
                    public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                        NetworkConnection.remoteResponseLogger(remoteResponse);

                    }
                });
            }
            } catch(JSONException e){

            }

    }
    private void ShowProgressBar(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(ActivityMaker.this);
            progressDialog.setTitle("Creating..");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
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
