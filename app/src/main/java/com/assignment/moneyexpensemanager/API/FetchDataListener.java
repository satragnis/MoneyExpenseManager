package com.assignment.moneyexpensemanager.API;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dell on 20/3/18.
 */

public interface FetchDataListener {
    void onFetchComplete(JSONArray data);

    void onFetchFailure(String msg);

    void onFetchStart();
}
