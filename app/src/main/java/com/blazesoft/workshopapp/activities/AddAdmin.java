package com.blazesoft.workshopapp.activities;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;

public class AddAdmin extends AppCompatActivity {
EditText fname,lname,phone, email,id_number;
Spinner role;
Button btnCreate, btnClear;
    ProgressDialog progressDialog;
    private TextView progressText;
    private View progressContainer;
    private ImageView progressWarning;
    String Roles="--UserRoles--";
    String superAdmin="super admin";
    String adminmaker="admin maker";
    String adminchecker="admin checker";
    String facilitator="facilitator";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        id_number=findViewById(R.id.idno);
        role=findViewById(R.id.roles);
        phone=findViewById(R.id.phone);
        btnCreate=findViewById(R.id.create);
        btnClear=findViewById(R.id.Clear);
        loadRoles();
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFields();
            }
        });
getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddAdmin.this, "No end point", Toast.LENGTH_SHORT).show();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()) {
                    CreateAdmin();
                    ClearFields();
                }
            }
        });
    }

    public void ClearFields(){

        fname.getText().clear();
        lname.getText().clear();;
        email.getText().clear();
        phone.getText().clear();

    }
    public  void loadRoles(){

        List<String> spinnerArray = new ArrayList<>();

        spinnerArray.add(superAdmin);
        spinnerArray.add(adminmaker);
        spinnerArray.add(adminchecker);
        spinnerArray.add(facilitator);



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AddAdmin.this,
                android.R.layout.simple_spinner_item,
                spinnerArray
      );
        role.setAdapter(adapter);
    }


    public boolean validateFields(){
        if(TextUtils.isEmpty(id_number.getText().toString())){
            id_number.setError("ID number Is Required");
            return false;
        }
        if(TextUtils.isEmpty(fname.getText().toString())){
            fname.setError("fname Is Required");
            return false;
        }
        if(TextUtils.isEmpty(lname.getText().toString())){
            lname.setError("lname Is Required");
            return false;
        }
        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("email Is Required");
            return false;
        }
        if(TextUtils.isEmpty(phone.getText().toString())){
            phone.setError("phone Is Required");
            return false;
        }
        return  true;
    }
    public void CreateAdmin(){

        ShowProgressBar(true,false,"Adding Admin..");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id_number", id_number.getText());
            jsonObject.put("fname", fname.getText().toString());
            jsonObject.put("lname", lname.getText().toString());
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("phone", phone.getText().toString());
            jsonObject.put("roles[]", role.getSelectedItem().toString());
Log.d("rolers",jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.ADD_ADMIN, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(AddAdmin.this), new OnReceivingResult() {
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
                            ShowProgressBar(false,false,"Adding Admin..");
                            resultData = response.getString("message").toString();
                            progressDialog.dismiss();
                            Toast.makeText(AddAdmin.this,"Success \n"+resultData,Toast.LENGTH_LONG).show();
                            //        resultData = response.get("reason").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddAdmin.this,"Ooops \nAdding Failed",Toast.LENGTH_LONG).show();

                            //   new GeneralDialogBuilder().model("Cancelled", "cancel").build(context);
                        }
                    }
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

    private void ShowProgressBar(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(AddAdmin.this);
            progressDialog.setTitle("Adding Admin..");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }
}
