package com.blazesoft.workshopapp.adapters;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.activities.activity_attendee_reg;
import com.blazesoft.workshopapp.models.Facilitator;
import com.blazesoft.workshopapp.models.Workshop;

import java.util.List;

public class AllWorkshopAdpter extends BaseAdapter{
    private AppCompatActivity context;
    private LayoutInflater layoutInflater;
    ProgressDialog progressDialog;
    private TextView progressText;
    private View progressContainer;
    private ImageView progressWarning;
    private List<Workshop> listData;
    Spinner facilitate;
    public List<Facilitator> facilitators;
    WorkshopAdapter.FacilitatorSpinnerAdapter facilitatorSpinnerAdapter;
    WorkshopAdapter.ConsultantsSpinnerAdapter consultantsSpinnerAdapter;
    private ViewPager viewPager;
    public AllWorkshopAdpter(AppCompatActivity context, List<Workshop> Listdata){
        this.context = context;
        if (this.context!=null){
            layoutInflater =LayoutInflater.from(this.context );
        }
        this.listData = Listdata;

        this.facilitators=facilitators;


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
        final AllWorkshopAdpter.ViewHolder rowHolder;

        convertView = layoutInflater.inflate(R.layout.landingpage, parent, false);
        rowHolder = new AllWorkshopAdpter.ViewHolder();
        final Workshop workshop =listData.get(position);
        //  awardcode,accountCode,Donar_Base_line
        rowHolder.awardcode=convertView.findViewById(R.id.awardcode);
        rowHolder.accountCode=convertView.findViewById(R.id.accountCode);
        rowHolder.Donar_Base_line=convertView.findViewById(R.id.donoline);
        rowHolder.name=convertView.findViewById(R.id.name);
        rowHolder.location=convertView.findViewById(R.id.location);
        rowHolder.stardate=convertView.findViewById(R.id.stardate);
        rowHolder.facilitatorid=convertView.findViewById(R.id.facilitatorid);
        rowHolder.cost=convertView.findViewById(R.id.cost);
        rowHolder.status=convertView.findViewById(R.id.status);


        rowHolder.name.setText(Html.fromHtml("<b>Workshop<b>: ")+workshop.getName());
        rowHolder.location.setText(Html.fromHtml("<b>Location<b>: ")+workshop.getLocation());
        rowHolder.stardate.setText(Html.fromHtml("<b>Start Date<b>: ")+workshop.getStart_date());
        rowHolder.accountCode.setText(Html.fromHtml("<b>Account Code<b>: ")+workshop.getAccount_code());
        rowHolder.awardcode.setText(Html.fromHtml("<b>Award Code<b>: ")+workshop.getAward_code());
        rowHolder.Donar_Base_line.setText(Html.fromHtml("<b>Base Line<b>: ")+workshop.getDonor_line());
        rowHolder.status.setText(Html.fromHtml("<b>Status<b>: ")+workshop.getDonor_line());
        rowHolder.cost.setText(Html.fromHtml("<b>Cost<b>: ")+workshop.getCost());
        rowHolder.facilitatorid.setText(Html.fromHtml("<b>Facilitator<b>: ")+workshop.getFacilitator_id());

        return convertView;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager=viewPager;
    }

    class ViewHolder{
        TextView name,location,stardate,facilitatorid,cost,status;
        TextView awardcode,accountCode,Donar_Base_line;


    }
}
