package com.blazesoft.workshopapp.adapters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.activities.activity_attendee_reg;
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

public class RegistrationAdapter extends BaseAdapter {

    private AppCompatActivity context;
    private LayoutInflater layoutInflater;
    ProgressDialog progressDialog;
    private TextView progressText;
    private View progressContainer;
    private ImageView progressWarning;
    private List<Workshop> listData;

    private ViewPager viewPager;
    public RegistrationAdapter(AppCompatActivity context, List<Workshop> Listdata){
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
        final RegistrationAdapter.ViewHolder rowHolder;

        convertView = layoutInflater.inflate(R.layout.approved_workshops, parent, false);
        rowHolder = new RegistrationAdapter.ViewHolder();
        final Workshop workshop =listData.get(position);
        //  awardcode,accountCode,Donar_Base_line
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        rowHolder.btnMore=convertView.findViewById(R.id.button_more);
        rowHolder.btnMore.startAnimation(animation);
        rowHolder.awardcode=convertView.findViewById(R.id.awardcode);
        rowHolder.accountCode=convertView.findViewById(R.id.accountCode);
        rowHolder.Donar_Base_line=convertView.findViewById(R.id.DonorBaseLine);
        rowHolder.approvedworkshopname=convertView.findViewById(R.id.approvedworkshopname);
        rowHolder.approvedtimeanddate=convertView.findViewById(R.id.approvedtimeanddate);
        rowHolder.approvedlocation=convertView.findViewById(R.id.approvedlocation);
        rowHolder.markComplte=convertView.findViewById(R.id.markComplte);

        rowHolder.approvedcreated_by=convertView.findViewById(R.id.approvedcreated_by);
        rowHolder.btnRegister=convertView.findViewById(R.id.register);
        rowHolder.sections = convertView.findViewById(R.id.sections);
        rowHolder.registrationlayout=convertView.findViewById(R.id.registrationlayout);
        rowHolder.status=convertView.findViewById(R.id.status);
        rowHolder.Confirm_approval=convertView.findViewById(R.id.Confirm_approval);
        rowHolder.btnReject=convertView.findViewById(R.id.Decline_approval);
        Log.d("workshop.getName()",workshop.getName());
        rowHolder.approvedworkshopname.setText(Html.fromHtml("<b>Workshop<b>: ")+workshop.getName());
        rowHolder.approvedlocation.setText(Html.fromHtml("<b>Location<b>: ")+workshop.getLocation());
        rowHolder.approvedcreated_by.setText(Html.fromHtml("<b>Created By<b>: ")+workshop.getUser_id());
        rowHolder.approvedtimeanddate.setText(Html.fromHtml("<b>Created On<b>: ")+workshop.getStart_date());
        rowHolder.accountCode.setText(Html.fromHtml("<b>Account Code<b>: ")+workshop.getAccount_code());
        rowHolder.awardcode.setText(Html.fromHtml("<b>Award Code<b>: ")+workshop.getAward_code());
        rowHolder.Donar_Base_line.setText(Html.fromHtml("<b>Donar Base line<b>: ")+workshop.getDonor_line());
      //  rowHolder.status.setText(Html.fromHtml("<B>Status<B>: ")+workshop.getApproval_status());
        if(workshop.getApproval_status().equals("1")){
            rowHolder.status.setText(Html.fromHtml("<b>Status<b>: ")+"Approved");
            rowHolder.btnReject.setVisibility(View.GONE);
            rowHolder.Confirm_approval.setVisibility(View.GONE);
        }else {
            rowHolder.status.setText(Html.fromHtml("<b>Status<b>: ")+"Pending");

        }
        rowHolder.Confirm_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkshopApproval(workshop);

            }
        });
        rowHolder.markComplte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markComplte(workshop);
            }
        });

        rowHolder.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Workshop workshop=getItem(position);
                Intent i =new Intent(context,activity_attendee_reg.class);
                Bundle bundle=new Bundle();
                bundle.putString("workshop_id",workshop.getId());
                bundle.putString("workshopname",workshop.getName());
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
        final View queueView = convertView.findViewById(R.id.sections);
        rowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(queueView.getVisibility()==View.VISIBLE){
                    queueView.setVisibility(View.GONE);
                    v.clearAnimation();
                }else{
                    queueView.setVisibility(View.VISIBLE);
                    v.clearAnimation();
                }
            }
        });

        rowHolder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkshopDecline(workshop);
            }
        });
        rowHolder.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        Button maker,btnMore;
        View sections,registrationlayout;

        TextView status;
        Button Confirm_approval,btnRegister,markComplte;
        Button btnReject;

    }

    //----------------------------------------------------------------------------------------------------------------------------//
    ///function to Approve workshop
    //----------------------------------------------------------------------------------------------------------------------------//
    public void markComplte(Workshop workshop){

        ShowProgressBar(true,false,"Approving");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("record_id", workshop.getId());
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.COMPLTE_WORKSHOP, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Approved", resultData ).build(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new GeneralDialogBuilder().model("Ooop!", "Failed to approved").build(context);
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
    //----------------------------------------------------------------------------------------------------------------------------//
    ///function to Approve workshop
    //----------------------------------------------------------------------------------------------------------------------------//
    public void WorkshopApproval(Workshop workshop){

        ShowProgressBar(true,false,"Approving");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("record_id", workshop.getId());
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.WORK_SHOP_APPROVAL, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Approved", resultData ).build(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new GeneralDialogBuilder().model("Ooop!", "Failed to approved").build(context);
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
    //----------------------------------------------------------------------------------------------------------------------------//
    ///function to decline workshop
    //----------------------------------------------------------------------------------------------------------------------------//
    public void WorkshopDecline(Workshop workshop){

        ShowProgressBar1(true,false,"Declining");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("record_id", workshop.getId());
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.DECLINE_WORKSHOP, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                           // resultData = p[
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
