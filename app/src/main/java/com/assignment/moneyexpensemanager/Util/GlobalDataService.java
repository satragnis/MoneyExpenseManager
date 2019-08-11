package com.assignment.moneyexpensemanager.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.assignment.moneyexpensemanager.Activity.MainActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import es.dmoral.toasty.Toasty;


public class GlobalDataService {
    private static GlobalDataService mInstance;
    private Context mContext;
    private String deviceId = "";
    private String gcmPushKey = "";
//    private CurrentUser currentUser;


    public GlobalDataService() {
    }
//    public CurrentUser getCurrentUser() {
//        return currentUser;
//    }
//
//    public void setCurrentUser(CurrentUser currentUser) {
//        this.currentUser = currentUser;
//    }

//    public void setGlobalVariable(Context context,String) {
//        try {
//            if(context!=null) {
//                SharedPreferences preference = context.getSharedPreferences("dnowCurrentUserInfo", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preference.edit();
////                JSONObject json = new JSONObject(currentUser.toSerializableParams());
//                editor.putString("currentUser", json.toString());
//                editor.apply();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    public static String convertToFirstCharUpper(String str) {
        // Create a char array of given String
        char ch[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            // If first character of a word is found
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {

                // If it is in lower-case
                if (ch[i] >= 'a' && ch[i] <= 'z') {

                    // Convert into Upper-case
                    ch[i] = (char) (ch[i] - 'a' + 'A');
                }
            }

            // If apart from first character
            // Any one is in Upper-case
            else if (ch[i] >= 'A' && ch[i] <= 'Z')

                // Convert into Lower-Case
                ch[i] = (char) (ch[i] + 'a' - 'A');
        }

        // Convert the char array to equivalent String
        String st = new String(ch);
        return st;
    }



    public static synchronized GlobalDataService getInstance() {
        if (mInstance == null) {
            mInstance = new GlobalDataService();
        }
        return mInstance;
    }


    public static void showToasty(Context context, String msg, String type) {
        type = type.toLowerCase();
        switch (type) {
            case "info":
                try {
                    Toasty.info(context, msg, Toast.LENGTH_SHORT, true).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
                break;
            case "error":
                try {
                    Toasty.error(context, msg, Toast.LENGTH_SHORT, true).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
                break;
            case "success":
                try {
                    Toasty.success(context, msg, Toast.LENGTH_SHORT, true).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
                break;
            case "warning":
                try {
                    Toasty.warning(context, msg, Toast.LENGTH_SHORT, true).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
//                    Toasty.info(context,msg,Toast.LENGTH_SHORT,true).show();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                break;

        }
    }


    public void removeSerializedCurrentUser(Context context) {
        SharedPreferences preference = context.getSharedPreferences("test", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove("currentUser");
        editor.apply();
    }

//    public String getPinGlobal(Context context) {
//        SharedPreferences preference = context.getSharedPreferences("test", Context.MODE_PRIVATE);
//        String ans = preference.getString(Params.PIN, null);
//        return ans;
//    }

    public static synchronized void clearInstance() {
        mInstance = null;
    }


    public static void logout(Context context) {
//        if(getActivity()!=null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
//            final AlertDialog alertDialog = builder.create();
////        alertDialog.setMessage(getString(R.string.HULI));
//            alertDialog.setMessage(getString(R.string.logout_string));
//            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    alertDialog.cancel();
//                    alertDialog.dismiss();
//                }
//            });
//            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    DatabaseHelper databaseHelper = new DatabaseHelper();
        try {
            DatabaseHelper.setAccountDetails(context, Params.USER_ID, "NOTFOUND");
            DatabaseHelper.tearDown();
            Toasty.info(context, "Logged out successfully.", Toast.LENGTH_SHORT, true).show();
//            UtilsPrefs.clearMpin(context);
//            UtilsPrefs.setMpinSwitch(context,false);
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

//                    try {
//                        FirebaseAuth.getInstance().signOut();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            });
//            alertDialog.show();
//        }

    }


    public static void logOutOnFreeze(Context context) {
        try {
            DatabaseHelper.setAccountDetails(context, Params.USER_ID, "NOTFOUND");
            DatabaseHelper.tearDown();
            Toasty.info(context, "Logged out successfully.", Toast.LENGTH_SHORT, true).show();
//            UtilsPrefs.clearMpin(context);
//            UtilsPrefs.setMpinSwitch(context,false);
        } catch (Exception e) {
            e.printStackTrace();
        }

//                    try {
//                        FirebaseAuth.getInstance().signOut();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            });
//            alertDialog.show();
//        }

    }


    //CheckLoginRedirect@ganesh

//    public static boolean  checkLoginStatus(Context context)
////    {
////        String data = DatabaseHelper.getAccountDetails(context, Params.AUTH_TOKEN);
////        return !data.equals("NOTFOUND");
////    }

    public static boolean isFirstTimeAppOpen(Context context) {
        String data = DatabaseHelper.getAccountDetails(context, Params.FIRST_TIME_APP);
        return data.equals("TRUE");
    }

    public static boolean FirstTimeAppOpened(Context context) {
        boolean data = DatabaseHelper.setAccountDetails(context, Params.FIRST_TIME_APP, "TRUE");
        if (!data) {
            return false;
        } else {
            Log.d(Params.TAG, "FirstTimeAppOpened: ");
            return true;
        }
    }

//    public static void redirectToLogin(Context context,Class baseClass)
//    {
//        Intent intent = new Intent(context,LoginActivity.class);
//        Log.d(Params.TAG, "redirectToLogin: "+baseClass.getSimpleName());
//        intent.putExtra("baseClass",baseClass);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
//        ((Activity)context).finish();
//    }

    //register network state reciever

//    public static void registerNetworkReceiver(Context context, ConnectivityReceiver connectivityReceiver) {
//        try {
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction("ConnectivityMonitor");
//            context.registerReceiver(connectivityReceiver, intentFilter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void unregisterNetworkReceiver(Context context, ConnectivityReceiver connectivityReceiver) {
//        try {
//            context.unregisterReceiver(connectivityReceiver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.apply();
            }
        }
        return uniqueID;
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static boolean isPasswordvalid(String target) {
//        return (!TextUtils.isEmpty(target) && target!= null && target.length() == 8);
        return target.length() == 8;
    }


    public static boolean isEmptyEditText(EditText editText) {
        return Objects.requireNonNull(editText).getText().toString().trim().isEmpty() || Objects.requireNonNull(editText).getText().toString().equalsIgnoreCase("");
    }


//    public static void backToHome(Context context) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//        ((Activity) context).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//        ((Activity) context).finish();
//
//    }
//
//    public static void backToSignup(Context context) {
//        Intent intent = new Intent(context, SignupActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//        ((Activity) context).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//        ((Activity) context).finish();
//    }
//
//
//    public static void moveToHomeScreen(Context context) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//        ((Activity) context).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//        ((Activity) context).finish();
//    }
//
//
//    public static void moveToWelcomeScreen(Context context) {
//        Intent intent = new Intent(context, WelcomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//        ((Activity) context).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//        ((Activity) context).finish();
//    }

    public static boolean checkLoginStatus(Context context) {
        String status = DatabaseHelper.getAccountDetails(context, Params.AUTH_TOKEN);
        return !status.equals(Params.NOT_FOUND);

    }




    public static  String getFormatedAmount(double amount){
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(amount);
    }

    public static String getOnboardingUrl(HashMap<String,String>  keys,String url) {

        HashMap<String, String> map = new HashMap<>();
        map.put("device_id", keys.get("device_id"));
        map.put("manufacturer",keys.get("manufacturer"));
        map.put("device_model",keys.get("device_model"));
        map.put("fcm_token",keys.get("fcm_token"));
        map.put("customer_id",keys.get("customer_id"));
        String finalUrl = GlobalDataService.buildQueryUrl(url, map);
       // Log.d(TAG, "getListUrl: " + url);url
        return finalUrl;


    }





//    public static boolean checkNetStatusResult(final Activity activity) {
//        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (Objects.requireNonNull(cm).getActiveNetworkInfo() == null) {
//            return false;
//        }
//        return true;
//    }


    public static String getDateTime(String timestamp) throws ParseException {
        //String dateStr = "2014-01-09T09:33:20+0530";
        String dateStr = timestamp;
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date dateObj1 = oldFormater1.parse(dateStr);
        SimpleDateFormat newFormater = new SimpleDateFormat("dd.MM.yyyy hh:mm a");
        return newFormater.format(dateObj1);
    }


    public static String getDateTimeSimple(String timestamp) throws ParseException {
        //String dateStr = "2014-01-09T09:33:20+0530";
        String dateStr = timestamp;
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date dateObj1 = oldFormater1.parse(dateStr);
        SimpleDateFormat newFormater =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return newFormater.format(dateObj1);
    }
    public static String getDateTimeWithSlashes(String timestamp) throws ParseException {
        //String dateStr = "2014-01-09T09:33:20+0530";
        String dateStr = timestamp;
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date dateObj1 = oldFormater1.parse(dateStr);
        SimpleDateFormat newFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        return newFormater.format(dateObj1);
    }

    public static String getDateTimeForOrder(String timestamp) throws ParseException {
        //String dateStr = "2014-01-09T09:33:20+0530";


        String dateStr = timestamp;
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date dateObj1 = oldFormater1.parse(dateStr);
        SimpleDateFormat newFormater = new SimpleDateFormat("dd.MM.yy | hh:mm a");
        return newFormater.format(dateObj1);
    }


    //stepper date time

    public static String getDateTimeForStepper(String timestamp) throws ParseException {
        //String dateStr = "2014-01-09T09:33:20+0530";
//        String dateStr = timestamp;
//        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        Date dateObj1 = oldFormater1.parse(dateStr);
//        SimpleDateFormat newFormater = new SimpleDateFormat("dd MMMM yyyy, hh:mm a");
//        return newFormater.format(dateObj1);



        String dateStr = timestamp;
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        oldFormater1.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateObj1 = oldFormater1.parse(dateStr);

        SimpleDateFormat newFormater = new SimpleDateFormat("dd MMMM yyyy, hh:mm a");
        newFormater.setTimeZone(TimeZone.getTimeZone("IST"));
        return newFormater.format(dateObj1);

    }

    public static Date getTimeStampFromDateTime(String timestamp) throws ParseException {
        //String dateStr = "2014-01-09T09:33:20+0530";
        String dateStr = timestamp;
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date dateObj1 = oldFormater1.parse(dateStr);
        SimpleDateFormat newFormater = new SimpleDateFormat("dd.MM.yyyy hh:mm a");
        return dateObj1;
    }


    public static String getStateHashMap(String typeOfUser, String status) {

        HashMap<String, String> hashMap;

        if (typeOfUser.equalsIgnoreCase("deposit")) {
            HashMap<String, String> depositorstatemap = new HashMap<>();
            depositorstatemap.put("matched", "Make Payement");
            depositorstatemap.put("payment_sent", "Awaiting For Approval");
            depositorstatemap.put("auto_canceled", "Auto Cancelled");
            depositorstatemap.put("payment_received_dispute", "Dispute");
            depositorstatemap.put("payment_pending_dispute", "Dispute");
            depositorstatemap.put("payment_received", "Match Completed");
            hashMap = depositorstatemap;
        } else {


            HashMap<String, String> withdraworstatemap = new HashMap<>();
            withdraworstatemap.put("matched", "Awaiting For Payment");
            withdraworstatemap.put("payment_sent", "Approve Payment");
            withdraworstatemap.put("auto_canceled", "Auto Cancelled");
            withdraworstatemap.put("payment_received_dispute", "Dispute");
            withdraworstatemap.put("payment_pending_dispute", "Dispute");
            withdraworstatemap.put("payment_received", "Match Completed");
            hashMap = withdraworstatemap;
        }


        return hashMap.get(status);

    }


    public static String getStateMachineHashMap(String typeOfUser, String status) {

        HashMap<String, String> hashMap;

        if (typeOfUser.equalsIgnoreCase("deposit")) {
            HashMap<String, String> depositorstatemap = new HashMap<>();
            depositorstatemap.put("matched", "Matched With");
            depositorstatemap.put("payment_sent", "Payment Sent");
            depositorstatemap.put("auto_canceled", "Auto Cancelled");
            depositorstatemap.put("payment_received_dispute", "You Have Raised Dispute");
            depositorstatemap.put("payment_pending_dispute", "Withdrawer Have Raised Dispute");
            depositorstatemap.put("payment_received", "Match Completed");
            hashMap = depositorstatemap;
        } else {

            HashMap<String, String> withdraworstatemap = new HashMap<>();
            withdraworstatemap.put("matched", "Matched With");
            withdraworstatemap.put("payment_sent", "Depositor Sent Payment");
            withdraworstatemap.put("auto_canceled", "Auto Cancelled");
            withdraworstatemap.put("payment_received_dispute", "Depositor Have Raised Dispute");
            withdraworstatemap.put("payment_pending_dispute", "You Have Raised Dispute");
            withdraworstatemap.put("payment_received", "Match Completed");
            hashMap = withdraworstatemap;
        }


        return hashMap.get(status);

    }


    public static String getStateDescHashMap(String typeOfUser, String status, String username) {

        HashMap<String, String> hashMap;

        if (typeOfUser.equalsIgnoreCase("deposit")) {
            HashMap<String, String> depositorstatemap = new HashMap<>();
            depositorstatemap.put("matched", "");
            depositorstatemap.put("payment_sent", "You have successfully recorded payment for this transaction, please wait for approval from " + username + " or you can raise dispute.");
            depositorstatemap.put("auto_canceled", "");
            depositorstatemap.put("payment_received_dispute", "You Have Raised Dispute stating the withdrawer is not marking payment received from their end");
            depositorstatemap.put("payment_pending_dispute", "Withdrawer Have Raised Dispute stating you have not sent the payment and recorded stating you have done");
            depositorstatemap.put("payment_received", "");
            hashMap = depositorstatemap;
        } else {

            HashMap<String, String> withdraworstatemap = new HashMap<>();
            withdraworstatemap.put("matched", "");
            withdraworstatemap.put("payment_sent", "Depositor successfully recorded payment for this transaction, please confirm that you have received or you can raise dispute");
            withdraworstatemap.put("auto_canceled", "");
            withdraworstatemap.put("payment_received_dispute", "Depositor Have Raised Dispute stating the withdrawer is not marking payment received from their end");
            withdraworstatemap.put("payment_pending_dispute", "You Have Raised Dispute stating Depositor have not sent the payment and recorded stating you have done");
            withdraworstatemap.put("payment_received", "");
            hashMap = withdraworstatemap;
        }


        return hashMap.get(status);

    }


    public static String getStateNewExtraHashMap(String typeOfUser, String status) {

        HashMap<String, String> hashMap;

        if (typeOfUser.equalsIgnoreCase("deposit")) {
            HashMap<String, String> depositorstatemap = new HashMap<>();
            depositorstatemap.put("stage2", "SEND PAYMENT");
            depositorstatemap.put("stage3", "WAITING FOR APPROVAL");

            hashMap = depositorstatemap;
        } else {

            HashMap<String, String> withdraworstatemap = new HashMap<>();
            withdraworstatemap.put("stage2", "WAITING FOR PAYMENT");
            withdraworstatemap.put("stage3", "PENDING CONFIRMATION");
            hashMap = withdraworstatemap;

        }


        return hashMap.get(status);

    }

    public static String getStateNewExtraDescHashMap(String typeOfUser, String status) {

        HashMap<String, String> hashMap;

        if (typeOfUser.equalsIgnoreCase("deposit")) {
            HashMap<String, String> depositorstatemap = new HashMap<>();
            depositorstatemap.put("stage2", "Send payment of provided bank details");
            depositorstatemap.put("stage3", "");

            hashMap = depositorstatemap;
        } else {

            HashMap<String, String> withdraworstatemap = new HashMap<>();
            withdraworstatemap.put("stage2", "Depositor is making payment");
            withdraworstatemap.put("stage3", "");
            hashMap = withdraworstatemap;

        }


        return hashMap.get(status);

    }


    public static String getDateTimeFromEpoc(long time) {
//        long unix_seconds = 1372339860;
        //convert seconds to milliseconds
//        Date date = new Date(unix_seconds*1000L);
        Date date = new Date(time);
        // format of the date
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat jdf = new SimpleDateFormat("dd.MM.yy | HH:mm:ss");
//        jdf.setTimeZone(TimeZone.getTimeZone("GMT+530"));
        return jdf.format(date);
    }


    public static String getTimeFromEpoc(long time) {
//        long unix_seconds = 1372339860;
        //convert seconds to milliseconds
//        Date date = new Date(unix_seconds*1000L);
        Date date = new Date(time);
        // format of the date
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat jdf = new SimpleDateFormat("HH:mm:ss");
//        jdf.setTimeZone(TimeZone.getTimeZone("GMT+530"));
        return jdf.format(date);
    }


//
//    public static  String getDateFromUTCTimestamp(long mTimestamp) {
//        Date dateObj = new Date(mTimestamp);
//        java.text.DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm a");
//        return  df.format(dateObj);
//    }


    public static String getDeviceId(Context context) {
        try {
            @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            return androidId;
        } catch (Exception e) {
            return "Android_Device_" + UUID.randomUUID().toString();
        }
    }


    public static Double getDecimalValueDouble(String value, String locale) {
        Double doubleValue = Double.parseDouble(value);
        String stringValue = String.format(locale, doubleValue);
        return Double.parseDouble(stringValue);
    }

    public static String getDecimalValueString(String value, String locale) throws ParseException {

        DecimalFormat df = new DecimalFormat("0.00");
        String formate = df.format(value);
        double finalValue = (Double) df.parse(formate);

        String stringValue = String.format(locale, finalValue);
        return stringValue;
    }






    public static String getLocale(String decimalValue) {

        Log.d("GLOBAL", "getLocale: " + decimalValue);
        return "%." + decimalValue + "f";
    }


//    public static void unregisterBus(Activity context) {
//        try {
//            if (EventBus.getDefault().isRegistered(context)) {
//                EventBus.getDefault().unregister(context);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

//    public static void registerBus(Activity context) {
//        try {
//            if (!EventBus.getDefault().isRegistered(context)) {
//                EventBus.getDefault().register(context);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

//    public static void unregisterBus(Fragment context) {
//        try {
//            if (EventBus.getDefault().isRegistered(context)) {
//                EventBus.getDefault().unregister(context);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static void registerBus(Fragment context) {
//        try {
//            if (!EventBus.getDefault().isRegistered(context)) {
//                EventBus.getDefault().register(context);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }


    public static String buildQueryUrl(String baseURL, HashMap<String, String> query) {
        Uri.Builder builder = Uri.parse(baseURL)
                .buildUpon();

        for (Map.Entry<String, String> item : query.entrySet()) {

            builder.appendQueryParameter(item.getKey(), item.getValue());
//                    .appendQueryParameter(FORMAT_PARAM, "json")
//                    .appendQueryParameter(UNITS_PARAM, "metric")
//                    .appendQueryParameter(DAYS_PARAM, Integer.toString(7))
//                    .build();
        }


        Log.d("URLBUILDER", "buildQueryUrl: " + builder.build().toString());
        return builder.build().toString();


    }


    public static void copyToClipBoard(Activity activity, String data) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copy", data);
        clipboard.setPrimaryClip(clip);
        GlobalDataService.showToasty(activity, "copied", Params.TOASTY_INFO);
    }


    // below date functions are used for the state machine

    public static String getISTFromUTC(String timeStamp) throws ParseException {
        String dateStr = timeStamp;
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        oldFormater1.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateObj1 = oldFormater1.parse(dateStr);


        SimpleDateFormat newFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // Log.d("Timer", "getCreatedTime: "+newFormater.format(dateObj1));
        return newFormater.format(dateObj1);
    }


    public static String getTimeStampISTFromUTCEpoc(long epoch) throws ParseException {
        // converting the epoch to datestring in UTC
        Date date = new Date(epoch);
        DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        newFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = newFormat.format(date);


        // converting the datestring to date object in UTC
        String dateStr = formatted;
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        oldFormater1.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateObj1 = oldFormater1.parse(dateStr);


        // converting the date object to datetring in IST
        SimpleDateFormat newFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // Log.d("Timer", "getServerTime: "+newFormater.format(dateObj1));
        return newFormater.format(dateObj1);

    }

    public static String getTimeStampISTFromISTEpoc(long epoch) throws ParseException {


        Date date = new Date(epoch);
        DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        newFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String formatted = newFormat.format(date);
        return formatted;

    }



    public static boolean giveExpiredStatus(String createdTime,long epoch,int expiryTime)
    {

        try {


            String servertime = getTimeStampISTFromUTCEpoc(epoch*1000L);
            String expiryTimeStamp = addMinutes(getDateTimeSimple(createdTime),expiryTime);
            Log.d("EXPIRED", "giveExpiredStatus: "+servertime + " "+expiryTimeStamp);
            if(isExpired(servertime,expiryTimeStamp))
            {
                return true;
            }else
            {
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }


    public static boolean isExpired(String serverTimeStamp, String EpiryTimeStamp) throws ParseException {

        if (GlobalDataService.isGreater(serverTimeStamp, EpiryTimeStamp)) {
            return true;
        } else {
            return false;
        }
    }


    public static long timeLeftInMilliSec(String startDateString, String endDateString) throws ParseException {

        long difference = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);

        if (isGreater(startDateString, endDateString)) {
            difference = startDate.getTime() - endDate.getTime();
        } else {
            difference = endDate.getTime() - startDate.getTime();
        }
        return difference;
    }

    public static boolean isNighHours(String serverTimeString, String startDateString, String endDateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);
        Date serverTime = simpleDateFormat.parse(serverTimeString);
        Log.d("Global", "isNighHours: " + startDate + " " + endDate + " " + serverTime);
        if (serverTime.compareTo(startDate) >= 0 && serverTime.compareTo(endDate) <= 0) {
            return true;
        }

        return false;
    }


    public static String addMinutes(String timestamp, long minutes) throws ParseException {

        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateObj1 = oldFormater1.parse(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateObj1);
        cal.add(Calendar.MINUTE, (int) minutes);
        String newTime = oldFormater1.format(cal.getTime());
        return newTime;

    }


    public static boolean isGreater(String firstTimeStamp, String SecondTimeStamp) throws ParseException {
        SimpleDateFormat oldFormater1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateObj1 = oldFormater1.parse(firstTimeStamp);
        Date dateObj2 = oldFormater1.parse(SecondTimeStamp);

        if (dateObj1.after(dateObj2)) {
            return true;
        } else {
            return false;
        }

    }


    public static String getBigDecimalValue(String value) {
        BigDecimal bd = new BigDecimal("123234545.4767");
        BigDecimal displayVal = bd.setScale(2, RoundingMode.HALF_EVEN);
        return displayVal.toString();
    }


//    public static void displayBottomSheet(String title, String content, FragmentManager fragmentManager, BottomSheetInterfaceConfirm lisener) {
//        BottomSheetConfirmModal bottomSheetConfirmModal = new BottomSheetConfirmModal(title, content, lisener);
//        bottomSheetConfirmModal.show(fragmentManager, "");
//    }
//
//
//    public static void displayBottomSheet(String title, String content, FragmentManager fragmentManager, BottomSheetInterfaceConfirm lisener, BottomSheetInterfaceCancel cancellisener, String confirmtext, String canceltext) {
//        BottomSheetConfirmModal bottomSheetConfirmModal = new BottomSheetConfirmModal(title, content, lisener, cancellisener, confirmtext, canceltext);
//        bottomSheetConfirmModal.show(fragmentManager, "");
//
//    }
//
//
//    public static void displayBottomSheet(String title, String content, String id, FragmentManager fragmentManager, BottomSheetInterfaceConfirm lisener) {
//        String msg = "Are you sure you want to cancel this open order?";
//        BottomSheetConfirmModal bottomSheetConfirmModal = new BottomSheetConfirmModal(title, content, lisener);
//        bottomSheetConfirmModal.show(fragmentManager, id);
//    }






    public void getScreenSize(Context context){
        String screenResolution = "";
            int size = context.getResources().getConfiguration().screenLayout
                    & Configuration.SCREENLAYOUT_SIZE_MASK;

            switch (size) {
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    // 720x960 dp units
                    screenResolution = "720x960";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    // 480x640 dp units
                    screenResolution = "480x640";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    // 320x470 dp units
                    screenResolution = "320x470";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    // 320x426 dp units
                    screenResolution = "320x426";
                    break;
                default:
                    break;
            }





    }
}
