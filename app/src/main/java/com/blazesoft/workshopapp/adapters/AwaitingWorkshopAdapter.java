package com.blazesoft.workshopapp.adapters;

import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
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
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class AwaitingWorkshopAdapter extends BaseAdapter {
    private AppCompatActivity context;
    private LayoutInflater layoutInflater;
    ProgressDialog progressDialog;
    private TextView progressText;
    private View progressContainer;
    private ImageView progressWarning;
    private List<Workshop> listData;

    private ViewPager viewPager;
    public AwaitingWorkshopAdapter(AppCompatActivity context, List<Workshop> Listdata){
        this.context = context;
        if (this.context!=null){
            layoutInflater =LayoutInflater.from(this.context );
        }
        this.listData = Listdata;

    }
    @Override
    public int getCount() {
        return (listData.size());
    }

    @Override
    public Workshop getItem(int position) {

        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AwaitingWorkshopAdapter.ViewHolder rowHolder;

        convertView = layoutInflater.inflate(R.layout.workshop_awaiting, parent, false);
        rowHolder = new AwaitingWorkshopAdapter.ViewHolder();
        final Workshop workshop =listData.get(position);
        //  awardcode,accountCode,Donar_Base_line
        rowHolder.awardcode=convertView.findViewById(R.id.awardcode);
        rowHolder.accountCode=convertView.findViewById(R.id.accountCode);
        rowHolder.Donar_Base_line=convertView.findViewById(R.id.DonorBaseLine);
        rowHolder.approvedworkshopname=convertView.findViewById(R.id.approvedworkshopname);
        rowHolder.approvedtimeanddate=convertView.findViewById(R.id.approvedtimeanddate);
        rowHolder.approvedlocation=convertView.findViewById(R.id.approvedlocation);
        rowHolder.approvedcreated_by=convertView.findViewById(R.id.approvedcreated_by);
        rowHolder.registrationlayout=convertView.findViewById(R.id.registrationlayout);
        rowHolder.Request_Approval=convertView.findViewById(R.id.requestApproval);
        Log.d("workshop.getName()",workshop.getName());
        rowHolder.approvedworkshopname.setText(Html.fromHtml("<b>Workshop<b>: ")+workshop.getName());
        rowHolder.approvedlocation.setText(Html.fromHtml("<b>Location<b>: ")+workshop.getLocation());
        rowHolder.approvedcreated_by.setText(Html.fromHtml("<b>Created By<b>: ")+workshop.getUser_id());
        rowHolder.approvedtimeanddate.setText(Html.fromHtml("<b>Created On<b>: ")+workshop.getStart_date());
        rowHolder.accountCode.setText(Html.fromHtml("<b>Account Code<b>: ")+workshop.getAccount_code());
        rowHolder.awardcode.setText(Html.fromHtml("<b>Award Code<b>: ")+workshop.getAward_code());
        rowHolder.Donar_Base_line.setText(Html.fromHtml("<b>Donar Base line<b>: ")+workshop.getDonor_line());
        rowHolder.Request_Approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendForApproval(workshop);
            }
        });
        return convertView;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager=viewPager;
    }

    class ViewHolder{
        TextView approvedworkshopname;
        TextView approvedtimeanddate;
        TextView approvedlocation,awardcode,accountCode,Donar_Base_line;
        TextView approvedcreated_by;
        Button maker,Request_Approval;
        View sections,registrationlayout;

        TextView status;
        Button Confirm_approval,btnRegister,markComplte;
        Button btnReject;

    }

    //----------------------------------------------------------------------------------------------------------------------------//
    ///function to Submitting For Approval workshop
    //----------------------------------------------------------------------------------------------------------------------------//
    public void sendForApproval(Workshop workshop){

        ShowProgressBar(true,false,"Submitting");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("record_id", workshop.getId());
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.SEND_FOR_APPROVAL, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            resultData = response.getString("message").toString();
                            progressDialog.dismiss();
                            new GeneralDialogBuilder().model("Submitted", resultData ).build(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new GeneralDialogBuilder().model("Ooop!", "Failed to Submit").build(context);
                        }
                    }else if (resultStatus.equals("201")){
                      //  String message = response.getString("message").toString();

                        new GeneralDialogBuilder().model("Approved", "No Result Found" ).build(context);


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
}
