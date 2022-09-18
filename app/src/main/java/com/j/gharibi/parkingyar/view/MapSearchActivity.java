package com.j.gharibi.parkingyar.view;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carto.BuildConfig;
import com.carto.graphics.Bitmap;
import com.carto.graphics.Color;
import com.carto.styles.AnimationStyle;
import com.carto.styles.AnimationStyleBuilder;
import com.carto.styles.AnimationType;
import com.carto.styles.LineStyle;
import com.carto.styles.LineStyleBuilder;
import com.carto.styles.MarkerStyle;
import com.carto.styles.MarkerStyleBuilder;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.adaptor.Ad_DisplayParking;
import com.j.gharibi.parkingyar.algorithm.Graph_pq;
import com.j.gharibi.parkingyar.algorithm.Node;
import com.j.gharibi.parkingyar.dataModule.Dm_InfoParking;
import com.j.gharibi.parkingyar.dataModule.Dm_ReserveParking;
import com.j.gharibi.parkingyar.utility.CustomAlertDialog;
import com.j.gharibi.parkingyar.utility.DB_OpenHelper;
import com.j.gharibi.parkingyar.utility.SharedClass;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.neshan.common.model.LatLng;
import org.neshan.common.utils.PolylineEncoding;
import org.neshan.mapsdk.MapView;
import org.neshan.mapsdk.internal.utils.BitmapUtils;
import org.neshan.mapsdk.model.Marker;
import org.neshan.mapsdk.model.Polyline;
import org.neshan.servicessdk.direction.model.Route;
//import org.neshan.servicessdk.search.model.Location;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapSearchActivity extends AppCompatActivity {

    ImageView imageViewSelectedHashieei, imageViewSelectedOmoomi, imageViewHashieei, imageViewOmoomi;
    MapView mapView;
    EditText editText;
    ImageView imageViewSearch, imageViewCurentLocation;
    AnimationStyle animationStyleMarker;
//    TextView textViewParking;
    TextView textViewDistance;
//    Button buttonSelectParking;
    SharedClass sharedClass;

    private Location userLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private String lastUpdateTime;
    private Boolean mRequestingLocationUpdates;
    private Marker markerCurrentLocation, markerParkingOmoomi, markerParkingHashieei, marker1, marker2;
    boolean flagImageSelectedHasieei = false, flagSelectedOmoomi = false;

    LatLng latLngUserCurrentLocation, latLngDestinationLocation;

    final int REQUEST_CODE = 123;
    // location updates interval - 1 sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // fastest updates interval - 1 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    DB_OpenHelper db_openHelper;
    List<Dm_InfoParking> infoAllParkings;
    List<Dm_InfoParking> dm_infoParkingSpecial;

    RecyclerView recyclerView;
    Ad_DisplayParking ad_displayParking;
    // constrainLayout in bottom sheet layout
    ConstraintLayout layout_BSL_ParkingType, layout_BSL_Reserved_State;
    BottomSheetBehavior bottomSheetBehaviorParkingType, bottomSheetBehaviorReservedState;
    AlertDialog alertDialog;
    int parkingId = 0;

    //    there are view bottom_sheet_reserved_parking
    TextView textViewParkingNameReserved, textViewNumStateReserved, textViewNumReserved;
    ImageView imageView15Min, imageView30Min, imageView60Min;
    Button buttonNavigation, buttonLock, buttonOkUpTime;
    List<Dm_ReserveParking> dm_reserveParkings;
    boolean upTimeReserve = false, lockState = false;
    Polyline polyline;

    List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map_search);

    }

    private void setupControlView() {
        setFontSizeApp();

        imageViewCurentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusOnUserLocation();
            }
        });


        mapView.setOnMapClickListener(latLng -> {
            if (bottomSheetBehaviorParkingType.getState() == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehaviorParkingType.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (bottomSheetBehaviorReservedState.getState() == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehaviorReservedState.setState(BottomSheetBehavior.STATE_COLLAPSED);

//            Bitmap currentMarker = com.carto.utils.BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.current_location));
//
//            if (markerCurrentLocation != null) {
//                mapView.removeMarker(markerCurrentLocation);
//                markerCurrentLocation = createMarker(latLng, currentMarker);
//                mapView.addMarker(markerCurrentLocation);
//            } else {
//                markerCurrentLocation = createMarker(latLng, currentMarker);
//                mapView.addMarker(markerCurrentLocation);
//            }
        });


        mapView.setOnMarkerClickListener(marker -> {

            Log.i(TAG, "setupControlView: size=" + infoAllParkings.size());
            for (int i = 0; i < infoAllParkings.size(); i++) {
                if (infoAllParkings.get(i).getLatitude() == marker.getLatLng().getLatitude() && infoAllParkings.get(i).getLongitude() == marker.getLatLng().getLongitude()) {
                    Log.i(TAG, "setupControlView: equal index= " + i + infoAllParkings.get(i).getParkingName());
                    Log.i(TAG, "setupControlView: Id = " + infoAllParkings.get(i).getId());
//                    editText.setText(infoAllParkings.get(i).getParkingName());
//                    textViewParking.setText(infoAllParkings.get(i).getParkingName());
                    editText.setText(infoAllParkings.get(i).getParkingName());
                    parkingId = infoAllParkings.get(i).getId();
                }
            }
        });
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().length() > 0) {
                    Log.i(TAG, "setupControlView: " + parkingId);
                    if (!callSelectParking(editText.getText().toString())){
                        neshanConvertAddressToState(editText.getText().toString());
                    }
                }
            }
        });

//        buttonSelectParking.setOnClickListener(view -> {
//
//            if (textViewParking.getText().toString().length() > 0) {
//                Log.i(TAG, "setupControlView: " + parkingId);
//                callSelectParking(textViewParking.getText().toString());
//            }
//        });
        imageViewHashieei.setOnClickListener(view -> {
            if (flagSelectedOmoomi) {
                flagSelectedOmoomi = false;
                imageViewSelectedOmoomi.setVisibility(View.GONE);
            }
            flagImageSelectedHasieei = true;
            imageViewSelectedHashieei.setVisibility(View.VISIBLE);

            if (bottomSheetBehaviorParkingType.getState() == BottomSheetBehavior.STATE_COLLAPSED && flagImageSelectedHasieei)
                bottomSheetBehaviorParkingType.setState(BottomSheetBehavior.STATE_EXPANDED);
            else if (bottomSheetBehaviorParkingType.getState() == BottomSheetBehavior.STATE_EXPANDED && !flagImageSelectedHasieei)
                bottomSheetBehaviorParkingType.setState(BottomSheetBehavior.STATE_COLLAPSED);

            callAdaptorParking(DB_OpenHelper.HASHIEEI_PARKING_TYPE, null);
        });


        imageViewOmoomi.setOnClickListener(view -> {
            if (flagImageSelectedHasieei) {
                flagImageSelectedHasieei = false;
                imageViewSelectedHashieei.setVisibility(View.GONE);
            }
            flagSelectedOmoomi = true;
            imageViewSelectedOmoomi.setVisibility(View.VISIBLE);

            if (bottomSheetBehaviorParkingType.getState() == BottomSheetBehavior.STATE_COLLAPSED && flagSelectedOmoomi)
                bottomSheetBehaviorParkingType.setState(BottomSheetBehavior.STATE_EXPANDED);
            else if (bottomSheetBehaviorParkingType.getState() == BottomSheetBehavior.STATE_EXPANDED && !flagSelectedOmoomi)
                bottomSheetBehaviorParkingType.setState(BottomSheetBehavior.STATE_COLLAPSED);

            callAdaptorParking(DB_OpenHelper.OMOOMI_PARKING_TYPE, null);
        });

        imageView15Min.setOnClickListener(view -> {
            imageView15Min.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
            imageView30Min.setBackgroundColor(getResources().getColor(R.color.Grey200));
            imageView60Min.setBackgroundColor(getResources().getColor(R.color.Grey200));

            upTimeReserve = true;
        });
        imageView30Min.setOnClickListener(view -> {
            imageView15Min.setBackgroundColor(getResources().getColor(R.color.Grey200));
            imageView30Min.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
            imageView60Min.setBackgroundColor(getResources().getColor(R.color.Grey200));
            upTimeReserve = true;
        });
        imageView60Min.setOnClickListener(view -> {
            imageView15Min.setBackgroundColor(getResources().getColor(R.color.Grey200));
            imageView30Min.setBackgroundColor(getResources().getColor(R.color.Grey200));
            imageView60Min.setBackgroundColor(getResources().getColor(R.color.color_button_selected));
            upTimeReserve = true;
        });
        buttonOkUpTime.setOnClickListener(view -> {

            if (upTimeReserve) {
                alertDialog = CustomAlertDialog.getAlertDialogPayment(this, CustomAlertDialog.DEFAULT_ALERT_FOR_PAYMENT, dm_reserveParkings.get(0), true);
                alertDialog.show();
            } else
                Toast.makeText(this, "لطفا زمان مورد نظر را انتخاب کنید!", Toast.LENGTH_SHORT).show();
        });

        buttonLock.setOnClickListener(view -> {
            if (lockState) {
                Toast.makeText(this, "قفل جایگاه فعال شد!", Toast.LENGTH_SHORT).show();
                lockState = false;
            } else {
                Toast.makeText(this, "قفل جایگاه غیرفعال شد!", Toast.LENGTH_SHORT).show();
                lockState = true;
            }
        });

        buttonNavigation.setOnClickListener(view -> {

            if (latLngUserCurrentLocation != null)
                neshanReverseAPI(latLngUserCurrentLocation, latLngDestinationLocation);
            else
                Toast.makeText(this, "لطفا موقعیت کنونی خود را مشخص کنید!", Toast.LENGTH_SHORT).show();
        });
        setupInitializeLocationParking();
    }

    private void neshanReverseAPI(LatLng originLatLng, LatLng destinationLatLng) {

        String requestURL = "https://api.neshan.org/v4/direction?type=car&origin=" + originLatLng.getLatitude() + "," + originLatLng.getLongitude()
                + "&destination=" + destinationLatLng.getLatitude() + "," + destinationLatLng.getLongitude();
        Log.i(TAG, "neshanConvertAddressToState: "+requestURL);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest reverseGeoSearchRequest = new StringRequest(
                Request.Method.GET,
                requestURL,
                response -> {
                    try {
                        Log.i(TAG, "neshanReverseAPI: " + response.toString());
                        JSONObject obj = new JSONObject(response);

                        JSONArray jsonArrayDistance=obj.getJSONArray("routes");
                        Log.i(TAG, "neshanReverseAPI: "+jsonArrayDistance.length());
                        Log.i(TAG, "neshanReverseAPI: "+jsonArrayDistance);

                        JSONArray jsonObject=jsonArrayDistance.getJSONObject(0).getJSONArray("legs");
                        Log.i(TAG, "neshanReverseAPI1: "+jsonObject.length());
                        Log.i(TAG, "neshanReverseAPI1: "+jsonObject);

                        JSONObject jsonObject1=jsonObject.getJSONObject(0).getJSONObject("distance");
                        Log.i(TAG, "neshanReverseAPI2: "+jsonObject1.length());
                        Log.i(TAG, "neshanReverseAPI2: "+jsonObject1);

                        textViewDistance.setText(("فاصله ").concat(jsonObject1.getString("text")));

                        Route route = new Gson().fromJson(obj.getJSONArray("routes").get(0).toString(), Route.class);
                        route.getLegs().get(0).getDirectionSteps();
                        ArrayList routeOverviewPolylinePoints = new ArrayList<>(PolylineEncoding.decode(route.getOverviewPolyline().getEncodedPolyline()));

                        LineStyleBuilder lineStCr = new LineStyleBuilder();
                        lineStCr.setColor(new Color((short) 2, (short) 119, (short) 189, (short) 190));
                        lineStCr.setWidth(10f);
                        lineStCr.setStretchFactor(0f);

                        Polyline polyline = new Polyline(routeOverviewPolylinePoints, lineStCr.buildStyle());

                        mapView.addPolyline(polyline);

                        mapView.moveCamera(new LatLng(originLatLng.getLatitude(), originLatLng.getLongitude()), .5f);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "neshanReverseAPI: "+e.getMessage());
                    }
                },
                error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                // TODO: replace "YOUR_API_KEY" with your api key
                params.put("Api-Key", "service.785c95fbe8f246b5bfeedeb50cb6d18c");
                return params;
            }
        };

        // Add the request to the queue
        requestQueue.add(reverseGeoSearchRequest);
    }

    private void neshanConvertAddressToState(String address) {

        String requestURL = "https://api.neshan.org/v4/geocoding?address=" + address;

        Log.i(TAG, "neshanConvertAddressToState: "+requestURL);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest reverseGeoSearchRequest = new StringRequest(
                Request.Method.GET,
                requestURL,
                response -> {
                    try {
                        Log.i(TAG, "neshanReverseAPI: " + response.toString());
                        JSONObject jsonObjectResponse=new JSONObject(response);

                        JSONObject jsonObjectLocation=jsonObjectResponse.getJSONObject("location");
                        LatLng latLng=new LatLng(jsonObjectLocation.getDouble("y"),jsonObjectLocation.getDouble("x"));
                        mapView.moveCamera(latLng, 0);
                        mapView.setZoom(13, 0.25f);


//                        JSONObject obj = new JSONObject(response);
//
//                        JSONArray jsonArrayDistance=obj.getJSONArray("routes");
//                        Log.i(TAG, "neshanReverseAPI: "+jsonArrayDistance.length());
//                        Log.i(TAG, "neshanReverseAPI: "+jsonArrayDistance);
//
//                        JSONArray jsonObject=jsonArrayDistance.getJSONObject(0).getJSONArray("legs");
//                        Log.i(TAG, "neshanReverseAPI1: "+jsonObject.length());
//                        Log.i(TAG, "neshanReverseAPI1: "+jsonObject);
//
//                        JSONObject jsonObject1=jsonObject.getJSONObject(0).getJSONObject("distance");
//                        Log.i(TAG, "neshanReverseAPI2: "+jsonObject1.length());
//                        Log.i(TAG, "neshanReverseAPI2: "+jsonObject1);
//
//                        textViewDistance.setText(("فاصله ").concat(jsonObject1.getString("text")));
//
//                        Route route = new Gson().fromJson(obj.getJSONArray("routes").get(0).toString(), Route.class);
//                        route.getLegs().get(0).getDirectionSteps();
//                        ArrayList routeOverviewPolylinePoints = new ArrayList<>(PolylineEncoding.decode(route.getOverviewPolyline().getEncodedPolyline()));
//
//                        LineStyleBuilder lineStCr = new LineStyleBuilder();
//                        lineStCr.setColor(new Color((short) 2, (short) 119, (short) 189, (short) 190));
//                        lineStCr.setWidth(10f);
//                        lineStCr.setStretchFactor(0f);
//
//                        Polyline polyline = new Polyline(routeOverviewPolylinePoints, lineStCr.buildStyle());
//
//                        mapView.addPolyline(polyline);
//
//                        mapView.moveCamera(new LatLng(originLatLng.getLatitude(), originLatLng.getLongitude()), .5f);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "neshanReverseAPI: "+e.getMessage());
                    }
                },
                error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                // TODO: replace "YOUR_API_KEY" with your api key
                params.put("Api-Key", "service.d72422aacd97461c9439a9383d49c5d1");
                return params;
            }
        };

        // Add the request to the queue
        requestQueue.add(reverseGeoSearchRequest);
    }

    private boolean callSelectParking(String parkingName) {
        boolean status=false;
        for (int i = 0; i < infoAllParkings.size(); i++) {

            if (infoAllParkings.get(i).getParkingName().contains(parkingName)) {
                status=true;

                dm_reserveParkings = db_openHelper.setReservedParking(infoAllParkings.get(i).getId());

                callAdaptorParking(i);


                Log.i(TAG, "setupControlView: EDIT " + editText.getText().toString());
                Log.i(TAG, "setupControlView: NAME " + infoAllParkings.get(i).getParkingName());
                Log.i(TAG, "setupControlView: ID " + infoAllParkings.get(i).getId());
                if (infoAllParkings.get(i).getParkingType().equals(DB_OpenHelper.OMOOMI_PARKING_TYPE)) {

                    if (dm_reserveParkings.size() > 0) {
                        if (bottomSheetBehaviorReservedState.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                            bottomSheetBehaviorReservedState.setState(BottomSheetBehavior.STATE_EXPANDED);

                        latLngDestinationLocation = new LatLng(infoAllParkings.get(i).getLatitude(), infoAllParkings.get(i).getLongitude());

                        textViewParkingNameReserved.setText(infoAllParkings.get(i).getParkingName());
                        textViewNumStateReserved.setText("ط ".concat(String.valueOf(dm_reserveParkings.get(0).getFloorParking())).concat(" , ").concat(String.valueOf(dm_reserveParkings.get(0).getNumberState())));
                        textViewNumReserved.setText(dm_reserveParkings.get(0).getTimeReserve());

                        buttonLock.setVisibility(View.GONE);
                    } else {
                        if (bottomSheetBehaviorParkingType.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                            bottomSheetBehaviorParkingType.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }


                    imageViewSelectedOmoomi.setVisibility(View.VISIBLE);
                    imageViewSelectedHashieei.setVisibility(View.GONE);
                    flagImageSelectedHasieei = false;
                    flagSelectedOmoomi = true;
                    Log.i(TAG, "setupControlView: omoomi");
                } else {
                    if (dm_reserveParkings.size() > 0) {
                        if (bottomSheetBehaviorReservedState.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                            bottomSheetBehaviorReservedState.setState(BottomSheetBehavior.STATE_EXPANDED);

                        latLngDestinationLocation = new LatLng(infoAllParkings.get(i).getLatitude(), infoAllParkings.get(i).getLongitude());


                        textViewParkingNameReserved.setText(infoAllParkings.get(i).getParkingName());
                        textViewNumStateReserved.setText(String.valueOf(dm_reserveParkings.get(0).getNumberState()));
                        textViewNumReserved.setText(dm_reserveParkings.get(0).getTimeReserve());

                        buttonLock.setVisibility(View.VISIBLE);
                    } else {
                        if (bottomSheetBehaviorParkingType.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                            bottomSheetBehaviorParkingType.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }

                    imageViewSelectedOmoomi.setVisibility(View.GONE);
                    imageViewSelectedHashieei.setVisibility(View.VISIBLE);
                    flagImageSelectedHasieei = true;
                    flagSelectedOmoomi = false;
                    Log.i(TAG, "setupControlView: hashieei");
                }
            }
        }

        return status;
    }


    private void callAdaptorParking(@Nullable String parkingType, @Nullable Integer parkingId) {
        dm_infoParkingSpecial.clear();
        dm_infoParkingSpecial = db_openHelper.getMyParking();

        for (int i = 0; i < dm_infoParkingSpecial.size(); i++) {
            Log.i(TAG, "callAdaptorParking: " + dm_infoParkingSpecial.get(i).getFreeState());
        }
        if (parkingType.equals(DB_OpenHelper.OMOOMI_PARKING_TYPE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dm_infoParkingSpecial.removeIf(j -> j.getParkingType().equals(DB_OpenHelper.HASHIEEI_PARKING_TYPE));
            }
        } else if (parkingType.equals(DB_OpenHelper.HASHIEEI_PARKING_TYPE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dm_infoParkingSpecial.removeIf(j -> j.getParkingType().equals(DB_OpenHelper.OMOOMI_PARKING_TYPE));
            }
        }


        int V = dm_infoParkingSpecial.size();
        int source = 0;
        // adjacency list representation of graph
        List<List<Node>> adj_list = new ArrayList<List<Node>>();
        // Initialize adjacency list for every node in the graph
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj_list.add(item);
        }


        for (int i = 0; i < V; i++) {
            adj_list.get(0).add(new Node(dm_infoParkingSpecial.get(i).getId(), dm_infoParkingSpecial.get(i).getFreeState()));
        }

        // Input graph edges
//        adj_list.get(0).add(new Node(1, 5));
//        adj_list.get(0).add(new Node(2, 3));
//        adj_list.get(0).add(new Node(3, 2));
//        adj_list.get(0).add(new Node(4, 3));
//        adj_list.get(0).add(new Node(5, 3));
//        adj_list.get(2).add(new Node(1, 2));
//        adj_list.get(2).add(new Node(3, 3));
        // call Dijkstra's algo method
//        Log.i(TAG, "callAdaptorParking: "+adj_list.size()+"  =  "+source);
//        Graph_pq dpq = new Graph_pq(V);
//        dpq.algo_dijkstra(adj_list, source);
//
//        System.out.println("The shorted path from source node to other nodes:");
//        System.out.println("Source\t\t" + "Node#\t\t" + "Distance");
//        for (int i = 0; i < dpq.dist.length; i++) {
//            System.out.println(source + " \t\t " + i + " \t\t " + dpq.dist[i]);
//        }


        ad_displayParking = new Ad_DisplayParking(MapSearchActivity.this, dm_infoParkingSpecial, parkingType);
        recyclerView.setLayoutManager(new LinearLayoutManager(MapSearchActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(ad_displayParking);
    }

    private void callAdaptorParking(int indexList) {
        dm_infoParkingSpecial.clear();
        dm_infoParkingSpecial.add(infoAllParkings.get(indexList));

        ad_displayParking = new Ad_DisplayParking(MapSearchActivity.this, dm_infoParkingSpecial, dm_infoParkingSpecial.get(0).getParkingType());
        recyclerView.setLayoutManager(new LinearLayoutManager(MapSearchActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(ad_displayParking);
    }

    private void setupInitializeLocationParking() {
        if (infoAllParkings != null)
            infoAllParkings.clear();

        infoAllParkings = db_openHelper.getMyParking();

        if (infoAllParkings != null) {
            List<Dm_ReserveParking> dm_reserveParkings = new ArrayList<>();
            boolean exist = false;

            Bitmap bitmapLocationOmoomiParking = com.carto.utils.BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.p_location));
            Bitmap bitmapLocationHashieeiParking = BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.line));

            Bitmap bitmapLocationOmoomiParkingReserved = com.carto.utils.BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.current_location));
            Bitmap bitmapLocationHashieeiParkingReserved = BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.car1));
            for (int i = 0; i < infoAllParkings.size(); i++) {
                Dm_InfoParking dm_infoParking = infoAllParkings.get(i);

                if (dm_reserveParkings != null)
                    dm_reserveParkings.clear();
                dm_reserveParkings = db_openHelper.setReservedParking(infoAllParkings.get(i).getId());


                if (dm_infoParking.getParkingType().equals(DB_OpenHelper.OMOOMI_PARKING_TYPE)) {

                    if (dm_reserveParkings.size() > 0)
                        markerParkingOmoomi = createMarker(new LatLng(dm_infoParking.getLatitude(), dm_infoParking.getLongitude()), bitmapLocationOmoomiParkingReserved);
                    else
                        markerParkingOmoomi = createMarker(new LatLng(dm_infoParking.getLatitude(), dm_infoParking.getLongitude()), bitmapLocationOmoomiParking);

                    markerParkingOmoomi.setTitle(dm_infoParking.getParkingName());
                    markerParkingOmoomi.setDescription(dm_infoParking.getParkingType());
                    mapView.addMarker(markerParkingOmoomi);
                } else if (dm_infoParking.getParkingType().equals(DB_OpenHelper.HASHIEEI_PARKING_TYPE)) {

                    if (dm_reserveParkings.size() > 0)
                        markerParkingHashieei = createMarker(new LatLng(dm_infoParking.getLatitude(), dm_infoParking.getLongitude()), bitmapLocationHashieeiParkingReserved);
                    else
                        markerParkingHashieei = createMarker(new LatLng(dm_infoParking.getLatitude(), dm_infoParking.getLongitude()), bitmapLocationHashieeiParking);

                    markerParkingHashieei.setTitle(dm_infoParking.getParkingName());
                    markerParkingHashieei.setDescription(dm_infoParking.getParkingType());
                    mapView.addMarker(markerParkingHashieei);
                }
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        initializeView();
        setupControlView();

//        getLocationPermission();
        // everything related to ui is initialized here
//        initLayoutReferences();

        initLocation();
        startReceivingLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                userLocation = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                onLocationChange();
            }
        };

        mRequestingLocationUpdates = false;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                        onLocationChange();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapSearchActivity.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(MapSearchActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        onLocationChange();
                    }
                });
    }

    public void stopLocationUpdates() {
        // Removing location updates
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void startReceivingLocationUpdates() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void onLocationChange() {
        if (userLocation != null) {
            if (markerCurrentLocation != null)
                mapView.removeMarker(markerCurrentLocation);

            com.carto.graphics.Bitmap bitmap = BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.click_location));
            markerCurrentLocation = createMarker(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), bitmap);
            mapView.addMarker(markerCurrentLocation);
        }
    }

    public void focusOnUserLocation() {
        if (userLocation != null) {
            latLngUserCurrentLocation = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            mapView.moveCamera(latLngUserCurrentLocation, 0);
            mapView.setZoom(13, 0.25f);
        }
    }

    // This method gets a LatLng as input and adds a marker on that position
    private Marker createMarker(LatLng loc, Bitmap bitmap) {
        // Creating animation for marker. We should use an object of type AnimationStyleBuilder, set
        // all animation features on it and then call buildStyle() method that returns an object of type
        // AnimationStyle
        AnimationStyleBuilder animStBl = new AnimationStyleBuilder();
        animStBl.setFadeAnimationType(AnimationType.ANIMATION_TYPE_SMOOTHSTEP);
        animStBl.setSizeAnimationType(AnimationType.ANIMATION_TYPE_SPRING);
        animStBl.setPhaseInDuration(0.5f);
        animStBl.setPhaseOutDuration(0.5f);
        animationStyleMarker = animStBl.buildStyle();

        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
        markStCr.setSize(30f);
        markStCr.setBitmap(bitmap);
        // AnimationStyle object - that was created before - is used here
        markStCr.setAnimationStyle(animationStyleMarker);
        MarkerStyle markSt = markStCr.buildStyle();

        // Creating marker
        return new Marker(loc, markSt);
    }

    private void initializeView() {
        imageViewSelectedHashieei = findViewById(R.id.imgView_header_selected_parking_hashieei_searchAct);
        imageViewSelectedOmoomi = findViewById(R.id.imgView_header_selected_parking_omomie_searchAct);
        imageViewHashieei = findViewById(R.id.imgView_park_hashieei_searchAct);
        imageViewOmoomi = findViewById(R.id.imgView_park_omoomi_searchAct);
        imageViewCurentLocation = findViewById(R.id.imgBtn_location_searchAct);

//        textViewParking = findViewById(R.id.txtView_parking_searchSearchAct);
        textViewDistance = findViewById(R.id.txtView_distance_searchSearchAct);

//        buttonSelectParking = findViewById(R.id.btn_select_searchAct);

        editText = findViewById(R.id.editText_searchSearchAct);
        imageViewSearch = findViewById(R.id.imgView_search_searchAct);
        layout_BSL_ParkingType = findViewById(R.id.constraintLayout_bottom_sheet);
        bottomSheetBehaviorParkingType = BottomSheetBehavior.from(layout_BSL_ParkingType);

        recyclerView = findViewById(R.id.rv_bottom_sheet_parking_type);
        mapView = findViewById(R.id.map_searchAct);
        db_openHelper = new DB_OpenHelper(this);
        dm_infoParkingSpecial = new ArrayList<>();

        layout_BSL_Reserved_State = findViewById(R.id.constraintLayout_bottomSheetReservedLayout);
        bottomSheetBehaviorReservedState = BottomSheetBehavior.from(layout_BSL_Reserved_State);

        textViewParkingNameReserved = findViewById(R.id.txtView_parking_name_bottomSheetReservedLayout);
        textViewNumStateReserved = findViewById(R.id.txtView_state_bottomSheetReservedLayout);
        textViewNumReserved = findViewById(R.id.txtView_num_reserve_bottomSheetReservedLayout);

        imageView15Min = findViewById(R.id.imgView_15min_bottomSheetReservedLayout);
        imageView30Min = findViewById(R.id.imgView_30min_bottomSheetReservedLayout);
        imageView60Min = findViewById(R.id.imgView_60min_bottomSheetReservedLayout);

        buttonNavigation = findViewById(R.id.btn_navigation_bottomSheetReservedLayout);
        buttonLock = findViewById(R.id.btn_lock_bottomSheetReservedLayout);
        buttonOkUpTime = findViewById(R.id.btn_ok_bottomSheetReservedLayout);

        sharedClass = new SharedClass(this);
    }

    private void setFontSizeApp() {
//        textViewParking.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

//        buttonSelectParking.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

        textViewParkingNameReserved.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewNumStateReserved.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewNumReserved.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

        buttonNavigation.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonLock.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonOkUpTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

    }

    // Drawing line on map
    public Polyline drawLineNavigation(View view) {
        if (polyline != null)
            mapView.removePolyline(polyline);

        // Adding some LatLng points to a latLngs
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(latLngUserCurrentLocation);
        latLngs.add(latLngDestinationLocation);
        // Creating a line from LineGeom. here we use getLineStyle() method to define line styles
        polyline = new Polyline(latLngs, getLineStyle());
        // adding the created line to lineLayer, showing it on map
        mapView.addPolyline(polyline);
        // focusing camera on first point of drawn line
        mapView.moveCamera(latLngUserCurrentLocation, 0.25f);
        mapView.setZoom(14, 0);
        return polyline;
    }

    private LineStyle getLineStyle() {
        LineStyleBuilder lineStCr = new LineStyleBuilder();
        lineStCr.setColor(new Color((short) 2, (short) 119, (short) 189, (short) 190));
        lineStCr.setWidth(7f);
        lineStCr.setStretchFactor(0f);
        return lineStCr.buildStyle();
    }

}