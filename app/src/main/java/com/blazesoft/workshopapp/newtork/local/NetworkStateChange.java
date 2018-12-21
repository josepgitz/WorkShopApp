package com.blazesoft.workshopapp.newtork.local;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStateChange extends BroadcastReceiver {
    NetworkStateChangeListener networkStateChangeListener;
    public NetworkStateChange(NetworkStateChangeListener networkStateChangeListener){
        this.networkStateChangeListener=networkStateChangeListener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (networkStateChangeListener!=null){
            networkStateChangeListener.onNetworkChangeDetected(intent);
        }

    }
}
