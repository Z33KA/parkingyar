package com.j.gharibi.parkingyar.view;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.utility.CustomAlertDialog;
import com.j.gharibi.parkingyar.utility.SharedClass;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SettingActivity extends AppCompatActivity {

    TextView textViewFontSize, textViewTheme;
    Spinner spinnerFontSize, spinnerTheme;
    Button buttonCallWithUs;
    AlertDialog alertDialog;

    private SharedClass sharedClass;
    private ArrayAdapter<CharSequence> adFontSize, ad_ThemeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setupInitializeView();
        setupControlView();
    }

    private void setupControlView() {
        setFontSizeApp();

        int positionSelectedFont = sharedClass.getFontSize();
        spinnerFontSize.setSelection(positionSelectedFont);

        int positionTheme=AppCompatDelegate.getDefaultNightMode();

        if (positionTheme==AppCompatDelegate.MODE_NIGHT_YES)
            spinnerTheme.setSelection(1);
        else if (positionTheme==AppCompatDelegate.MODE_NIGHT_NO)
            spinnerTheme.setSelection(0);

        Log.i(TAG, "setupControlView: "+positionTheme);

        spinnerFontSize.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    callClickFontSizeSpinner();
                }
                return false;
            }
        });

        spinnerTheme.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    callClickThemeSpinner();
                return false;
            }
        });

        buttonCallWithUs.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:09123456789"));
            startActivity(intent);
        });

    }

    private void callClickFontSizeSpinner() {
        spinnerFontSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                view.setOnClickListener(v -> {
                    Log.i(TAG, "onItemSelected: clicked");
                });
                callAlertDialogForFontSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "onItemSelected: non");
            }
        });
    }

    private void callClickThemeSpinner() {
        spinnerTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                view.setOnClickListener(v -> {
                    Log.i(TAG, "onItemSelected: clicked");
                });
                if (position == 0)
                    callAlertDialogForThemeSpinner(AppCompatDelegate.MODE_NIGHT_NO);
                else if (position == 1)
                    callAlertDialogForThemeSpinner(AppCompatDelegate.MODE_NIGHT_YES);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "onItemSelected: non");
            }
        });
    }

    private void callAlertDialogForFontSpinner(int directSizeDimension) {
        alertDialog = CustomAlertDialog.getAlertDialogYesOrNo(SettingActivity.this, "سایز فونت", "آیا مایل به تغییر سایز فونت برنامه می باشید؟", "بله", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedClass.saveFontSize(directSizeDimension);

                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, "خیر", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        }, true);
        alertDialog.show();
    }

    private void callAlertDialogForThemeSpinner(int directTheme) {
        alertDialog = CustomAlertDialog.getAlertDialogYesOrNo(SettingActivity.this, "قالب نمایشی نرم افزار", "آیا مایل به تغغیر قالب می باشید؟", "بله", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(directTheme);

                alertDialog.dismiss();
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, "خیر", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        }, true);
        alertDialog.show();
    }

    private void setFontSizeApp() {
        textViewFontSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewTheme.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

        buttonCallWithUs.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
    }

    private void setupInitializeView() {
        textViewFontSize = findViewById(R.id.txtView_font_size_settingAct);
        textViewTheme = findViewById(R.id.txtView_theme_settingAct);

        spinnerFontSize = findViewById(R.id.spinner_font_size_settingAct);
        spinnerTheme = findViewById(R.id.spinner_theme_settingAct);

        buttonCallWithUs = findViewById(R.id.btn_call_with_we_settingAct);

        adFontSize = ArrayAdapter.createFromResource(this, R.array.font_size_array, android.R.layout.simple_spinner_dropdown_item);
        spinnerFontSize.setAdapter(adFontSize);

        ad_ThemeName = ArrayAdapter.createFromResource(this, R.array.theme_app, android.R.layout.simple_spinner_dropdown_item);
        spinnerTheme.setAdapter(ad_ThemeName);

        sharedClass = new SharedClass(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}