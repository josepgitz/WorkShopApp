package com.blazesoft.workshopapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

public class NavigationItem extends ArrayAdapter {
    Object[] object;
    public NavigationItem(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
        this.object=objects;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return  this.object[position];
    }
}
