package com.blazesoft.workshopapp.adapters;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.dialogs.GeneralDialogBuilder;
import com.blazesoft.workshopapp.models.Consultants;
import com.blazesoft.workshopapp.models.ConsultantsList;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class CosultantAdapter extends BaseAdapter {


    private AppCompatActivity context;
    private LayoutInflater layoutInflater;
    ProgressDialog progressDialog;
    private TextView progressText;
    private View progressContainer;
    private ImageView progressWarning;
    private List<Consultants> listData;
    public CosultantAdapter(AppCompatActivity context, List<Consultants> Listdata){
        this.context = context;
        if (this.context!=null){
            layoutInflater =LayoutInflater.from(this.context );
        }
        this.listData = Listdata;

    }
    @Override
    public int getCount() {
        listData.size();
        return (listData.size());
    }

    @Override
    public Consultants getItem(int position) {

        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CosultantAdapter.ViewHolder rowHolder;

        convertView = layoutInflater.inflate(R.layout.approved_consultant_row, parent, false);
        rowHolder = new CosultantAdapter.ViewHolder();
        final Consultants consultants =listData.get(position);


        rowHolder.name=convertView.findViewById(R.id.name);
        rowHolder.email=convertView.findViewById(R.id.email);
        rowHolder.phone=convertView.findViewById(R.id.phone);
        rowHolder.reg_no=convertView.findViewById(R.id.reg_no);
        rowHolder.btnApprove=convertView.findViewById(R.id.approved);
        rowHolder.btnReject=convertView.findViewById(R.id.decline);
        rowHolder.btnDelete=convertView.findViewById(R.id.delete);
        rowHolder.name.setText(Html.fromHtml("<b>Name<b>:  ")+consultants.getName());
        rowHolder.email.setText(Html.fromHtml("<b>Email<b>:  ")+consultants.getEmail());
        rowHolder.phone.setText(Html.fromHtml("<b>Phone<b>:  ")+consultants.getPhone());
        rowHolder.reg_no.setText(Html.fromHtml("<b>RegNo<b>:  ")+consultants.getRegistration_number());

            rowHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteConsultant(consultants);
                }
            });
                    rowHolder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveConsultant(consultants);
            }
        });

        rowHolder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineApproval(consultants);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView email;
        TextView phone;
        TextView reg_no;
        Button btnApprove,btnReject, btnDelete;

    }

    public void deleteConsultant(Consultants consultants){

        ShowProgressBar1(true,false,"Declining");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("record_id", consultants.getId());
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.DELETE_CONSULTANT, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            progressDialog.dismiss();
                            resultData = response.getString("message").toString();
                            resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Declined", resultData ).build(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new GeneralDialogBuilder().model("Oops!", "Failed to Decline. \nPlease  try again").build(context);
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
    public void declineApproval(Consultants consultants){

        ShowProgressBar1(true,false,"Declining");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("record_id", consultants.getId());
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.SUSPEND_CONSULTANT, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            progressDialog.dismiss();
                            ShowProgressBar1(false,false,"Declining");
                            resultData = response.getString("message").toString();
                          //  resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Declined", resultData ).build(context);
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            new GeneralDialogBuilder().model("Oops!", "Failed to Decline. \nPlease  try again").build(context);
                        }
                    } else if(resultStatus.equals("201")){
                        progressDialog.dismiss();
                        ShowProgressBar1(false,false,"Declining");

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
    public void approveConsultant(Consultants consultants){

        ShowProgressBar1(true,false,"Declining");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("record_id", consultants.getId());
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.APPROVE_CONSULTANT, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            progressDialog.dismiss();
                            resultData = response.getString("message").toString();
                            resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Declined", resultData ).build(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new GeneralDialogBuilder().model("Oops!", "Failed to Decline. \nPlease  try again").build(context);
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
    private void ShowProgressBar(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Approving...");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }
    private void ShowProgressBar1(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Declining...");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }
}
