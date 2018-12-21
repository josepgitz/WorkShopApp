package com.blazesoft.workshopapp.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.models.Consultants;
import com.blazesoft.workshopapp.activities.activity_attendee_reg;
import com.blazesoft.workshopapp.activities.dashboardsReview;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.dialogs.GeneralDialogBuilder;
import com.blazesoft.workshopapp.models.Facilitator;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkshopAdapter extends BaseAdapter {
    private AppCompatActivity context;
    private LayoutInflater layoutInflater;
    ProgressDialog progressDialog;
    private TextView progressText;
    private View progressContainer;
    private ImageView progressWarning;
    private List<Workshop> listData;
    Spinner facilitate;
    public List<Facilitator> facilitators=new ArrayList<>();
    public List<Consultants> consultants=new ArrayList<>();
 FacilitatorSpinnerAdapter facilitatorSpinnerAdapter;
    ConsultantsSpinnerAdapter consultantsSpinnerAdapter;
    private ViewPager viewPager;
    public WorkshopAdapter(AppCompatActivity context, List<Workshop> Listdata,List<Facilitator> facilitators,List<Consultants> consultants){
        this.context = context;
        if (this.context!=null){
            layoutInflater =LayoutInflater.from(this.context );
        }
        this.listData = Listdata;
        getFacilitator();
        getConsultants();
        this.facilitators=facilitators;
         facilitatorSpinnerAdapter=new FacilitatorSpinnerAdapter(context,facilitators);
        consultantsSpinnerAdapter=new ConsultantsSpinnerAdapter(context,consultants);


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
        final ViewHolder rowHolder;

        convertView = layoutInflater.inflate(R.layout.workshop_approval_row, parent, false);
        rowHolder = new ViewHolder();
        final Workshop workshop =listData.get(position);
      //  awardcode,accountCode,Donar_Base_line
            rowHolder.awardcode=convertView.findViewById(R.id.awardcode);
            rowHolder.accountCode=convertView.findViewById(R.id.accountCode);
            rowHolder.Donar_Base_line=convertView.findViewById(R.id.DonorBaseLine);
            rowHolder.approvedworkshopname=convertView.findViewById(R.id.approvedworkshopname);
            rowHolder.approvedtimeanddate=convertView.findViewById(R.id.approvedtimeanddate);
            rowHolder.approvedlocation=convertView.findViewById(R.id.approvedlocation);
            rowHolder.btnMore=convertView.findViewById(R.id.button_more);
        rowHolder.Attach=convertView.findViewById(R.id.Attach);
            rowHolder.approvedcreated_by=convertView.findViewById(R.id.approvedcreated_by);
            rowHolder.sections = convertView.findViewById(R.id.sections);
            rowHolder.approvallayout=convertView.findViewById(R.id.approval_layout);
            rowHolder.btnSuspend=convertView.findViewById(R.id.suspend);
            rowHolder.btnSendBack=convertView.findViewById(R.id.send_back);
            rowHolder.status=convertView.findViewById(R.id.status);
            rowHolder.facilitate=convertView.findViewById(R.id.facilitat);
            rowHolder.consultant=convertView.findViewById(R.id.Consultants);
            rowHolder.facilitate.setAdapter(facilitatorSpinnerAdapter);
            rowHolder.consultant.setAdapter(consultantsSpinnerAdapter);
            rowHolder.Confirm_approval=convertView.findViewById(R.id.Confirm_approval);
            rowHolder.ConfirmSuspendno=convertView.findViewById(R.id.ConfirmSuspendno);
            rowHolder.btnSendBackconfimr=convertView.findViewById(R.id.ConfirmSendBack);
            rowHolder.ConfirmSendBackno=convertView.findViewById(R.id.ConfirmSendBackno);
            rowHolder.btnSuspendconfirm=convertView.findViewById(R.id.ConfirmSuspend);
            rowHolder.btnReject=convertView.findViewById(R.id.Decline_approval);
            rowHolder.sendBackReason=convertView.findViewById(R.id.reasons);
            rowHolder.suspendReason=convertView.findViewById(R.id.suspendReasons);


               ///Animation starts here
                final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                animation.setDuration(500); // duration - half a second
                animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
                animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
                animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
                rowHolder.btnMore=convertView.findViewById(R.id.button_more);
                rowHolder.btnMore.startAnimation(animation);
                //animalion ends here



            Log.d("workshop.getName()",workshop.getName());
            rowHolder.approvedworkshopname.setText(Html.fromHtml("<b>Workshop<b>: ")+workshop.getName());
            rowHolder.approvedlocation.setText(Html.fromHtml("<b>Location<b>: ")+workshop.getLocation());
            rowHolder.approvedcreated_by.setText(Html.fromHtml("<b>Created By<b>: ")+workshop.getUser_id());
            rowHolder.approvedtimeanddate.setText(Html.fromHtml("<b>Created On<b>: ")+workshop.getStart_date());
            rowHolder.accountCode.setText(Html.fromHtml("<b>Account Code<b>: ")+workshop.getAccount_code());
            rowHolder.awardcode.setText(Html.fromHtml("<b>Award Code<b>: ")+workshop.getAward_code());
            rowHolder.Donar_Base_line.setText(Html.fromHtml("<b>Donar Base line<b>: ")+workshop.getDonor_line());

        if(workshop.getApproval_status().equals("1")){
                rowHolder.status.setText(Html.fromHtml("<b>Status<b>: ")+"Approved");
            rowHolder.sections.setBackgroundColor( context.getResources().getColor(R.color.colorMuted));
                rowHolder.btnReject.setVisibility(View.GONE);
                rowHolder.Confirm_approval.setVisibility(View.GONE);
            }else {
                rowHolder.status.setText(Html.fromHtml("<b>Status<b>: ")+"Pending");
            rowHolder.sections.setBackgroundColor( context.getResources().getColor(R.color.common_google_signin_btn_text_light_disabled));
            }

//            rowHolder.approvallayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Workshop workshop=getItem(position);
//                    Intent i =new Intent(context,activity_attendee_reg.class);
//                    Bundle bundle=new Bundle();
//                    bundle.putString("workshop_id",workshop.getId());
//                    bundle.putString("workshopname",workshop.getName());
//                    i.putExtras(bundle);
//                    context.startActivity(i);
//                }
//            });
        final View attachView = convertView.findViewById(R.id.Attach1);
        attachView.setVisibility(View.GONE);
        final View sectionView = convertView.findViewById(R.id.sections);
        final View buttonsView = convertView.findViewById(R.id.buttons);
        final View reasonView = convertView.findViewById(R.id.reason);
        final View suspendView = convertView.findViewById(R.id.suspendReason);
        rowHolder.ConfirmSuspendno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    suspendView.setVisibility(View.GONE);
                    buttonsView.setVisibility(View.VISIBLE);

            }
        });

        rowHolder.ConfirmSendBackno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reasonView.setVisibility(View.GONE);
                buttonsView.setVisibility(View.VISIBLE);

            }
        });
        rowHolder.btnSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(suspendView.getVisibility()==View.VISIBLE){
                    suspendView.setVisibility(View.GONE);
                    buttonsView.setVisibility(View.VISIBLE);

                }else{
                    suspendView.setVisibility(View.VISIBLE);
                    buttonsView.setVisibility(View.GONE);
                    rowHolder.suspendReason.requestFocus();
                }
            }
        });
        rowHolder.btnSendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(reasonView.getVisibility()==View.VISIBLE){
                    reasonView.setVisibility(View.GONE);
                    sectionView.setVisibility(View.VISIBLE);
                }else{
                    reasonView.setVisibility(View.VISIBLE);
                    buttonsView.setVisibility(View.GONE);
                    rowHolder.sendBackReason.requestFocus();
                }
            }
        });

        rowHolder.btnSendBackconfimr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(TextUtils.isEmpty(rowHolder.sendBackReason.getText().toString()))){

                    WorkshopSendBack(workshop, rowHolder.sendBackReason.getText().toString());
                }else {
              rowHolder.sendBackReason.setError("Reason is empty");

                }
            }
        });
        rowHolder.btnSuspendconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(!(TextUtils.isEmpty(rowHolder.suspendReason.getText().toString()))){

            WorkshopSuspend(workshop,rowHolder.suspendReason.getText().toString().trim());
        }else{

            rowHolder.suspendReason.setError("Reason is empty");
        }


            }
        });
        rowHolder.facilitate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(attachView.getVisibility()==View.GONE){

                    attachView.setVisibility(View.VISIBLE);
                }else {
                    attachView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
              //  attachView.setVisibility(View.GONE);

            }
        });

        rowHolder.Attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///add attach functions here
                rowHolder.Attach.setVisibility(View.GONE);
                if(consultants.get(position).getRegistration_number()!=null){
                    AttachConsultants(workshop,consultants.get(position));
                }
            }
        });
        rowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {

                    if(sectionView.getVisibility()==View.VISIBLE){
                        sectionView.setVisibility(View.GONE);
                        v.clearAnimation();
                    }else{
                        sectionView.setVisibility(View.VISIBLE);
                        v.clearAnimation();
                    }
                }
            });
       // rowHolder.maker=convertView.findViewById(R.id.temperaNaker);
            rowHolder.Confirm_approval.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkshopApproval(workshop);
                }
            });
            rowHolder.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkshopDecline(workshop);
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
        Button maker,btnMore,btnSuspend,btnSendBack,btnSendBackconfimr,btnSuspendconfirm,ConfirmSuspendno,ConfirmSendBackno;
        View sections,approvallayout;
        Spinner facilitate,consultant;
        TextView status;
        Button Confirm_approval,Attach;
        Button btnReject;
        EditText sendBackReason,suspendReason;
    }

    //----------------------------------------------------------------------------------------------------------------------------//
    ///function to suspend workshop
    //----------------------------------------------------------------------------------------------------------------------------//
    public void WorkshopSendBack(Workshop workshop,String Reason){
        ShowProgressBar(true,false,"Reversing...");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("record_id", workshop.getId());
            jsonObject.put("reason",Reason);
            Log.d("Visit1222", jsonObject.toString());
            NetworkConnection.makeAPostRequest(URL.SEND_BACK_WORKSHOP, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            //   resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Reversed", resultData ).build(context);
                        } catch (JSONException e) {
                            Log.d("Logger",resultData);
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
    ///function to suspend workshop
    //----------------------------------------------------------------------------------------------------------------------------//
    public void WorkshopSuspend(Workshop workshop,String Reason){

        ShowProgressBar(true,false,"Suspending");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("record_id", workshop.getId());
            jsonObject.put("reason",Reason );
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.SUSPEND_WORKSHOP, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            //   resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Suspended", resultData ).build(context);
                        } catch (JSONException e) {
                            Log.d("Logger",resultData);
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
                         //   resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Approved", resultData ).build(context);
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            Log.d("Logger",resultData);
                            e.printStackTrace();
                            progressDialog.dismiss();
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
                            progressDialog.dismiss();
                            resultData = response.getString("message").toString();
                            new GeneralDialogBuilder().model("Declined", resultData ).build(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
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

    private void ShowProgressBar2(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Attaching...");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
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
    public class FacilitatorSpinnerAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private List<Facilitator> listData;
        private Context context;

        @SuppressLint("ServiceCast")
        public FacilitatorSpinnerAdapter(Context context, List<Facilitator>listData){
            this.listData=listData;
            layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            this.context=context;


        }
        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Facilitator getItem(int position) {
            return (Facilitator)listData.get(position);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           viewHolder spinnerViewHolder;
            if(convertView==null){
                spinnerViewHolder=new viewHolder();
                convertView=layoutInflater.inflate(R.layout.facilitator_spinner_list,parent,false);
                spinnerViewHolder.spinnerItemList=convertView.findViewById(R.id.spinner_list);
                spinnerViewHolder.phone=convertView.findViewById(R.id.phone);
                spinnerViewHolder.name=convertView.findViewById(R.id.name);
                spinnerViewHolder.idno=convertView.findViewById(R.id.idno);
                convertView.setTag(spinnerViewHolder);

            }else{

                spinnerViewHolder= (viewHolder) convertView.getTag();

            }
                spinnerViewHolder.spinnerItemList.setText( "Name:"+listData.get(position).getFname()+" "+listData.get(position).getLname()+" ID No "+listData.get(position).getId_number());

            return convertView;
        }
        class viewHolder{
            TextView spinnerItemList,phone,name,idno;

        }



    }




    public class ConsultantsSpinnerAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private List<Consultants> listData;
        private Context context;

        @SuppressLint("ServiceCast")
        public ConsultantsSpinnerAdapter(Context context, List<Consultants>listData){
            this.listData=listData;
            layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            this.context=context;


        }
        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Consultants getItem(int position) {
            return (Consultants)listData.get(position);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            viewHolder1 spinnerViewHolder;
            if(convertView==null){
                spinnerViewHolder=new viewHolder1();
                convertView=layoutInflater.inflate(R.layout.consultants_spinner_list,parent,false);
                spinnerViewHolder.spinnerItemList=convertView.findViewById(R.id.spinner_list);
                spinnerViewHolder.phone=convertView.findViewById(R.id.phone);
                spinnerViewHolder.name=convertView.findViewById(R.id.name);
                spinnerViewHolder.idno=convertView.findViewById(R.id.idno);
                convertView.setTag(spinnerViewHolder);

            }else{

                spinnerViewHolder= (viewHolder1) convertView.getTag();

            }
            spinnerViewHolder.spinnerItemList.setText( "Name: "+listData.get(position).getName()+" Phone: "+listData.get(position).getRegistration_number());

            return convertView;
        }
        class viewHolder1{
            TextView spinnerItemList,phone,name,idno;

        }



    }
    public void getFacilitator() {
        JSONObject headers = new JSONObject();
        String url = URL.GET_FACILITATOR;
        headers = GLobalHeaders.getGlobalHeaders(context);
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

                                Facilitator facilitator = Facilitator.getFacilitatorFrom(jsonArray.getJSONObject(i).toString());
                               facilitators.add(facilitator);

                            }
                        }


                       // FacilitatorSpinnerAdapter facilitatorSpinnerAdapter = new FacilitatorSpinnerAdapter( context,facilitators);
                      facilitatorSpinnerAdapter.notifyDataSetChanged();
                        notifyDataSetChanged();
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
    public void getConsultants() {
   //     loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_APPROVED_CONSULTANTS;
        headers = GLobalHeaders.getGlobalHeaders(context);
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

                                Consultants consultant = Consultants.getConsultantFrom(jsonArray.getJSONObject(i).toString());
                                consultants.add(consultant);
                            }

                        }
                        consultantsSpinnerAdapter.notifyDataSetChanged();
                        notifyDataSetChanged();
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
    @SuppressLint("WrongConstant")
    private void manageBlinkEffect() {
//        ObjectAnimator anim = ObjectAnimator.ofInt(btnMore, "backgroundColor", Color.WHITE, Color.RED,
//                Color.WHITE);
//        anim.setDuration(1500);
//        anim.setEvaluator(new ArgbEvaluator());
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        anim.start();
    }

    public void AttachConsultants(Workshop workshop,Consultants consultants){

        ShowProgressBar2(true,false,"Attaching...");
        int consultant =1;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("consultant_type", consultant);
            jsonObject.put("consultant_id", consultants.getId());
            jsonObject.put("workshop_id",workshop.getId());
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.ATTACH_CONSULTANTS, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                            //   resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Attached", resultData ).build(context);
                        } catch (JSONException e) {
                            Log.d("Logger",resultData);
                            e.printStackTrace();
                            new GeneralDialogBuilder().model("Ooop!", "Failed to approved").build(context);
                        }
                    }else if(resultStatus.equals("201")){
                        progressDialog.dismiss();
                        new GeneralDialogBuilder().model("Failed To Attach", resultData ).build(context);

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
}
