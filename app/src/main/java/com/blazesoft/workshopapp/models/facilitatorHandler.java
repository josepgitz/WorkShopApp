package com.blazesoft.workshopapp.models;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blazesoft.workshopapp.R;

import java.util.List;

public class facilitatorHandler extends BaseAdapter {
    private AppCompatActivity context;
    private LayoutInflater layoutInflater;
    private List <Facilitator> listData;
    View view;
   public facilitatorHandler(AppCompatActivity context, List<Facilitator> Listdata, View view){
         this.context = context;
         this.listData = Listdata;
         this.view=view;
    }
    @Override
    public int getCount() {
        return (listData.size());
    }

    @Override
    public Facilitator getItem(int position) {

        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final ViewHolder rowHolder;
//        convertView = layoutInflater.inflate(R.layout.spinner_facilitator, parent, false);
//        rowHolder = new ViewHolder();
//        final Facilitator facilitator =listData.get(position);
//
//        for(int i =0; i < listData.size(); i++) {
//            rowHolder.name=convertView.findViewById(R.id.Name);
//            rowHolder.Phone=convertView.findViewById(R.id.Phone);
//            rowHolder.ID_Number=convertView.findViewById(R.id.ID_number);
//            rowHolder.name.setText(facilitator.getFname()+""+facilitator.getLname());
//            rowHolder.Phone.setText(facilitator.getPhone());
//            rowHolder.ID_Number.setText(facilitator.getId_number());
//        }
            return convertView;
    }


    class ViewHolder{
       TextView name;
       TextView ID_Number;
       TextView Phone;
    }
}
