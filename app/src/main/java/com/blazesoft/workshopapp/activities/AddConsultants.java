package com.blazesoft.workshopapp.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AddConsultants extends AppCompatActivity {
EditText name,email,phone, registration;
Button btnCreate, btnClear;
    ProgressDialog progressDialog;
    private TextView progressText;
    String longitude,latitude;
    private View progressContainer;
    private ImageView progressWarning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consultants);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        registration=findViewById(R.id.regno);
        btnClear=findViewById(R.id.Clear);
        btnCreate=findViewById(R.id.createConsultant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields()){
                    CreateWorkshopAttende();
                    clearFields();
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
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

    public void clearFields(){
        try{
            name.getText().clear();
            email.getText().clear();
            phone.getText().clear();
            registration.getText().clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean validateFields(){
        if(TextUtils.isEmpty(name.getText().toString())){
            name.setError("Name id Required");
            return  false;
        }
        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Name id Required");
            return  false;
        }
        if(TextUtils.isEmpty(phone.getText().toString())){
            phone.setError("Name id Required");
            return  false;
        } if(TextUtils.isEmpty(registration.getText().toString())){
            registration.setError("Name id Required");
            return  false;
        }
        return  true;
    }
    public void CreateWorkshopAttende(){


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name.getText().toString());
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("phone", phone.getText().toString());
            jsonObject.put("reg_no", registration.getText().toString());

            NetworkConnection.makeAPostRequest(URL.ADD_CONSULTANTS, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(AddConsultants.this), new OnReceivingResult() {
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

                            Toast.makeText(AddConsultants.this, "Consultant Added Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            //  new GeneralDialogBuilder().model("Ooop!", "Failed To Create Account"+resultData).build(context);
                            progressDialog.dismiss();
                        }
                    }else{

                        Toast.makeText(AddConsultants.this, "Cosultant already exist", Toast.LENGTH_SHORT).show();


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

            progressDialog = new ProgressDialog(AddConsultants.this);
            progressDialog.setTitle("Creating Account...");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }
}
