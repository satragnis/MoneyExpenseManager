//package com.assignment.moneyexpensemanager.API;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.NetworkResponse;
//import com.android.volley.NoConnectionError;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.HttpHeaderParser;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.assignment.moneyexpensemanager.Util.GlobalDataService;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Objects;
//
//
//import static com.android.volley.VolleyLog.TAG;
//
//
//public class POSTAPIRequest {
//    public void request(final Context context, final FetchDataListener listener, final JSONObject params, final String ApiURL) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (listener != null) {
//                    //call onFetchComplete of the listener
//                    //to show progress dialog
//                    //-indicates calling started
//                    try{
//                        ((Activity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                listener.onFetchStart();
//                            }
//                        });
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//                //base server URL
//                String baseUrl=ApiUrls.BASE_URL;
//                //add extension api url received from caller
//                //and make full api
//                String url="";
//                if(ApiURL.charAt(0)=='/') {
//                    url = baseUrl + ApiURL;
//                }else{
//                    url = ApiURL;
//
//                }
//                Log.d(TAG, "request: url "+url);
//                Log.d(TAG, "request: parmas "+params.toString());
//                //Requesting with post body as params
//                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, params,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Log.d(TAG, "onResponse: " + response.toString());
//                                try {
//                                    if (listener != null) {
//                                        listener.onFetchComplete(response);
//                                    }
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "onErrorResponse: "+error.toString());
//                        if (error instanceof NoConnectionError) {
//                            if(listener!=null)
//                                listener.onFetchFailure("Network Connectivity Problem");
//                        } else if (error.networkResponse != null && error.networkResponse.data != null) {
//                            if(error.networkResponse.statusCode==401){
//                                GlobalDataService.logout(context);
//                            }
//                            VolleyError volley_error = new VolleyError(new String(error.networkResponse.data));
//                            String errorMessage      = "";
//                            try {
//                                JSONObject errorJson = new JSONObject(volley_error.getMessage());
//                                if(errorJson.has("error")) errorMessage = errorJson.getString("error");
//                                else if(errorJson.has("errors")) errorMessage = errorJson.getString("errors");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            if (errorMessage.isEmpty()) {
//                                errorMessage = volley_error.getMessage();
//                            }
//                            if (listener != null) listener.onFetchFailure(errorMessage);
//                        } else {
//                            Objects.requireNonNull(listener).onFetchFailure("Something went wrong. Please try again later");
//                        }
//                    }
//                })
//                {
////                    @Override
////                    public Map<String, String> getHeaders() {
////                        Map<String, String>  params = new HashMap<String, String>();
////                        params.put("Content-Type", "application/json");
////                        params.put("Accept", "application/json");
////                        params.put("User-Agent", Params.USER_AGENT);
////                        if( !DatabaseHelper.getAccountDetails(context,Params.DEVICE_ID).equalsIgnoreCase("NOTFOUND")) {
////                            params.put("device-id", DatabaseHelper.getAccountDetails(context, Params.DEVICE_ID));
////                        }else{
////                            try {
////                                DatabaseHelper.setAccountDetails(context, Params.DEVICE_ID, GlobalDataService.getDeviceId(context));
////                                Log.d("DEVID", "onCreate: "+DatabaseHelper.getAccountDetails(context,Params.DEVICE_ID));
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }
////                            params.put("device-id", DatabaseHelper.getAccountDetails(context, Params.DEVICE_ID));
////                        }
//////                params.put("device-id", Params.DEVICE_ID);
////                        params.put("device-type", Params.DEVICE_TYPE);
////                        params.put("app-v",BuildConfig.VERSION_NAME);
////                        params.put("new-mobile-v",BuildConfig.VERSION_NAME);
////                        params.put("frontendv", String.valueOf(Params.FRONTEND_V));
////                        String authToken = DatabaseHelper.getAccountDetails(context,Params.AUTH_TOKEN);
////                        if(!authToken.equalsIgnoreCase("NOTFOUND")) {
////                            params.put("Authorization",authToken);
////                        }
////                        Log.d(Params.TAG, "getHeaders: " + params.toString());
////                        return params;
////                    }
//                    @Override
//                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                    try {
//                        String jsonString = new String(response.data,
//                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
//                        if (response.statusCode == 200 || response.statusCode == 201 || response.statusCode ==204) {
//                            if (!jsonString.isEmpty()) {
//                                JSONObject jsonResponse = new JSONObject(jsonString);
//                                Log.d(TAG, "POSTparseNetworkResponse: " + response.statusCode);
//                                Log.d(TAG, "POSTparseNetworkResponse: " + response.headers);
////                    jsonResponse.put("headers", new JSONObject(response.headers));
//                                return Response.success(jsonResponse,
//                                        HttpHeaderParser.parseCacheHeaders(response));
//                            } else {
//                                JSONObject jsonResponse = new JSONObject();
//                                jsonResponse.put("status", "ok");
//                                return Response.success(jsonResponse,
//                                        HttpHeaderParser.parseCacheHeaders(response));
//                            }
//                        } else {
//                            JSONObject jsonResponse = new JSONObject(jsonString);
//                            Log.d(TAG, "POSTparseNetworkResponse: " + response.statusCode);
//                            Log.d(TAG, "POSTparseNetworkResponse: " + response.headers);
////                    jsonResponse.put("headers", new JSONObject(response.headers));
//                            return Response.success(jsonResponse,
//                                    HttpHeaderParser.parseCacheHeaders(response));
//                        }
//                    } catch (UnsupportedEncodingException | JSONException e) {
//                        return Response.error(new ParseError(e));
//                    }
//                }
//                };
//                RequestQueueService.getInstance(context).addToRequestQueue(postRequest.setShouldCache(false));
//            }
//        }).start();
//    }
//}
