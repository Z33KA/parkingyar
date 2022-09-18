package com.j.gharibi.parkingyar.adaptor;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.dataModule.Dm_ReserveParking;
import com.j.gharibi.parkingyar.utility.CustomAlertDialog;
import com.j.gharibi.parkingyar.utility.DB_OpenHelper;

import java.util.List;


public class Ad_StateParking extends RecyclerView.Adapter<Ad_StateParking.MyViewHolder> {


    private final Context context;
    private int parkingId;
    private int currentFloor;
    private int selectedState;
    private CustomAlertDialog.OnGetStatePark onGetStatePark;
    private int states = 0;
    private int blogStates = 12;
    private int thisState = 0;
    AlertDialog alertDialog;
    boolean result, value;
    DB_OpenHelper db_openHelper;
    List<Dm_ReserveParking> dm_reserveParkings;

    public Ad_StateParking(Context context, int ParkingId, int currentFloor, int selectedState, CustomAlertDialog.OnGetStatePark onGetStatePark) {
        this.context = context;
        parkingId = ParkingId;
        this.currentFloor = currentFloor;
        this.selectedState = selectedState;
        this.onGetStatePark = onGetStatePark;

        db_openHelper = new DB_OpenHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_layout_reserve_parking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        dm_reserveParkings = db_openHelper.setReservedParking(parkingId);

        if (currentFloor == 1) {
            if (position == 0)
                states = 12;
            else if (position == 1)
                states = 24;
            else if (position == 2)
                states = 36;
        } else if (currentFloor == 2) {
            if (position == 0)
                states = 48;
            else if (position == 1)
                states = 60;
            else if (position == 2)
                states = 72;
        } else if (currentFloor == 3) {
            if (position == 0)
                states = 84;
            else if (position == 1)
                states = 96;
            else if (position == 2)
                states = 108;
        } else if (currentFloor == 4) {
            if (position == 0)
                states = 120;
            else if (position == 1)
                states = 132;
            else if (position == 2)
                states = 144;
        }

        Log.i(TAG, "onBindViewHolder: state = " + states + " position = " + position);

        thisState = states - (blogStates - 1);

        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState1.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState1.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 2);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState2.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState2.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 3);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState3.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState3.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 4);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState4.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState4.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 5);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState5.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState5.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 6);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState6.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState6.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 7);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState7.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState7.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 8);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState8.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState8.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 9);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState9.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState9.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 10);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState10.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState10.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 11);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState11.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState11.setText(String.valueOf(thisState));

        thisState = states - (blogStates - 12);
        if (isReservedState(String.valueOf(thisState)))
            holder.buttonState12.setBackgroundColor(context.getResources().getColor(R.color.Grey200));
        holder.buttonState12.setText(String.valueOf(thisState));




        holder.buttonState1.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState1.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState1.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState2.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState2.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState2.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState3.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState3.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState3.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState4.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState4.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState4.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState5.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState5.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState5.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState6.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState6.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState6.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState7.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState7.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState7.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState8.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState8.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState8.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState9.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState9.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState9.getText().toString()), value);
            else
                callAlert();
        });
        holder.buttonState10.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState10.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState10.getText().toString()), value);
            else
                callAlert();

        });
        holder.buttonState11.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState11.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState11.getText().toString()), value);
            else
                callAlert();
        });

        holder.buttonState12.setOnClickListener(view -> {
            if (!isReservedState(holder.buttonState12.getText().toString()))
                onGetStatePark.getState(Integer.parseInt(holder.buttonState12.getText().toString()), value);
            else
                callAlert();
        });


    }

    private boolean isReservedState(String selectedState) {
        boolean exist = false;
        if (dm_reserveParkings.size() > 0) {
            for (int i = 0; i < dm_reserveParkings.size(); i++) {
                if (dm_reserveParkings.get(i).getParkingId() == parkingId)
                    if (dm_reserveParkings.get(i).getNumberState() == Integer.parseInt(selectedState))
                        exist = true;
            }
        }
        return exist;
    }

    private void callAlert() {
        alertDialog = CustomAlertDialog.getAlertDialogOk(context, "انتخاب جای پارک", "جایگاه انتخاب شده قبلا رزرو شده است!", "باشه", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        }, true);
        alertDialog.show();

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        Button buttonState1, buttonState2, buttonState3, buttonState4, buttonState5, buttonState6, buttonState7, buttonState8, buttonState9, buttonState10, buttonState11, buttonState12;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonState1 = itemView.findViewById(R.id.btn_state1_rvState);
            buttonState2 = itemView.findViewById(R.id.btn_state2_rvState);
            buttonState3 = itemView.findViewById(R.id.btn_state3_rvState);
            buttonState4 = itemView.findViewById(R.id.btn_state4_rvState);
            buttonState5 = itemView.findViewById(R.id.btn_state5_rvState);
            buttonState6 = itemView.findViewById(R.id.btn_state6_rvState);
            buttonState7 = itemView.findViewById(R.id.btn_state7_rvState);
            buttonState8 = itemView.findViewById(R.id.btn_state8_rvState);
            buttonState9 = itemView.findViewById(R.id.btn_state9_rvState);
            buttonState10 = itemView.findViewById(R.id.btn_state10_rvState);
            buttonState11 = itemView.findViewById(R.id.btn_state11_rvState);
            buttonState12 = itemView.findViewById(R.id.btn_state12_rvState);
            constraintLayout = itemView.findViewById(R.id.constraintLayout_rvState);
        }
    }
}
