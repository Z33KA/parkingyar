package com.j.gharibi.parkingyar.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.j.gharibi.parkingyar.R;
import com.j.gharibi.parkingyar.dataModule.Dm_Account;

public class SharedClass {
    private static final String SHARED_DATA_FONT_SIZE = "001100";
    private static final String SHARED_DATA_DATA_ACCOUNT = "001200";

    private static final String KEY_DATA_FONT_SIZE_DEFAULT = "110011";

    private static final String KEY_DATA_ACCOUNT_NAME = "110011";
    private static final String KEY_DATA_ACCOUNT_PHONE = "110012";
    private static final String KEY_DATA_ACCOUNT_MODULE_CAR = "110013";
    private static final String KEY_DATA_ACCOUNT_COLOR_CAR = "110014";
    private static final String KEY_DATA_ACCOUNT_PELAK1_CAR = "110015";
    private static final String KEY_DATA_ACCOUNT_PELAK2_CAR = "110016";
    private static final String KEY_DATA_ACCOUNT_PELAK3_CAR = "110017";
    private static final String KEY_DATA_ACCOUNT_PELAK4_CAR = "110018";
    private static final String KEY_DATA_ACCOUNT_TYPE_CAR_WEIGHT = "110019";

    private Context context;
    private SharedPreferences sharedPreferences;

    public SharedClass(Context context) {
        this.context = context;
    }

    public void saveFontSize(int fontSize) {
        sharedPreferences = context.getSharedPreferences(SHARED_DATA_FONT_SIZE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_DATA_FONT_SIZE_DEFAULT, fontSize);

        editor.commit();
    }

    public int getFontSize() {
        sharedPreferences = context.getSharedPreferences(SHARED_DATA_FONT_SIZE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_DATA_FONT_SIZE_DEFAULT, 1);
    }

    public float getTextSizeType() {
        float size = 0;
        int fontSize = getFontSize();

        if (fontSize == 0)
            size = context.getResources().getDimension(R.dimen.lbl_small_font_size_12);
        else if (fontSize == 1)
            size = context.getResources().getDimension(R.dimen.lbl_normal_font_size_16);
        else if (fontSize == 2)
            size = context.getResources().getDimension(R.dimen.lbl_large_font_size_20);

        return size;
    }

    public void saveAccount(Dm_Account dm_account) {
        sharedPreferences = context.getSharedPreferences(SHARED_DATA_DATA_ACCOUNT, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_DATA_ACCOUNT_NAME, dm_account.getName());
        editor.putString(KEY_DATA_ACCOUNT_PHONE, dm_account.getPhone());
        editor.putString(KEY_DATA_ACCOUNT_COLOR_CAR, dm_account.getCarColor());
        editor.putString(KEY_DATA_ACCOUNT_PELAK1_CAR, dm_account.getPelak1());
        editor.putString(KEY_DATA_ACCOUNT_PELAK2_CAR, dm_account.getPelak2());
        editor.putString(KEY_DATA_ACCOUNT_PELAK3_CAR, dm_account.getPelak3());
        editor.putString(KEY_DATA_ACCOUNT_PELAK4_CAR, dm_account.getPelak4());
        editor.putString(KEY_DATA_ACCOUNT_TYPE_CAR_WEIGHT, dm_account.getTypeCarWeight());

        editor.commit();
    }

    public Dm_Account getGetAccount() {
        Dm_Account dm_account = new Dm_Account();
        sharedPreferences = context.getSharedPreferences(SHARED_DATA_DATA_ACCOUNT, Context.MODE_PRIVATE);


        dm_account.setName(sharedPreferences.getString(KEY_DATA_ACCOUNT_NAME, ""));
        dm_account.setPhone(sharedPreferences.getString(KEY_DATA_ACCOUNT_PHONE, ""));
        dm_account.setCarColor(sharedPreferences.getString(KEY_DATA_ACCOUNT_COLOR_CAR, ""));
        dm_account.setPelak1(sharedPreferences.getString(KEY_DATA_ACCOUNT_PELAK1_CAR, ""));
        dm_account.setPelak2(sharedPreferences.getString(KEY_DATA_ACCOUNT_PELAK2_CAR, ""));
        dm_account.setPelak3(sharedPreferences.getString(KEY_DATA_ACCOUNT_PELAK3_CAR, ""));
        dm_account.setPelak4(sharedPreferences.getString(KEY_DATA_ACCOUNT_PELAK4_CAR, ""));
        dm_account.setTypeCarWeight(sharedPreferences.getString(KEY_DATA_ACCOUNT_TYPE_CAR_WEIGHT, "0"));

        return dm_account;
    }
    public void deleteAccount(){
        sharedPreferences=context.getSharedPreferences(SHARED_DATA_DATA_ACCOUNT,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }
}
