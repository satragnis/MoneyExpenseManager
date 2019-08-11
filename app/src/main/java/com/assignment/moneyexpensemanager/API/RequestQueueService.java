package com.assignment.moneyexpensemanager.API;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.assignment.moneyexpensemanager.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



/**
 * Created by dell on 20/3/18.
 */

public class RequestQueueService {
    private static RequestQueueService mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private static Dialog mProgressDialog;
    Button cancel;

    private RequestQueueService(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestQueueService(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public Map<String, String> getRequestHeader() {
        return new HashMap<>();
    }

    public void clearCache() {
        mRequestQueue.getCache().clear();
    }

    public void removeCache(String key) {
        mRequestQueue.getCache().remove(key);
    }

    //To show alert / error message
    public static void showAlert(String message, final FragmentActivity context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Something went haywire..");
            builder.setMessage(message);
            builder.setPositiveButton("OK", null);
            builder.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Start showing progress
    public static void showProgressDialog(final Activity activity) {
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mProgressDialog != null) {
                        if (mProgressDialog.isShowing()){ cancelProgressDialog();}
                    }mProgressDialog = new Dialog(activity);
                    Objects.requireNonNull(mProgressDialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    mProgressDialog.setContentView(R.layout.progress_indicator);
                    mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mProgressDialog.show();
                    mProgressDialog.setCancelable(false);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //Stop showing progress
    public static void cancelProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void showAlert(String message, final Context context, View view) {
    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(context.getResources().getString(R.string.thistime_alert));
    builder.setMessage(message);
    builder.setPositiveButton("OK", null);
    builder.show();*/
        try {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setVisibility(View.GONE);
                        }
                    })
                    .setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
