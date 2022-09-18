package com.j.gharibi.parkingyar.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.dataModule.Dm_ReserveParking;
import com.j.gharibi.parkingyar.view.MapSearchActivity;
import com.j.gharibi.parkingyar.view.PayActivity;

public class CustomAlertDialog {
    private static AlertDialog alertDialog = null;
    public static String QR_CODE = "qr_code";
    public static String DEFAULT_ALERT_FOR_PAYMENT = "default_alert";

    public static AlertDialog getAlertDialogYesOrNo(Context context, String title, String msg, String labelButtonYes, View.OnClickListener positiveOnClick, String labelButtonNo, View.OnClickListener negativeOnClick, boolean cancelabe) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_custom_alert_dialog_message, null);
        TextView textViewTitle = view.findViewById(R.id.txtV_title_custom_dialog_layout_message);
        textViewTitle.setText(title);
        TextView textViewMsg = view.findViewById(R.id.txtV_body_custom_dialog_layout_message);
        textViewMsg.setText(msg);

        Button buttonOk = view.findViewById(R.id.btn_ok_custom_dialog_layout_message);
        buttonOk.setVisibility(View.VISIBLE);
        buttonOk.setText(labelButtonYes);
        buttonOk.setOnClickListener(positiveOnClick);

        Button buttonNo = view.findViewById(R.id.btn_no_custom_dialog_layout_message);
        buttonNo.setVisibility(View.VISIBLE);
        buttonNo.setText(labelButtonNo);
        buttonNo.setOnClickListener(negativeOnClick);

        builder.setCancelable(cancelabe);

        builder.setView(view);

        alertDialog = builder.create();
        try {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.TRANSPARENT70)));
        } catch (Exception e) {

        }

        return alertDialog;
    }

    public static AlertDialog getAlertDialogPayment(Context context, String inputType, Dm_ReserveParking dm_reserveParking, boolean cancelable) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DB_OpenHelper db_openHelper = new DB_OpenHelper(context);

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_custom_alert_dialog_payment, null);

        ImageView imageViewQrcode = view.findViewById(R.id.imgView_qrcode_alertPaymentLayout);
        TextView textViewTitleReserve = view.findViewById(R.id.txtView_title_code_reserve_alertPaymentLayout);
        TextView textViewReserve = view.findViewById(R.id.txtView_code_reserve_alertPaymentLayout);

        CardView cardView = view.findViewById(R.id.cardView_alertPaymentLayout);
        TextView textViewWallet = view.findViewById(R.id.txtView_wallet_cardView_alertPaymentLayout);
        TextView textViewTitle = view.findViewById(R.id.txtView_title_alertPaymentLayout);
        Button buttonOnline = view.findViewById(R.id.btn_online_alertPaymentLayout);
        Button buttonWallet = view.findViewById(R.id.btn_wallet_alertPaymentLayout);

        if (inputType.equals(QR_CODE)) {
            imageViewQrcode.setVisibility(View.VISIBLE);
            textViewTitleReserve.setVisibility(View.VISIBLE);
            textViewReserve.setVisibility(View.VISIBLE);

            textViewReserve.setText(String.valueOf(dm_reserveParking.getTimeReserve()));

            cardView.setVisibility(View.GONE);
            textViewWallet.setVisibility(View.GONE);
            textViewTitle.setVisibility(View.GONE);
            buttonOnline.setVisibility(View.GONE);
            buttonWallet.setVisibility(View.GONE);
        }


        buttonWallet.setOnClickListener(view1 -> {
            imageViewQrcode.setVisibility(View.VISIBLE);
            textViewTitleReserve.setVisibility(View.VISIBLE);
            textViewReserve.setVisibility(View.VISIBLE);

            textViewReserve.setText(String.valueOf(dm_reserveParking.getTimeReserve()));

            cardView.setVisibility(View.GONE);
            textViewWallet.setVisibility(View.GONE);
            textViewTitle.setVisibility(View.GONE);
            buttonOnline.setVisibility(View.GONE);
            buttonWallet.setVisibility(View.GONE);

            db_openHelper.Insert_Reserved_Parking_State(dm_reserveParking);

            Intent intent = new Intent(context, MapSearchActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();

        });

        buttonOnline.setOnClickListener(view1 -> {
            db_openHelper.Insert_Reserved_Parking_State(dm_reserveParking);

            Intent intent = new Intent(context, PayActivity.class);
            context.startActivity(intent);
        });

        builder.setCancelable(cancelable);

        builder.setView(view);

        alertDialog = builder.create();
        try {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.TRANSPARENT70)));
        } catch (Exception e) {

        }

        return alertDialog;
    }

    public static AlertDialog getAlertDialogOk(Context context, String title, String msg, String labelButtonOk, View.OnClickListener positiveOnClick, boolean cancelAbe) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_custom_alert_dialog_message, null);
        TextView textViewTitle = view.findViewById(R.id.txtV_title_custom_dialog_layout_message);
        textViewTitle.setText(title);
        TextView textViewMsg = view.findViewById(R.id.txtV_body_custom_dialog_layout_message);
        textViewMsg.setText(msg);

        ImageView imageViewIcon = view.findViewById(R.id.imgV_icon_customize_dialog_message);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        scaleAnimation.setDuration(5000);
        imageViewIcon.startAnimation(scaleAnimation);

        Button buttonOk = view.findViewById(R.id.btn_ok_custom_dialog_layout_message);
        buttonOk.setVisibility(View.VISIBLE);
        buttonOk.setText(labelButtonOk);
        buttonOk.setOnClickListener(positiveOnClick);


        Button buttonNo = view.findViewById(R.id.btn_no_custom_dialog_layout_message);
        buttonNo.setVisibility(View.GONE);


        builder.setCancelable(cancelAbe);

        builder.setView(view);

        alertDialog = builder.create();
        try {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.TRANSPARENT70)));
        } catch (Exception e) {

        }
        return alertDialog;
    }

    public static AlertDialog getSelectStateParkingOmoomi(Context context, int currentFloor, int selectedState, OnGetStatePark onGetStatePark) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_custom_alert_dialog_select_state_omoomi, null);

        RecyclerView recyclerView = view.findViewById(R.id.rv_selectStateCustomAlertDialog);

        TextView textViewSelectedState = view.findViewById(R.id.txtView_selected_state_selectStateCustomAlertDialog);

//        Ad_StateParking ad_stateParking = new Ad_StateParking(context, currentFloor, selectedState, onGetStatePark);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setAdapter(ad_stateParking);

        Button buttonOk = view.findViewById(R.id.btn_ok_selectStateCustomAlertDialog);
        buttonOk.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        Button buttonCancel = view.findViewById(R.id.btn_cancel_selectStateCustomAlertDialog);
        buttonCancel.setOnClickListener(view1 -> {
            onGetStatePark.getState(0, true);
            alertDialog.dismiss();
        });

        builder.setCancelable(false);

        builder.setView(view);

        alertDialog = builder.create();
        try {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.TRANSPARENT70)));
        } catch (Exception e) {

        }
        return alertDialog;
    }

    public interface OnGetStatePark {
        void getState(int state, boolean result);
    }
}
