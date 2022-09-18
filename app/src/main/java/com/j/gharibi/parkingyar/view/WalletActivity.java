package com.j.gharibi.parkingyar.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.utility.SharedClass;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class WalletActivity extends AppCompatActivity {
    TextView textViewTitleMoney, textViewMoney, textViewToman, textViewUpMoney;
    Button buttonPay, buttonCancel;
    EditText editTextMoney;
    private SharedClass sharedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        setupInitializeView();
        setupControlView();

    }

    private void setupControlView() {
        setFontSizeApp();

        buttonCancel.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        buttonPay.setOnClickListener(v -> {
            Intent intent = new Intent(this, PayActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setFontSizeApp() {

        textViewTitleMoney.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewMoney.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewToman.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewUpMoney.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        editTextMoney.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonPay.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

    }

    private void setupInitializeView() {
        textViewTitleMoney = findViewById(R.id.txtView_title_money_walletAct);
        textViewMoney = findViewById(R.id.txtView_money_walletAct);
        textViewToman = findViewById(R.id.txtView_toman_walletAct);
        textViewUpMoney = findViewById(R.id.txtView_up_money_walletAct);

        editTextMoney = findViewById(R.id.editText_money_walletAct);

        buttonPay = findViewById(R.id.btn_pay_walletAct);
        buttonCancel = findViewById(R.id.btn_cancel_walletAct);

        sharedClass = new SharedClass(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}