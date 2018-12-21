package com.blazesoft.workshopapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;
import com.blazesoft.workshopapp.models.Consultants;

import java.util.List;

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
        ConsultantsSpinnerAdapter.viewHolder1 spinnerViewHolder;
        if(convertView==null){
            spinnerViewHolder=new ConsultantsSpinnerAdapter.viewHolder1();
            convertView=layoutInflater.inflate(R.layout.consultants_spinner_list,parent,false);
            spinnerViewHolder.spinnerItemList=convertView.findViewById(R.id.spinner_list);
            spinnerViewHolder.phone=convertView.findViewById(R.id.phone);
            spinnerViewHolder.name=convertView.findViewById(R.id.name);
            spinnerViewHolder.idno=convertView.findViewById(R.id.idno);
            convertView.setTag(spinnerViewHolder);

        }else{

            spinnerViewHolder= (ConsultantsSpinnerAdapter.viewHolder1) convertView.getTag();

        }
        spinnerViewHolder.spinnerItemList.setText( "Name: "+listData.get(position).getName()+"Phone: "+listData.get(position).getRegistration_number());

        return convertView;
    }
    class viewHolder1{
        TextView spinnerItemList,phone,name,idno;

    }


}
