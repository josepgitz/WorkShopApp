package com.blazesoft.workshopapp.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class GeneralDialogBuilder {
    private String message;
    private String title;
   public GeneralDialogBuilder model(String title,String message){
        this.title=title;
        this.message=message;
        return this;
    }
   public  void build(Context context){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
          dialog.dismiss();
            }
        });

       builder.create().show();
        return;
    }
}
