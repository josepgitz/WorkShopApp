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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.activities.activity_attendee_reg;
import com.blazesoft.workshopapp.models.Workshop;

import java.util.List;

public class SuspendedWorkshopAdpter extends BaseAdapter {
    private AppCompatActivity context;
    private LayoutInflater layoutInflater;
    ProgressDialog progressDialog;
    private TextView progressText;
    private View progressContainer;
    private ImageView progressWarning;
    private List<Workshop> listData;

    private ViewPager viewPager;
    public SuspendedWorkshopAdpter(AppCompatActivity context, List<Workshop> Listdata){
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
        final SuspendedWorkshopAdpter.ViewHolder rowHolder;
        convertView = layoutInflater.inflate(R.layout.suspended_workshop_row, parent, false);
        rowHolder = new SuspendedWorkshopAdpter.ViewHolder();
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
        rowHolder.status=convertView.findViewById(R.id.status);
        rowHolder.Confirm_approval=convertView.findViewById(R.id.Confirm_approval);
        Log.d("workshop.getName()",workshop.getName());
        rowHolder.approvedworkshopname.setText(Html.fromHtml("<b>Workshop<b>: ")+workshop.getName());
        rowHolder.approvedlocation.setText(Html.fromHtml("<b>Location<b>: ")+workshop.getLocation());
        rowHolder.approvedcreated_by.setText(Html.fromHtml("<b>Created By<b>: ")+workshop.getUser_id());
        rowHolder.approvedtimeanddate.setText(Html.fromHtml("<b>Created On<b>: ")+workshop.getStart_date());
        rowHolder.accountCode.setText(Html.fromHtml("<b>Account Code<b>: ")+workshop.getAccount_code());
        rowHolder.awardcode.setText(Html.fromHtml("<b>Award Code<b>: ")+workshop.getAward_code());
        rowHolder.Donar_Base_line.setText(Html.fromHtml("<b>Donar Base line<b>: ")+workshop.getDonor_line());
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
}
