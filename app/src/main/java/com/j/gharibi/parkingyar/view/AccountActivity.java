package com.j.gharibi.parkingyar.view;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.dataModule.Dm_Account;
import com.j.gharibi.parkingyar.utility.SharedClass;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class AccountActivity extends AppCompatActivity {
    TextView textViewTitleName, textViewTitlePhone, textViewTitleColorCar;
    EditText editTextName, editTextPhone, editTextColorCar, editTextPelak1, editTextPelak2, editTextPelak3, editTextPelak4;
    Button buttonEdit, buttonWallet;
    private SharedClass sharedClass;
    ArrayAdapter<String> typeCarAdapter;
    Spinner spinnerTypeCar;
    List<String> arrString;
    public String typeCar900 = "پراید", typeCar1400 = "سمند", typeCar2500 = "لندکروز";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        setupInitializeView();
        setupControlView();
    }

    private void setupControlView() {
        arrString = new ArrayList<>();
        arrString.add(typeCar900);
        arrString.add(typeCar1400);
        arrString.add(typeCar2500);

        typeCarAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrString);
        typeCarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeCar.setAdapter(typeCarAdapter);

        setFontSizeApp();
        setValueAccount();


        buttonEdit.setOnClickListener(v -> {

            if (editTextName.getText().toString().length() >0 && editTextPhone.getText().toString().length() > 0 && editTextColorCar.getText().toString().length() > 0 && editTextPelak1.getText().toString().length() > 1 && editTextPelak2.getText().toString().length() >0 && editTextPelak3.getText().toString().length() > 2 && editTextPelak4.getText().toString().length() > 1){
                Dm_Account dm_account = new Dm_Account();

                dm_account.setName(editTextName.getText().toString());
                dm_account.setPhone(editTextPhone.getText().toString());
                dm_account.setCarColor(editTextColorCar.getText().toString());
                dm_account.setPelak1(editTextPelak1.getText().toString());
                dm_account.setPelak2(editTextPelak2.getText().toString());
                dm_account.setPelak3(editTextPelak3.getText().toString());
                dm_account.setPelak4(editTextPelak4.getText().toString());
                dm_account.setTypeCarWeight(spinnerTypeCar.getSelectedItem().toString());

                Log.i(TAG, "setupControlView: "+spinnerTypeCar.getSelectedItem().toString());
                sharedClass.saveAccount(dm_account);

                Toast.makeText(this, "اطلاعات کاربر ثبت شد!", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }else
            {
                Toast.makeText(this, "لطفا کادرهای بالا را پر کنید!", Toast.LENGTH_SHORT).show();
            }

        });
        buttonWallet.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, WalletActivity.class);
            startActivity(intent);
        });
    }

    private void setValueAccount() {
        if (sharedClass.getGetAccount() != null) {
            Dm_Account dm_account = sharedClass.getGetAccount();

            editTextName.setText(dm_account.getName());
            editTextPhone.setText(dm_account.getPhone());
            editTextColorCar.setText(dm_account.getCarColor());
            editTextPelak1.setText(dm_account.getPelak1());
            editTextPelak2.setText(dm_account.getPelak2());
            editTextPelak3.setText(dm_account.getPelak3());
            editTextPelak4.setText(dm_account.getPelak4());

            for (int i = 0; i < arrString.size(); i++) {
                if (dm_account.getTypeCarWeight().equals(arrString.get(i))) {
                    spinnerTypeCar.setSelection(i);
                }
            }
        }

    }

    private void setFontSizeApp() {

        editTextName.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        editTextPhone.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        editTextColorCar.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

        textViewTitleName.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        textViewTitleColorCar.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());

        buttonEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
        buttonWallet.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedClass.getTextSizeType());
    }

    private void setupInitializeView() {
        editTextName = findViewById(R.id.edtText_name_accountAct);
        editTextPhone = findViewById(R.id.edtText_phone_accountAct);
        editTextColorCar = findViewById(R.id.edtText_color_car_accountAct);

        editTextPelak1 = findViewById(R.id.edtText_pelak1_accountAct);
        editTextPelak2 = findViewById(R.id.edtText_pelak2_accountAct);
        editTextPelak3 = findViewById(R.id.edtText_pelak3_accountAct);
        editTextPelak4 = findViewById(R.id.edtText_pelak4_accountAct);

        textViewTitleName = findViewById(R.id.txtView_title_name_accountAct);
        textViewTitlePhone = findViewById(R.id.txtView_title_phone_accountAct);
        textViewTitleColorCar = findViewById(R.id.txtView_title_color_car_accountAct);

        buttonEdit = findViewById(R.id.btn_edit_accountAct);
        buttonWallet = findViewById(R.id.btn_wallet_accountAct);

        spinnerTypeCar = findViewById(R.id.spinner_type_car_weight_accountAct);

        sharedClass = new SharedClass(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}