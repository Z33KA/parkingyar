package com.j.gharibi.parkingyar.view;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.algorithm.Graph_pq;
import com.j.gharibi.parkingyar.algorithm.Node;
//import com.j.gharibi.parkingyar.utility.ApiServices;

import org.json.JSONException;
import org.json.JSONObject;
import org.neshan.common.model.LatLng;
import org.neshan.servicessdk.direction.NeshanDirection;
import org.neshan.servicessdk.direction.model.NeshanDirectionResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        ConstraintLayout constraintLayout = findViewById(R.id.constrain_payAct);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayActivity.this, MapSearchActivity.class);
                startActivity(intent);
                finish();
            }
        });


//        int V = 6;
//        int source = 0;
//        // adjacency list representation of graph
//        List<List<Node>> adj_list = new ArrayList<List<Node>>();
//        // Initialize adjacency list for every node in the graph
//        for (int i = 0; i < V; i++) {
//            List<Node> item = new ArrayList<Node>();
//            adj_list.add(item);
//        }
//
//
//        // Input graph edges
//        adj_list.get(0).add(new Node(1, 5));
//        adj_list.get(0).add(new Node(2, 3));
//        adj_list.get(0).add(new Node(3, 2));
//        adj_list.get(0).add(new Node(4, 3));
//        adj_list.get(0).add(new Node(5, 3));
////        adj_list.get(2).add(new Node(1, 2));
////        adj_list.get(2).add(new Node(3, 3));
//        // call Dijkstra's algo method
//        Graph_pq dpq = new Graph_pq(V);
//        dpq.algo_dijkstra(adj_list, source);
//
////        int[] integers = new int[]{15,55,2,1,8,3,9,11,10,89,92,16};
//        int[] integers = new int[6];
//
//        // Print the shortest path from source node to all the nodes
//        System.out.println("The shorted path from source node to other nodes:");
//        System.out.println("Source\t\t" + "Node#\t\t" + "Distance");
//        for (int i = 0; i < dpq.dist.length; i++) {
//            System.out.println(source + " \t\t " + i + " \t\t " + dpq.dist[i]);
//            integers[i] = dpq.dist[i];
//        }
//
//        for (int i = 0; i < integers.length; i++) {
//            System.out.println(integers[i]);
//        }
//
//        int temp = 0;
//        for (int i = 0; i < integers.length; i++) {
//            for (int j = 0; j < integers.length; j++) {
//                if (integers[i] > integers[j]) {
//                    temp = integers[i];
//                    integers[i] = integers[j];
//                    integers[j] = temp;
//                }
//            }
//        }
//
//        System.out.println("============================");
//
//        for (int i = 0; i < integers.length; i++) {
//            System.out.println(integers[i]);
//        }


//        String requestURL = "https://api.neshan.org/v4/direction/no-traffic?parameters" ;
//
//
//        JSONObject param=new JSONObject();
//        try {
//            param.put("type","car");
//            param.put("origin","35.3091,46.9987");
//            param.put("destination","35.3091,47.0004");
////                param.put("avoidTrafficZone","false");
////                param.put("avoidOddEvenZone","false");
////                param.put("alternative","true");
//            param.put("bearing","180");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        ApiServices apiServices=new ApiServices(this);
//
//        apiServices.sendDataJsonObject(param, requestURL, new ApiServices.OnGetResponseData() {
//            @Override
//            public void onGetData(JSONObject responseData) {
//                Log.i(TAG, "onGetData: "+responseData.toString());
//            }
//        });


//        String urll="https://api.neshan.org/v2/direction?origin=32.12254%2C52.365644&destination=32.13254%2C52.364644&avoidTrafficZone=false&avoidOddEvenZone=true&alternative=true}";
//        String requestURL = "https://api.neshan.org/v4/direction?parameters" ;
//        String requestURL = "https://api.neshan.org/v4/direction/no-traffic?parameters" ;
//        String requestURL = "https://api.neshan.org/v4/direction/no-traffic?parameters" ;


//        String requestURL = "https://api.neshan.org/v2/direction" ;
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        StringRequest directionRequestString = new StringRequest(
//                Request.Method.GET,
//                requestURL,
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, "onResponse: "+response);
//
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Log.i(TAG, "onResponse: error = "+error.getLocalizedMessage());
//                Log.i(TAG, "onResponse: error = "+error.networkResponse);
//                Log.i(TAG, "onResponse: error = "+error.getCause());
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String>  params = new HashMap<>();
//                // TODO: replace "YOUR_API_KEY" with your api key
//                params.put("Api-Key", "service.GG0J48526ovLKHiHWWHosM4q248iYygpRJVWPZSA");
////                params.put("Api-Key", "service.pN22n9QRjZIPL2R0AvBiZ7woAgxUiPykVDyB7ELe");
//                return params;
//            }
//
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> param=new HashMap<>();
//                param.put("type","car");
//                param.put("origin","35.3091,46.9987");
//                param.put("destination","35.3091,47.0004");
//                param.put("bearing","180");
//                return param;
//            }
//        };
//        // Add the request to the queue
//        directionRequestString.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(directionRequestString);


//        new NeshanDirection.Builder("service.pN22n9QRjZIPL2R0AvBiZ7woAgxUiPykVDyB7ELe",new LatLng(32.12254,52.365644),new LatLng(32.13254,52.364644))
//                .setAlternative(true)
//                .setAvoidOddEvenZone(true)
//                .build()
//                .call(new Callback<NeshanDirectionResult>() {
//                    @Override
//                    public void onResponse(Call<NeshanDirectionResult> call, Response<NeshanDirectionResult> response) {
//                        Log.d(TAG,response.body().toString());
//                        Log.i(TAG, "onResponse: "+response.toString());
//                        Log.i(TAG, "onResponse: "+response.body().toString());
//                        Log.i(TAG, "onResponse: "+response.body().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<NeshanDirectionResult> call, Throwable t) {
//                        Log.d(TAG,"Error");
//                        Log.i(TAG, "onResponse: error ="+t.toString());
//                        Log.i(TAG, "onResponse: error ="+t.getMessage());
//                    }
//                });

    }

}