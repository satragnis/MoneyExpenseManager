package com.assignment.moneyexpensemanager.Util;

import android.content.Context;
import android.util.Log;

import com.scottyab.aescrypt.AESCrypt;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.ArrayList;


public class DatabaseHelper implements Serializable {
    private static DB dbh = null;

    private static DB getInstance(Context ctx) {
        try {
            if (dbh == null)
                dbh = DBFactory.open(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbh;
    }

    public static void closeInstance() {
        try {
            if (dbh != null) {
                dbh.close();
                dbh = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public static boolean storeNewsArray(String string, ArrayList<News> newsArrayList, Context context) {
//        try {
//            DB sp =getInstance(context);
//            sp.put(string, newsArrayList);
//            return true;
//        } catch (SnappydbException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public ArrayList<News> getNewsArray(String string,Context context) {
//        ArrayList<News> newsArrayList = new ArrayList();
//
//        try {
//            DB sp = getInstance(context);
//            if (sp.exists(string)) {
//                newsArrayList = sp.getArray(string,News.class);
//            }
//
//        } catch (SnappydbException e) {    e.printStackTrace();}
//        return newsArrayList;
//    }


//    public static boolean storeHashmap(String string, HashMap obj, Context context) {
//        try {
//            DB sp =getInstance(context);
//            sp.put(string, obj);
//
//            return true;
//
//        } catch (SnappydbException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    public static HashMap getHashmap(String string,Context context) {
//        HashMap hashMap = new HashMap();
//
//        try {
//            DB sp = getInstance(context);
//            if (sp.exists(string)) {
//                hashMap= sp.getObject(string,HashMap.class);
//            }
//
//        } catch (SnappydbException e) {    e.printStackTrace();}
//        return hashMap;
//    }


    public boolean storeJsonObject(String string, JSONObject obj, Context context) {
        try {
            DB sp = getInstance(context);
            sp.put(string, obj);

            return true;

        } catch (SnappydbException e) {
            e.printStackTrace();
            return false;
        }


    }

    public JSONObject getJsonObject(String string, Context context) {
        JSONObject jsonObject = new JSONObject();

        try {
            DB sp = getInstance(context);
            if (sp.exists(string)) {
                jsonObject = sp.getObject(string, JSONObject.class);
            }

        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static boolean storeJsonArray(String string, JSONArray jsonArray, Context context) {
        try {
            DB sp = getInstance(context);
            sp.put(string, jsonArray);

            return true;

        } catch (SnappydbException e) {
            e.printStackTrace();
            return false;
        }


    }

    public static JSONArray getJsonArray(String string, Context context) {
        JSONArray jsonArray = new JSONArray();

        try {
            DB sp = getInstance(context);
            if (sp.exists(string)) {
                jsonArray = sp.getObject(string, JSONArray.class);
            }

        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


    public boolean storeArrayList(String string, ArrayList<String> ints, Context context) {
        try {
            DB sp = getInstance(context);
            sp.put(string, ints);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList getArraylist(String string, Context context) {
        ArrayList arrayList = new ArrayList();

        try {
            DB sp = getInstance(context);
            arrayList = sp.getObject(string, ArrayList.class);
            return arrayList;

        } catch (Exception e) {

            return arrayList;

        }

    }










    public static boolean setAccountDetails(Context context, String key, String value) {
        try {
            //DB snappydb = DBFactory.open(context); //create or open an existing database using the default name
            String newValue = AESCrypt.encrypt(Params.GUPT, value);
            DB snappydb = getInstance(context);
            snappydb.put(key, newValue);
            // get array of string
            Log.d("SNAPPYDB", "PUT-> DATA: " + "key: " + key + " value:" + value);
            Log.d("SNAPPYDB", "PUT-> DATA: " + "key: " + key + " newValue:" + newValue);
            return true;
        } catch (GeneralSecurityException e) {
            //handle error
            return false;
        } catch (SnappydbException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static String getAccountDetails(Context context, String key) {

        String data = "NOTFOUND";
        String newData = "NOTFOUND";
        try {
            DB snappydb = getInstance(context);
            //create or open an existing database using the default name
            if (snappydb.exists(key)) {
                data = snappydb.get(key);
                newData = AESCrypt.decrypt(Params.GUPT, data);
            } else {
                return "NOTFOUND";
            }
            Log.d("SNAPPYDB", "getAccountDetails->KEY " + key + " DATA:" + data);
            Log.d("SNAPPYDB", "getAccountDetails->KEY " + key + " newDATA: " + newData);
            //snappydb.close();
        } catch (GeneralSecurityException | SnappydbException e) {
            //handle error
            e.printStackTrace();
            return "NOTFOUND";
        }
        return newData;
    }


    public static void tearDown() {
        try {
            if (dbh != null) {
                dbh.close();
                dbh.destroy();
            }
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        dbh = null;
    }


}
