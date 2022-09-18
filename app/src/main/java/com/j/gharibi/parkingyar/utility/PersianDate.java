package com.j.gharibi.parkingyar.utility;

import android.content.Context;
import android.graphics.Color;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;


public class PersianDate {
    Calendar c;

    public PersianDate() {
        c = Calendar.getInstance();
    }

    public String getNowDate() {
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        CalendarTool ct = new CalendarTool(mYear, mMonth, mDay);
        return ct.getIranianDate();

    }

    public String getNowTime() {
        String hour = getNowHour();
        String minute = getNowMinute();
        String second = getNowSecond();

        String nowTime = hour.concat(":").concat(minute).concat(":").concat(second);

        return nowTime;
    }

    public String getNowHour() {
        c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        String hourFinal;
        if (hour < 10)
            hourFinal = String.valueOf(0).concat(String.valueOf(hour));
        else
            hourFinal = String.valueOf(hour);

        return hourFinal;
    }

    public String getNowMinute() {
        c = Calendar.getInstance();

        int minute = c.get(Calendar.MINUTE);
        String minuteFinal;
        if (minute < 10)
            minuteFinal = String.valueOf(0).concat(String.valueOf(minute));
        else
            minuteFinal = String.valueOf(minute);

        return minuteFinal;
    }

    public String getNowSecond() {
        c = Calendar.getInstance();

        int second = c.get(Calendar.SECOND);
        String secondFinal;
        if (second < 10)
            secondFinal = String.valueOf(0).concat(String.valueOf(second));
        else
            secondFinal = String.valueOf(second);

        return secondFinal;
    }

    public static PersianDatePickerDialog getPersianDatePickerDialog(Context context, GetDateNow getDateNow) {

        PersianDatePickerDialog picker;
        return picker = new PersianDatePickerDialog(context)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
                .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                .setInitDate(PersianDatePickerDialog.THIS_YEAR, PersianDatePickerDialog.THIS_MONTH, PersianDatePickerDialog.THIS_DAY)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new PersianPickerListener() {
                    @Override
                    public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                        String nowDate = persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay();
                        getDateNow.onGetDate(nowDate);
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
    }

    public static PersianDatePickerDialog getPersianNowDate(Context context, GetDateNow getDateNow) {

        PersianDatePickerDialog picker;
        return picker = new PersianDatePickerDialog(context)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
                .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                .setInitDate(PersianDatePickerDialog.THIS_YEAR, PersianDatePickerDialog.THIS_MONTH, PersianDatePickerDialog.THIS_DAY)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new PersianPickerListener() {
                    @Override
                    public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                        String nowDate = persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay() + "_" + persianPickerDate.getTimestamp();
                        getDateNow.onGetDate(nowDate);

                    }

                    @Override
                    public void onDismissed() {

                    }
                });
    }

    public interface GetDateNow {
        void onGetDate(String nowDate);
    }
}
