package com.assignment.moneyexpensemanager.Util;

import android.os.Build;

public class Params {

    public static final String DEPOST_TYPE = "deposit";
    public static final String WITHDRAW_TYPE = "withdrawal";
    public static final String WALLET_ACTION_TYPE = "type";
    public static final String TAG = "++JPR++";
    public static final String USER_ID = "USER_ID";
    public static final String LAST_NEWS_ID = "LAST_NEWS_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String VERIFIED_USER = "VERIFIED_USER";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String LOOP_USER_NAME = "LOOP_USER_NAME";
    public static final String FIRST_TIME_APP = "FIRST_TIME_APP";
    public static final String USER_PHONE = "USER_PHONE";
    public static final String USER_BLOCKED = "USER_BLOCKED";
    public static final String USER_COUNTRY_CODE = "USER_C_CODE";
    public static final String USER_BASE_NEWS = "USER_BASE_NEWS";
    public static final String USER_NEWS_CATEGORY = "USER_NEWS_CATEGORY";
    public static final String USER_BASE_NEWS_FLAG = "USER_BASE_NEWS_FLAG";
    public static final String USER_NEWS_CATEGORY_FLAG = "USER_NEWS_CATEGORY_FLAG";
    public static final String DB_NEWS = "DB_NEWS";
    public static final int CAMERA_REQUEST_EDIT_PROFILE = 111;
    public static final int GALLERY_PICTURE_EDIT_PROFILE = 112;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 117;

    public static final String MANUFACTURER = Build.MANUFACTURER.toLowerCase();
    public static final String DEVICE_MODEL = Build.MODEL.toLowerCase();



    public static final String AUTH_TOKEN = "AUTH_TOKEN";
    public static final String NOT_FOUND = "NOTFOUND";
    public static final String GUPT = "KOINEXCHANGE";




    public static final String TOASTY_SUCCESS = "success";
    public static final String TOASTY_WARN = "warning";
    public static final String TOASTY_ERROR = "error";
    public static final String TOASTY_INFO = "info";
    public static final String LOCALE_4 = "%.4f";
    public static final String LOCALE_8 = "%.8f";
    public static int MIN_GRAPH_VALUE = 0;

    public static final String ReqStatusCompleted = "completed";
    public static final String ReqStatusPending = "pending";
    public static final String ReqStatusCanceled = "canceled";
    public static final String USER_GIFT = "user_gift";
    public static boolean TICKER_DATA_FLAG = false;
    public static boolean BUY_SELL_CONFIRM_FLAG = false;
    public static boolean SOUND_SWITCHOFF_FLAG = false;
    public static String GLOBAL_FREEZE = "global_freeze";
    public static String FORCE_UPDATE = "FORCE_UPDATE";
    public static String MAINTENANCE = "MAINTENANCE";


    private static String getUserAgent() {
        try {
            return Build.MANUFACTURER.toLowerCase() + " " + Build.MODEL.toLowerCase();
        } catch (Exception e) {
            return "Android Device";
        }
    }


}
