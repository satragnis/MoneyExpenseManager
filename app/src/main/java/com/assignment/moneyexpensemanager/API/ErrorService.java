package com.assignment.moneyexpensemanager.API;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.assignment.moneyexpensemanager.R;

import org.json.JSONObject;



/**
 * Created by dell on 3/4/18.
 */

public class ErrorService {
    private final JSONObject obj;
    private final Context context;
    private final View view;

    /*For checkErrorOld
        public ErrorService(JSONObject data,Context context){
            this.obj=data;
            this.context=context;
        }*/
    public ErrorService(JSONObject data, Context context, View view){
        this.obj                                          = data;
        this.context                                      = context;
        this.view = view;
    }
    public Boolean checkError(){
        Boolean errorMsg                                  = true;
        try {

            if (obj.has("error")) {
                String error                              = obj.getString("error");
                if(error.equalsIgnoreCase("1")){
                    if(obj.has("errorCode")){
                        String errorCode                  = obj.getString("errorCode");
                        int errId                         = 0;
                        String errorMsg1                   = obj.getString("errorMessage");
                        if(Integer.parseInt(error)>0){
                            errId = 1000;
                        }
//                        switch(errorCode){
//                            case "1000":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1001":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1002":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1003":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1004":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1005":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1006":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1007":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1008":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1009":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1010":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1011":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1012":
//                                errId                     = R.string.err1000;
//                                break;
//                            case "1013":
//                                errId                     = R.string.err1013;
//                                break;
//                            case "1017":
//                                errId                     = R.string.err1017;
//                                break;
//                            case "1018":
//                                errId                     = R.string.err1018;
//                                break;
//                            case "1020":
//                                errId                     = R.string.err1020;
//                                break;
//                            case "1021":
//                                errId                     = R.string.err1021;
//                                break;
//                            case "1022":
//                                errId                     = R.string.err1022;
//                                break;
//                            case "1023":
//                                errId                     = R.string.err1023;
//                                break;
//
//                        }
                        if(errId!=0){
//                            String message                = context.getResources().getString(errId);
                            String message                = errorMsg1;

                            if(errId == 100) {
                                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                                        .setAction("UPDATE NOW", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                v.setVisibility(View.GONE);
                                                final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                                                try {
                                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                                } catch (android.content.ActivityNotFoundException anfe) {
                                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                }
                                            }
                                        })
                                        .setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                                snackbar.show();

                            }else{
                                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                                        .setAction("DISMISS", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                v.setVisibility(View.GONE);
                                            }
                                        })
                                        .setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                                snackbar.show();
                            }
                            errorMsg=false;
                        }else{
                            errorMsg=true;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return errorMsg;
    }
}
