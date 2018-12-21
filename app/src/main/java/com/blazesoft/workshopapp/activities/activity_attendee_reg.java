package com.blazesoft.workshopapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.adapters.WorkshopAdapter;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.dialogs.GeneralDialogBuilder;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class activity_attendee_reg extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
EditText fname,lname,sname,phone,id_number,password,dependent,county,subcounty,town;
TextView dob;
View section1,section2;
    Spinner maritalstatus,gender;
    String maritalDefault="--Marital Status--";
    String single="Single";
    String married="Married";
    String genders="--Gender--";
    String male="Male";
    String female="Female";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private static final String TAG = "activity_attendee_reg";
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private LocationManager locationManager;
Button btncreate, btncancel;
    Calendar myCalendar;
    AppCompatActivity context;

    String dateFormat = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.UK);
    Button btnCreate,btnCancel,btnNext,btnBack;
    DatePickerDialog datePickerDialog;
    ProgressDialog progressDialog;
    private TextView progressText;
    String longitude,latitude;
    private View progressContainer;
    private ImageView progressWarning;
    private List<Workshop> listData;
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
        setContentView(R.layout.activity_attendee_reg);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        btnNext=findViewById(R.id.next);
        password=findViewById(R.id.password);
        section2=findViewById(R.id.firstActivity);
        section1=findViewById(R.id.attendee2);
        btnCancel=findViewById(R.id.back);
        town=findViewById(R.id.town);
        sname=findViewById(R.id.sname);
        phone=findViewById(R.id.phone);
        id_number=findViewById(R.id.IDNo);
        gender=(Spinner)findViewById(R.id.gender);
        dob=findViewById(R.id.dob);
        maritalstatus=(Spinner)findViewById(R.id.MaritalStatus);
        btnCreate=findViewById(R.id.create);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //    checkPermission();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields()) {
                    CreateWorkshopAttende();
                }
            }
        });
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        dob.setText(dateString);
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
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                // TODO Auto-generated method stub
                int Day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int Month = myCalendar.get(Calendar.MONTH);
                int Year = myCalendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(activity_attendee_reg.this, date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog = new DatePickerDialog(activity_attendee_reg.this, new DatePickerDialog.OnDateSetListener() {
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
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                section1.setVisibility(View.VISIBLE);
                section2.setVisibility(View.GONE);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                section1.setVisibility(View.GONE);
                section2.setVisibility(View.VISIBLE);
            }
        });
        loadMaritalStatus();
        loadGender();
        checkLocation();
    }



    //function to load Gender information
    public  void loadGender(){

        List<String> spinnerArray = new ArrayList<>();

        spinnerArray.add(genders);
        spinnerArray.add(male);
        spinnerArray.add(female);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity_attendee_reg.this,
                android.R.layout.simple_spinner_item,
                spinnerArray
        ){
            @Nullable
            @Override
            public String getItem(int position) {
                switch (position){
                    case 1:
                        return "1";
                    case 2:
                        return "2";
                    case 3:
                        return "3";

                }
                return super.getItem(position);
            }
        };
        gender.setAdapter(adapter);
    }
    //function to load marital status information
    public  void loadMaritalStatus(){

        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add(maritalDefault);
        spinnerArray.add(single);
        spinnerArray.add(married);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity_attendee_reg.this,
                android.R.layout.simple_spinner_item,
                spinnerArray
        ){

            @Nullable
            @Override
            public String getItem(int position) {

                switch (position){
                    case 1:
                        return "1";
                    case 2:
                        return "2";
                    case 3:
                        return "3";

                }
                return super.getItem(position);
            }
        };
        maritalstatus.setAdapter(adapter);
    }
    private void updateDate() {
        String dateString = sdf.format(myCalendar.getTimeInMillis());
        dob.setText(dateString);
    }
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
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

    protected void startLocationUpdates() {
        // Create the location request

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL
                );

        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    public  boolean validateFields(){

        if(TextUtils.isEmpty(fname.getText().toString())){
            fname.setError("first name is Required");
            return  false;
        }
        if(TextUtils.isEmpty(sname.getText().toString())){
            sname.setError("Second Name is Required");
            return  false;
        }
        if(TextUtils.isEmpty(lname.getText().toString())){
            lname.setError("Last Name is Required");
            return  false;
        }
        if(TextUtils.isEmpty(phone.getText().toString())){
            phone.setError("Phone is Required");
            return  false;
        }
        if(TextUtils.isEmpty(id_number.getText().toString())){
            id_number.setError("ID number is Required");
            return  false;
        }
        if(TextUtils.isEmpty(gender.getSelectedItem().toString())){
          Toast.makeText(activity_attendee_reg.this,"Gender is Required",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(TextUtils.isEmpty(dob.getText().toString())){
            dob.setError("Date of birth is Required");
            return  false;
        }if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("password is Required");
            return  false;
        }if(TextUtils.isEmpty(maritalstatus.getSelectedItem().toString())){
            Toast.makeText(activity_attendee_reg.this,"",Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }
    public void CreateWorkshopAttende(){

      //  ShowProgressBar(true,false,"Creating");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("fname", fname.getText().toString());
            jsonObject.put("sname", sname.getText().toString());
            jsonObject.put("lname", lname.getText().toString());
            jsonObject.put("phone", phone.getText().toString());
            jsonObject.put("id_number", id_number.getText().toString());
            jsonObject.put("gender", gender.getSelectedItem().toString());
            jsonObject.put("dob", dob.getText().toString());
            jsonObject.put("password", password.getText().toString());
            jsonObject.put("marital_status", maritalstatus.getSelectedItem().toString());


            NetworkConnection.makeAPostRequest(URL.CREATE_ACCOUNT, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(activity_attendee_reg.this), new OnReceivingResult() {
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
                        // progressText.setText("Response error");
                    }
                    if (resultStatus.equals("200") ) {
                        try {
                            ShowProgressBar(false,false,"Creating");
                            resultData = response.getString("message").toString();

                           Toast.makeText(activity_attendee_reg.this, "Account Created SuccessFully", Toast.LENGTH_SHORT).show();
                     progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            new GeneralDialogBuilder().model("Ooop!", "Failed To Create Account"+resultData).build(context);
                            progressDialog.dismiss();
                        }
                    }
                  //  progressDialog.dismiss();
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
        } catch (JSONException e) {

        }

    }
    private void ShowProgressBar(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(activity_attendee_reg.this);
            progressDialog.setTitle("Creating Account...");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }



    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public  void checkPermission(){


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
