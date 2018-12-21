package com.blazesoft.workshopapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class SmartPayDialogScreen extends DialogFragment {
    protected LayoutInflater layoutInflater;
    protected TextView textView;
    private Button MyButton;
    protected ImageView iconView;
    protected   TextView messageStatusView;
    protected String message="";
    protected int IconId=0;
    protected String MessageStatus;
    protected String ButtonString="";

    public Button getMyButton() {
        return MyButton;
    }

    public String getButttonString() {
        return ButtonString;
    }

    public void setButttonString(String butttonString) {
        ButtonString = butttonString;
    }

    public void setIconId(int icon){

        this.IconId=icon;
    }

    public String getMessageStatus() {
        return MessageStatus;
    }

    public int getIconId() {

        return IconId;
    }

    public void setMessageStatus(String messageStatus) {
        MessageStatus = messageStatus;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogbuilder= new AlertDialog.Builder(getActivity());
        layoutInflater=getActivity().getLayoutInflater();

        return dialogbuilder.create();
    }
    public void removeAllClickListener(){
        if(MyButton.hasOnClickListeners()){
            MyButton.setOnClickListener(null);
        }
    }
    public void addClickListener(View.OnClickListener onClickListener){
        MyButton.setOnClickListener(onClickListener);
    }
    public void getFooterView(){

    }
    public void setMessage(String message){
        if(message==null){
            this.message="Balance ";
        }
        this.message=message;
    }


}
