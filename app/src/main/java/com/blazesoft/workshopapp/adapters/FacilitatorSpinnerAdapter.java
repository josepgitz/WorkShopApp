package com.blazesoft.workshopapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.activities.dashboardsReview;
import com.blazesoft.workshopapp.constants.GLobalHeaders;
import com.blazesoft.workshopapp.constants.URL;
import com.blazesoft.workshopapp.models.Facilitator;
import com.blazesoft.workshopapp.models.Workshop;
import com.blazesoft.workshopapp.newtork.local.NetworkConnection;
import com.blazesoft.workshopapp.newtork.local.OnReceivingResult;
import com.blazesoft.workshopapp.newtork.local.RemoteResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static com.blazesoft.workshopapp.R.layout.facilitator_spinner_list;

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
//            spinnerViewHolder.name=convertView.findViewById(R.id.name);
//            spinnerViewHolder.idno=convertView.findViewById(R.id.idno);
        convertView.setTag(spinnerViewHolder);

        }else{

            spinnerViewHolder= (viewHolder) convertView.getTag();

        }
        spinnerViewHolder.spinnerItemList.setText( "Name:"+listData.get(position).getFname()+" "+listData.get(position).getLname()+" ID No "+listData.get(position).getId_number());
      //  spinnerViewHolder.phone.setText(listData.get(position).getPhone());
//        spinnerViewHolder.name.setText(listData.get(position).getFname()+""+listData.get(position).getLname());
//        spinnerViewHolder.idno.setText(listData.get(position).getId_number());
        return convertView;
    }
    class viewHolder{
        TextView spinnerItemList,phone,name,idno;

    }



}
