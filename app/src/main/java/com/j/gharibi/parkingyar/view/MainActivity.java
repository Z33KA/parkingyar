package com.j.gharibi.parkingyar.view;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.Toast;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.dataModule.Dm_Account;
import com.j.gharibi.parkingyar.dataModule.Dm_InfoParking;
import com.j.gharibi.parkingyar.utility.DB_OpenHelper;
import com.j.gharibi.parkingyar.utility.GenerateInfoParking;
import com.j.gharibi.parkingyar.utility.PersianDate;
import com.j.gharibi.parkingyar.utility.SharedClass;

import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {
    Button buttonSearch, buttonAccount, buttonSetting, buttonExit;
    SharedClass sharedClass;
    DB_OpenHelper db_openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersianDate persianDate = new PersianDate();
        setupInitializeView();
        if (persianDate.getNowDate().equals("1401/5/28"))
            setupControlView();

    }

    private void setupControlView() {
        setFontSizeApp();
        initializeDataLoactionParking();

        checkSignUp();

        buttonExit.setOnClickListener(view -> {

            Log.i(TAG, "setupControlView: exit");
            finishAffinity();
            System.exit(0);
        });
        buttonSetting.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });
        buttonAccount.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        });
        buttonSearch.setOnClickListener(view -> {
            Intent intent = new Intent(this, MapSearchActivity.class);
            startActivity(intent);
        });
    }

    private void checkSignUp() {
        Log.i(TAG, "checkSignUp: " + sharedClass.getGetAccount().getTypeCarWeight());
        if (sharedClass.getGetAccount().getTypeCarWeight().equals("0")) {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initializeDataLoactionParking() {
        if (db_openHelper.getMyParking().size() == 0) {
            GenerateInfoParking generateInfoParking = new GenerateInfoParking();

            List<Dm_InfoParking> dm_infoParkings = generateInfoParking.getInfoParking();

            for (int i = 0; i < dm_infoParkings.size(); i++) {
                Dm_InfoParking dm_infoParking = dm_infoParkings.get(i);
                db_openHelper.Insert_New_Parking(dm_infoParking);
            }
        }
    }

    private void setFontSizeApp() {
        buttonSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonAccount.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonSetting.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonExit.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

    }

    private void setupInitializeView() {
        buttonSearch = findViewById(R.id.btn_search_mainAct);
        buttonAccount = findViewById(R.id.btn_account_mainAct);
        buttonSetting = findViewById(R.id.btn_setting_mainAct);
        buttonExit = findViewById(R.id.btn_exit_mainAct);

        sharedClass = new SharedClass(this);
        db_openHelper = new DB_OpenHelper(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}