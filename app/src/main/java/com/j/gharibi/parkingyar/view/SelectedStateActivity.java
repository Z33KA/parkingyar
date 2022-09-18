package com.j.gharibi.parkingyar.view;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.adaptor.Ad_StateParking;
import com.j.gharibi.parkingyar.utility.CustomAlertDialog;
import com.j.gharibi.parkingyar.utility.SharedClass;

public class SelectedStateActivity extends AppCompatActivity {


    private int currentFloor, selectedState, selectNowState = 0,intParkingId;
    private String parkingType;

    TextView textViewSelectedState;
    RecyclerView recyclerView;
    Button buttonOk, buttonCancel;
    SharedClass sharedClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_alert_dialog_select_state_omoomi);

        setupInitializeView();
        setupControlView();
    }

    private void setupControlView() {
        setFontSizeApp();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            parkingType = bundle.getString("ParkingType");
            selectedState = bundle.getInt("SelectedState");
            currentFloor = bundle.getInt("CurrentFloor");
            intParkingId = bundle.getInt("ParkingId");

            textViewSelectedState.setText(String.valueOf(selectedState));
        }

        Ad_StateParking ad_stateParking = new Ad_StateParking(this,intParkingId, currentFloor, selectedState, new CustomAlertDialog.OnGetStatePark() {
            @Override
            public void getState(int state, boolean result) {
                textViewSelectedState.setText(String.valueOf(state));
                selectNowState = state;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(ad_stateParking);

        buttonOk.setOnClickListener(view1 -> {

            Log.i(TAG, "setupControlView: "+selectNowState);
            Intent intent = new Intent(this, SelectParkingActivity.class);
            intent.putExtra("selectedState", selectNowState);
            intent.putExtra("ParkingType", parkingType);
            intent.putExtra("ParkingId", intParkingId);
            startActivity(intent);
            finish();

        });

        buttonCancel.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, SelectParkingActivity.class);
            intent.putExtra("selectedState", 0);
            intent.putExtra("ParkingType", parkingType);
            intent.putExtra("ParkingId", intParkingId);
            startActivity(intent);
            finish();
        });
    }

    private void setupInitializeView() {
        recyclerView = findViewById(R.id.rv_selectStateCustomAlertDialog);
        textViewSelectedState = findViewById(R.id.txtView_selected_state_selectStateCustomAlertDialog);
        buttonOk = findViewById(R.id.btn_ok_selectStateCustomAlertDialog);
        buttonCancel = findViewById(R.id.btn_cancel_selectStateCustomAlertDialog);

        sharedClass=new SharedClass(this);
    }

    private void setFontSizeApp() {
        textViewSelectedState .setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonOk.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonCancel .setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
    }
}