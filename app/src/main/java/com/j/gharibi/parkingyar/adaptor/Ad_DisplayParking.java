package com.j.gharibi.parkingyar.adaptor;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.dataModule.Dm_InfoParking;
import com.j.gharibi.parkingyar.utility.CustomAlertDialog;
import com.j.gharibi.parkingyar.utility.DB_OpenHelper;
import com.j.gharibi.parkingyar.utility.SharedClass;
import com.j.gharibi.parkingyar.view.SelectParkingActivity;

import java.util.List;


public class Ad_DisplayParking extends RecyclerView.Adapter<Ad_DisplayParking.MyViewHolder> {


    private final Context context;
    private List<Dm_InfoParking> dm_infoParkings;
    private String parkingType;
    SharedClass sharedClass;
    AlertDialog alertDialog;

    public Ad_DisplayParking(Context context, List<Dm_InfoParking> dm_infoParkings, String parkingType) {
        this.context = context;
        this.dm_infoParkings = dm_infoParkings;
        this.parkingType = parkingType;
        sharedClass = new SharedClass(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_layout_parking, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.setIsRecyclable(false);
        Dm_InfoParking dm_infoParking = dm_infoParkings.get(holder.getAdapterPosition());

        holder.imageViewParking.setImageResource(context.getResources().getIdentifier(dm_infoParking.getNameParkingImage(), "drawable", context.getPackageName()));

//        if (dm_infoParking.getParkingTypeOmoomi() != null && dm_infoParking.getParkingTypeOmoomi().length() > 5)
//            holder.textViewName.setText(dm_infoParking.getParkingName().concat(System.getProperty("line.separator")).concat(dm_infoParking.getParkingTypeOmoomi()));
//        else
        holder.textViewName.setText(dm_infoParking.getParkingName());

        holder.textViewTime.setText(dm_infoParking.getTimeWork());
        holder.textViewFloor.setText(String.valueOf(dm_infoParking.getFloors()));
        holder.textViewTotalState.setText(String.valueOf(dm_infoParking.getTotalQuantityState()));
        holder.textViewFreeState.setText(String.valueOf(dm_infoParking.getFreeState()));

        if (parkingType.equals(DB_OpenHelper.HASHIEEI_PARKING_TYPE)) {
            holder.textViewFloor.setText(String.valueOf(dm_infoParking.getFloors()));

            holder.textViewTitleFloor.setVisibility(View.GONE);
            holder.textViewFloor.setVisibility(View.GONE);
        }

        holder.buttonSelect.setOnClickListener(view -> {
//            if (dm_infoParkings.get(holder.getAdapterPosition()).getParkingType().equals(DB_OpenHelper.OMOOMI_PARKING_TYPE)) {
//                Log.i(TAG, "onBindViewHolder: " + sharedClass.getGetAccount().getTypeCarWeight());
//                Log.i(TAG, "onBindViewHolder: " + dm_infoParking.getParkingTypeOmoomi());
//
//                if (dm_infoParkings.get(holder.getAdapterPosition()).getParkingTypeOmoomi().equals(sharedClass.getGetAccount().getTypeCarWeight())) {
            Intent intent = new Intent(context, SelectParkingActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("ParkingType", dm_infoParkings.get(holder.getAdapterPosition()).getParkingType());
            bundle.putInt("ParkingId", dm_infoParkings.get(holder.getAdapterPosition()).getId());

            intent.putExtras(bundle);
            context.startActivity(intent);
//                } else {
//                    alertDialog = CustomAlertDialog.getAlertDialogOk(context, "توجه", ("این پارکینگ مخصوص ").concat(dm_infoParkings.get(holder.getAdapterPosition()).getParkingTypeOmoomi()).concat(" می باشد. "), "باشه", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//                        }
//                    }, true);
//                    alertDialog.show();
//                }
//            } else {
//                Intent intent = new Intent(context, SelectParkingActivity.class);
//                Bundle bundle = new Bundle();
//
//                bundle.putString("ParkingType", dm_infoParkings.get(holder.getAdapterPosition()).getParkingType());
//                bundle.putInt("ParkingId", dm_infoParkings.get(holder.getAdapterPosition()).getId());
//
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
        });
    }

    @Override
    public int getItemCount() {
        return dm_infoParkings.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewParking;
        TextView textViewName, textViewTime, textViewTitleFloor, textViewFloor, textViewTotalState, textViewFreeState;
        Button buttonSelect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewParking = itemView.findViewById(R.id.imgView_rvParking);
            textViewName = itemView.findViewById(R.id.txtView_nameParking_rvParking);
            textViewTime = itemView.findViewById(R.id.txtView_time_rvParking);
            textViewTitleFloor = itemView.findViewById(R.id.txtView_titleFloors_rvParking);
            textViewFloor = itemView.findViewById(R.id.txtView_floors_rvParking);
            textViewTotalState = itemView.findViewById(R.id.txtView_totalState_rvParking);
            textViewFreeState = itemView.findViewById(R.id.txtView_totalStateFree_rvParking);
            buttonSelect = itemView.findViewById(R.id.btn_selectParking_rvParking);

        }
    }
}
