package com.j.gharibi.parkingyar.view;


import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.dataModule.Dm_InfoParking;
import com.j.gharibi.parkingyar.dataModule.Dm_ReserveParking;
import com.j.gharibi.parkingyar.utility.CustomAlertDialog;
import com.j.gharibi.parkingyar.utility.DB_OpenHelper;
import com.j.gharibi.parkingyar.utility.PersianDate;
import com.j.gharibi.parkingyar.utility.SharedClass;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

public class SelectParkingActivity extends AppCompatActivity {

    ImageView imageViewParking, imageViewCalender;
    TextView textViewParkingName, textViewTitleSelect, textViewDisplayStateSelectedOnRv,  textViewPrice, textViewMonth, textViewSelectedState, textViewNowTime;
    Button buttonFloor1, buttonFloor2, buttonFloor3, buttonFloor4, buttonPay, buttonselectStateHashieei;
    View viewCustomAlertDialog;

    Dm_InfoParking dm_infoParking;
    SharedClass sharedClass;
    DB_OpenHelper db_openHelper;
    AlertDialog alertDialog;
    String strParkingType;
    int intParkingId, minutePerPark = 45;
    int intSelectedState = 0, nowHour = 0;
    //    , intSelectedStateHasieei = 0;
    double tempMinute = 0, tempHour = 0, tempMonth = 0, totalTimePerMinuteReserved = 0, totalPrice;
    DecimalFormat decimalFormat;
    PersianDate persianDate;
    Activity activity;

    NumberPicker numberPickerHour, numberPickerMinute;
    String[] arrStrHour, arrStrMin;
    int selectedHour, selectedMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parking);

        setupInitializeView();
        setupControl();
    }

    private void setupControl() {
        setFontSizeApp();
        Bundle bundle = getIntent().getExtras();

        callNowTime();

        arrStrHour = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
        arrStrMin = new String[]{"0", "15", "30", "45"};

        numberPickerHour.setMinValue(0);
        numberPickerHour.setMaxValue(arrStrHour.length - 1);
        numberPickerHour.setDisplayedValues(arrStrHour);

        numberPickerMinute.setMinValue(0);
        numberPickerMinute.setMaxValue(arrStrMin.length - 1);
        numberPickerMinute.setDisplayedValues(arrStrMin);

        textViewPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: " + arrStrHour[numberPickerHour.getValue()]);
                Log.i(TAG, "onClick: " + arrStrMin[numberPickerMinute.getValue()]);
            }
        });

        if (bundle != null) {
            strParkingType = bundle.getString("ParkingType");
            intParkingId = bundle.getInt("ParkingId");
            intSelectedState = bundle.getInt("selectedState");

            if (intSelectedState > 0)
                textViewSelectedState.setText(String.valueOf(intSelectedState));


            dm_infoParking = db_openHelper.getSpecialParking(intParkingId);

            imageViewParking.setImageResource(getResources().getIdentifier(dm_infoParking.getNameParkingImage(), "drawable", getPackageName()));
            textViewParkingName.setText(dm_infoParking.getParkingName());

            if (strParkingType.equals(DB_OpenHelper.OMOOMI_PARKING_TYPE)) {
                buttonFloor1.setVisibility(View.VISIBLE);
                buttonFloor2.setVisibility(View.VISIBLE);
                buttonFloor3.setVisibility(View.VISIBLE);
                buttonFloor4.setVisibility(View.VISIBLE);
//                imageViewCalender.setVisibility(View.GONE);

                if (dm_infoParking.getFloors() == 1) {
                    buttonFloor2.setVisibility(View.GONE);
                    buttonFloor3.setVisibility(View.GONE);
                    buttonFloor4.setVisibility(View.GONE);
                } else if (dm_infoParking.getFloors() == 2) {
                    buttonFloor3.setVisibility(View.GONE);
                    buttonFloor4.setVisibility(View.GONE);
                } else if (dm_infoParking.getFloors() == 3) {
                    buttonFloor4.setVisibility(View.GONE);
                }
            } else if (strParkingType.equals(DB_OpenHelper.HASHIEEI_PARKING_TYPE)) {
                buttonFloor1.setVisibility(View.GONE);
                buttonFloor2.setVisibility(View.GONE);
                buttonFloor3.setVisibility(View.GONE);
                buttonFloor4.setVisibility(View.GONE);
                buttonselectStateHashieei.setVisibility(View.VISIBLE);
                textViewTitleSelect.setVisibility(View.GONE);
            }
        }


        buttonFloor1.setOnClickListener(view -> {
            callIntentSelectState(1, intSelectedState, strParkingType);
        });
        buttonFloor2.setOnClickListener(view -> {
            callIntentSelectState(2, intSelectedState, strParkingType);
        });
        buttonFloor3.setOnClickListener(view -> {
            callIntentSelectState(3, intSelectedState, strParkingType);
        });
        buttonFloor4.setOnClickListener(view -> {
            callIntentSelectState(4, intSelectedState, strParkingType);
        });

        buttonselectStateHashieei.setOnClickListener(view -> {
            callIntentSelectState(1, intSelectedState, strParkingType);
        });

//        textViewHour.setOnClickListener(view -> {
//
//            PopupMenu popupMenu = new PopupMenu(SelectParkingActivity.this, textViewHour);
//            // Inflating popup menu from popup_menu.xml file
//            popupMenu.getMenuInflater().inflate(R.menu.menu_item_hours, popupMenu.getMenu());
//            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem menuItem) {
//                    tempHour = 0;
//                    tempMonth = 0;
//                    textViewMonth.setText("");
//
//                    tempHour = Double.valueOf(menuItem.getTitle().toString()) * 60;
//                    textViewHour.setText(menuItem.getTitle());
//                    calculatePrice();
//                    return true;
//                }
//            });
//            // Showing the popup menu
//            popupMenu.show();
//        });
//        textViewMinute.setOnClickListener(view -> {
//
//            PopupMenu popupMenu = new PopupMenu(SelectParkingActivity.this, textViewMinute);
//            // Inflating popup menu from popup_menu.xml file
//            popupMenu.getMenuInflater().inflate(R.menu.menu_item_minute, popupMenu.getMenu());
//            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem menuItem) {
//                    tempMinute = 0;
//                    tempMonth = 0;
//                    textViewMonth.setText("");
//
//                    tempMinute = Double.valueOf(menuItem.getTitle().toString());
//
//                    textViewMinute.setText(menuItem.getTitle());
//                    calculatePrice();
//                    return true;
//                }
//            });
//            // Showing the popup menu
//            popupMenu.show();
//        });
        imageViewCalender.setOnClickListener(view -> {

            PopupMenu popupMenu = new PopupMenu(SelectParkingActivity.this,imageViewCalender);
            // Inflating popup menu from popup_menu.xml file
            popupMenu.getMenuInflater().inflate(R.menu.menu_item_calender, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    calculatePrice(menuItem.getTitle().toString(), "0", "0");

                    textViewMonth.setText(menuItem.getTitle());
                    numberPickerHour.setValue(0);
                    numberPickerMinute.setValue(0);
//                    textViewMinute.setText("+");
//                    textViewHour.setText("+");
                    return true;
                }
            });
            // Showing the popup menu
            popupMenu.show();
        });

        numberPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                calculatePrice("nothing",arrStrHour[i1],arrStrMin[numberPickerMinute.getValue()]);
            }
        });

        numberPickerMinute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                calculatePrice("nothing",arrStrHour[numberPickerHour.getValue()],arrStrMin[i1]);
            }
        });
        buttonPay.setOnClickListener(view -> {

            if (textViewPrice.getText().toString().length() > 0 && intSelectedState > 0) {

                Dm_ReserveParking dm_reserveParking = new Dm_ReserveParking();
                dm_reserveParking.setParkingId(intParkingId);
                dm_reserveParking.setNumberState(intSelectedState);
                dm_reserveParking.setFloorParking(dm_infoParking.getFloors());
                dm_reserveParking.setParkingType(strParkingType);
                dm_reserveParking.setTimeReserve(String.valueOf(totalTimePerMinuteReserved));
                dm_reserveParking.setPricePayment(String.valueOf(totalPrice));

                alertDialog = CustomAlertDialog.getAlertDialogPayment(this, CustomAlertDialog.DEFAULT_ALERT_FOR_PAYMENT, dm_reserveParking, true);
                alertDialog.show();
            } else {
                Toast.makeText(this, "لطفا زمان و جایگاه پارک مورد نظر را وارد کنید", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void callNowTime() {
        new Thread() {
            @Override
            public void run() {
                try {
                    int init = 2400000;
                    while (init-- > 0) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewNowTime.setText(("اکنون ").concat(persianDate.getNowTime()));
                                nowHour = Integer.valueOf(persianDate.getNowHour());
                            }
                        });
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void calculatePrice(String longTime, String hourNow, String minuteNow) {
//        totalPrice = 0;
//        totalTimePerMinuteReserved = tempHour + tempMinute + tempMonth;
//        totalPrice = totalTimePerMinuteReserved * minutePerPark;
//
//        String price = decimalFormat.format(totalPrice);
//
//        selectedHour = Integer.parseInt(hourNow);
//        selectedMin = Integer.parseInt(minuteNow);
        double convertedTime = 0L;

        if (longTime.equals("3 ماه")) {
            convertedTime = 800D * 21600;
        } else if (longTime.equals("6 ماه")) {
            convertedTime = 800D * 43200;
        } else if (longTime.equals("12 ماه")) {
            convertedTime = 800D * 86400;
        } else {
            String selectedTime = hourNow.concat(",").concat(minuteNow);
//            convertedTime = Long.parseLong(selectedTime);

            Log.i(TAG, "calculatePrice 0: "+selectedTime);

            Number number = null;
            try {
                number = NumberFormat.getNumberInstance(Locale.FRENCH).parse(selectedTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            convertedTime= number.doubleValue();

            Log.i(TAG, "calculatePrice 1: "+convertedTime);


            if (nowHour >= 00 && nowHour <= 06) {
                convertedTime = convertedTime * 800D;
            } else if (nowHour >= 18 && nowHour <= 22) {
                convertedTime = convertedTime * 1200D;
            } else {
                convertedTime = convertedTime * 1000D;
            }

            Log.i(TAG, "calculatePrice 2: "+convertedTime);
        }

        String price = decimalFormat.format(convertedTime);


        textViewPrice.setText(String.valueOf(price));
    }

    private void callIntentSelectState(int currentFloor, int selectedState, String parkingType) {
        Intent intent = new Intent(SelectParkingActivity.this, SelectedStateActivity.class);
        intent.putExtra("ParkingType", parkingType);
        intent.putExtra("SelectedState", selectedState);
        intent.putExtra("ParkingId", intParkingId);
        intent.putExtra("CurrentFloor", currentFloor);
        startActivity(intent);
    }

    private void setupInitializeView() {
        viewCustomAlertDialog = View.inflate(this, R.layout.layout_custom_alert_dialog_select_state_omoomi, null);
        textViewDisplayStateSelectedOnRv = viewCustomAlertDialog.findViewById(R.id.txtView_selected_state_selectStateCustomAlertDialog);

        imageViewParking = findViewById(R.id.imgView_imageParking_selectParkingAct);
        imageViewCalender = findViewById(R.id.imgView_calender_selectParkingAct);

        textViewParkingName = findViewById(R.id.txtView_nameParking_selectParkingAct);
        textViewTitleSelect = findViewById(R.id.txtView_title_state_selectParkingAct);

        buttonFloor1 = findViewById(R.id.btn_floor1_selectParkingAct);
        buttonFloor2 = findViewById(R.id.btn_floor2_selectParkingAct);
        buttonFloor3 = findViewById(R.id.btn_floor3_selectParkingAct);
        buttonFloor4 = findViewById(R.id.btn_floor4_selectParkingAct);
        buttonPay = findViewById(R.id.btn_payment_selectParkingAct);
        buttonselectStateHashieei = findViewById(R.id.btn_selectStateHashieei_selectParkingAct);

        textViewPrice = findViewById(R.id.editText_price_selectParkingAct);
        textViewMonth = findViewById(R.id.txtView_selected_month_selectParkingAct);
        textViewSelectedState = findViewById(R.id.txtView_selected_state_selectParkingAct);
        textViewNowTime = findViewById(R.id.txtVie_time_selectParkingAct);

        numberPickerHour = findViewById(R.id.numPicker_hour_selectParkingAct);
        numberPickerMinute = findViewById(R.id.numPicker_minute_selectParkingAct);

        sharedClass = new SharedClass(this);
        db_openHelper = new DB_OpenHelper(this);

        decimalFormat = new DecimalFormat("#,##0");
        persianDate = new PersianDate();
        activity = this;
    }

    private void setFontSizeApp() {
        textViewDisplayStateSelectedOnRv.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewParkingName.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewTitleSelect.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

        buttonFloor1.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonFloor2.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonFloor3.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonFloor4.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonPay.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonselectStateHashieei.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

        textViewPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewMonth.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewSelectedState.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
    }
}