package com.j.gharibi.parkingyar.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.utility.DB_OpenHelper;
import com.j.gharibi.parkingyar.utility.SharedClass;

public class BeginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beging);

        DB_OpenHelper db_openHelper = new DB_OpenHelper(this);
        SharedClass sharedClass = new SharedClass(this);

        if (db_openHelper.getMyParking().size() > 0) {
            db_openHelper.truncateDataTable(DB_OpenHelper.TABLE_RESERVE_PARKING);
            sharedClass.deleteAccount();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}