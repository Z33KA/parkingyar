//package com.j.gharibi.parkingyar.utility;
//
//import android.content.Context;
//import android.os.Build;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NetworkError;
//import com.android.volley.NoConnectionError;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.ServerError;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONObject;
//
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ApiServices {
//    Context context;
//    static String TAG = "tag";
//
//    public static final String HOST_NAME = "https://api.neshan.org/v1/distance-matrix";
//
//    public ApiServices(Context context) {
//        this.context = context;
//    }
//
//    public void sendDataJsonObject( @Nullable JSONObject jsonObject,String severName, @Nullable final OnGetResponseData onGetResponseData) {
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, severName, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                onGetResponseData.onGetData(response);
//                Log.i(TAG, "onResponse: "+response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String message = null;
////                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "onErrorResponse: " + error.networkResponse.statusCode);
//                Log.i(TAG, "onErrorResponse: " + error.getMessage());
//
//                if (error instanceof NetworkError) {
////                    message = "Cannot connect to Internet...Please check your connection!";
//                    message = "برقراری ارتباط با اینترنت مقدور نمیباشد، لطفا ارتباط اینترنت را چک کنید!";
////                    Toast.makeText(context, context.getString(R.string.connectivity_internet_disconnected), Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                } else if (error instanceof ServerError) {
//                    message = "The server could not be found. Please try again after some time!!";
////                    Toast.makeText(context, context.getString(R.string.connectivity_internet_lower), Toast.LENGTH_LONG).show();
////                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                } else if (error instanceof AuthFailureError) {
////                    message = "Cannot connect to Internet...Please check your connection!";
//                    message = "برقراری ارتباط با اینترنت مقدور نمیباشد، لطفا ارتباط اینترنت را چک کنید!";
////                    Toast.makeText(context, context.getString(R.string.connectivity_internet_lower), Toast.LENGTH_LONG).show();
////                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                } else if (error instanceof ParseError) {
//                    error.printStackTrace();
//                    message = "Parsing error! Please try again after some time!!";
////                    Toast.makeText(context, context.getString(R.string.connectivity_internet_lower), Toast.LENGTH_LONG).show();
////                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                } else if (error instanceof NoConnectionError) {
////                    message = "Cannot connect to Internet...Please check your connection!";
//                    message = "برقراری ارتباط با اینترنت مقدور نمیباشد، لطفا ارتباط اینترنت را چک کنید!";
////                    Toast.makeText(context, context.getString(R.string.connectivity_internet_lower), Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                } else if (error instanceof TimeoutError) {
////                    message = "Connection TimeOut! Please check your internet connection.";
////                    Toast.makeText(context, context.getString(R.string.connectivity_internet_lower), Toast.LENGTH_LONG).show();
////                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                }
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> header = new HashMap<>();
//                header.put("Api-Key", "service.pN22n9QRjZIPL2R0AvBiZ7woAgxUiPykVDyB7ELe");
////                header.put("Content-Type", "application/json; charset=utf-8");
////                header.put("Content-Type", URLEncoder.encode("application/json;charset=UTF-8", "UTF-8"));
//                return header;
//            }
//        };
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(context).add(jsonObjectRequest);
//    }
//
//
//    public interface OnGetResponseData {
//        void onGetData(JSONObject responseData);
//    }
//}
