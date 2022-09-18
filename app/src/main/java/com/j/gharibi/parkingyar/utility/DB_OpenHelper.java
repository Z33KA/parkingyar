package com.j.gharibi.parkingyar.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.j.gharibi.parkingyar.dataModule.Dm_InfoParking;
import com.j.gharibi.parkingyar.dataModule.Dm_ReserveParking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DB_OpenHelper extends SQLiteOpenHelper {

    PersianDate persianDate = new PersianDate();

    private static final String DB_NAME = "DB_PARKING_YAR";
    private static final int DB_VERSION = 1;

    public static final String HASHIEEI_PARKING_TYPE = "hashieei_parking";
    public static final String OMOOMI_PARKING_TYPE = "omoomi_parking";

    public static final String TABLE_INFO_PARKING = "tbl_info_parking";
    public static final String TABLE_RESERVE_PARKING_HASHIEEI = "tbl_reserve_parking_hashieei";
    public static final String TABLE_RESERVE_PARKING = "tbl_reserve_parking";


    //    String Fields Name
    private static final String CLM_ID = "clm_id";
    private static final String CLM_LONGITUDE = "clm_longtude";
    private static final String CLM_LATITUDE = "clm_latitude";
    private static final String CLM_PARKING_TYPE = "clm_parking_type";
    private static final String CLM_NAME_PARKING_IMAGE = "clm_parking_image";
    private static final String CLM_PARKING_TYPE_OMOOMI = "clm_parking_type_omoomi";

    private static final String CLM_PARKING_NAME = "clm_parking_name";
    private static final String CLM_TIME_WORK = "clm_time_work";
    private static final String CLM_FLOORS = "clm_floors";
    private static final String CLM_TOTAL_QUANTITY_STATE = "clm_total_quantity_state";

    private static final String CLM_PARKING_ID = "clm_parking_id";
    private static final String CLM_PARKING_FLOOR_PARKING_OMOOMI = "clm_floor_parking_omoomi";
    private static final String CLM_NUMBER_STATE_RESERVE = "clm_number_state_reserve";
    private static final String CLM_TIME_RESERVE = "clm_time_reserve";
    private static final String CLM_MONY_PAYMENT = "clm_mony_payment";


    final String SQL_COMMAND_CREATE_TABLE_INFO_PARKING = "CREATE TABLE IF NOT EXISTS " + TABLE_INFO_PARKING + " (" +
            CLM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CLM_LONGITUDE + " DOUBLE, " +
            CLM_LATITUDE + " DOUBLE, " +
            CLM_PARKING_TYPE + " TEXT, " +
            CLM_NAME_PARKING_IMAGE + " TEXT, " +
            CLM_PARKING_NAME + " TEXT, " +
            CLM_TIME_WORK + " TEXT, " +
            CLM_FLOORS + " INTEGER, " +
            CLM_PARKING_TYPE_OMOOMI + " TEXT, " +
            CLM_TOTAL_QUANTITY_STATE + " INTEGER);";


    final String SQL_COMMAND_CREATE_TABLE_RESERVE_PARKING = "CREATE TABLE IF NOT EXISTS " + TABLE_RESERVE_PARKING + " (" +
            CLM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CLM_PARKING_ID + " INTEGER, " +
            CLM_NUMBER_STATE_RESERVE + " INTEGER, " +
            CLM_PARKING_FLOOR_PARKING_OMOOMI + " INTEGER, " +
            CLM_PARKING_TYPE + " TEXT, " +
            CLM_TIME_RESERVE + " TEXT, " +
            CLM_MONY_PAYMENT + " TEXT);";

    private static final String TAG = "SQLiteTag";
    Context context;
    SharedClass sharedClass;

    public DB_OpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        sharedClass = new SharedClass(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(SQL_COMMAND_CREATE_TABLE_INFO_PARKING);
            db.execSQL(SQL_COMMAND_CREATE_TABLE_RESERVE_PARKING);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate: " + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        switch (newVersion) {
//            case 1:
//                db.execSQL(SQL_COMMAND_CREATE_TABLE_CATEGORY_SERVICE);
//                break;
//            case 2:
//                db.execSQL(SQL_COMMAND_CREATE_TABLE_SELECTED_SERVICE);
//                break;
//            case 5:
//                db.execSQL(SQL_COMMAND_CREATE_TABLE_SELECTED_SERVICE2);
//                break;
//        }

    }

    public void truncateDataTable(String TableName) {

        String query = "DELETE FROM " + TableName + " ;";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();

//        db.delete(TABLE_RESERVE_PARKING_HASHIEEI, null, null);
        db.close();

//        Log.i(TAG, "deleteSelectedService: " + res);
    }


    public void Insert_New_Parking(Dm_InfoParking dm_infoParking) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(CLM_LONGITUDE, dm_infoParking.getLongitude());
        cv.put(CLM_LATITUDE, String.valueOf(dm_infoParking.getLatitude()));
        cv.put(CLM_PARKING_TYPE, String.valueOf(dm_infoParking.getParkingType()));
        cv.put(CLM_NAME_PARKING_IMAGE, String.valueOf(dm_infoParking.getNameParkingImage()));
        cv.put(CLM_PARKING_NAME, String.valueOf(dm_infoParking.getParkingName()));
        cv.put(CLM_TIME_WORK, String.valueOf(dm_infoParking.getTimeWork()));
        cv.put(CLM_FLOORS, dm_infoParking.getFloors());
        cv.put(CLM_PARKING_TYPE_OMOOMI, dm_infoParking.getParkingTypeOmoomi());
        cv.put(CLM_TOTAL_QUANTITY_STATE, dm_infoParking.getTotalQuantityState());

        Long isInsert = db.insert(TABLE_INFO_PARKING, null, cv);

        Log.i(TAG, "addData: " + isInsert);
        db.close();
    }

    public void Insert_Reserved_Parking_State(Dm_ReserveParking dm_reserveParking) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(CLM_PARKING_ID, dm_reserveParking.getParkingId());
        cv.put(CLM_NUMBER_STATE_RESERVE, String.valueOf(dm_reserveParking.getNumberState()));
        cv.put(CLM_PARKING_FLOOR_PARKING_OMOOMI, String.valueOf(dm_reserveParking.getFloorParking()));
        cv.put(CLM_PARKING_TYPE, String.valueOf(dm_reserveParking.getParkingType()));
        cv.put(CLM_TIME_RESERVE, String.valueOf(dm_reserveParking.getTimeReserve()));
        cv.put(CLM_MONY_PAYMENT, String.valueOf(dm_reserveParking.getPricePayment()));

        Long isInsert = db.insert(TABLE_RESERVE_PARKING, null, cv);

        Log.i(TAG, "addData: " + isInsert);
        db.close();
    }

    public List<Dm_InfoParking> getMyParking() {
        List<Dm_InfoParking> dm_infoParkings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_INFO_PARKING, null);
        cursorInfo.moveToFirst();
        if (cursorInfo.getCount() > 0) {
            while (!cursorInfo.isAfterLast()) {
                Dm_InfoParking dm_infoParking = new Dm_InfoParking();

                Log.i(TAG, "getMyParking1: " + cursorInfo.getString(8));
                Log.i(TAG, "getMyParking2: " + sharedClass.getGetAccount().getTypeCarWeight());
                Log.i(TAG, "getMyParking3: " + cursorInfo.getString(3));
                Log.i(TAG, "getMyParking4: " + DB_OpenHelper.OMOOMI_PARKING_TYPE);
                if (cursorInfo.getString(8) != null && cursorInfo.getString(8).equals(sharedClass.getGetAccount().getTypeCarWeight()) && cursorInfo.getString(3).equals(DB_OpenHelper.OMOOMI_PARKING_TYPE)) {


                    dm_infoParking.setId(cursorInfo.getInt(0));
                    dm_infoParking.setLongitude(cursorInfo.getDouble(1));
                    dm_infoParking.setLatitude(cursorInfo.getDouble(2));
                    dm_infoParking.setParkingType(cursorInfo.getString(3));
                    dm_infoParking.setNameParkingImage(cursorInfo.getString(4));
                    dm_infoParking.setParkingName(cursorInfo.getString(5));
                    dm_infoParking.setTimeWork(cursorInfo.getString(6));
                    dm_infoParking.setFloors(cursorInfo.getInt(7));
                    dm_infoParking.setParkingTypeOmoomi(cursorInfo.getString(8));
                    dm_infoParking.setTotalQuantityState(cursorInfo.getInt(9));


                    int counterReservedState = 0;
//                calculate free state parking
                    {
                        Cursor cursorFreeState = db.rawQuery("SELECT * FROM " + TABLE_RESERVE_PARKING + " WHERE " + CLM_PARKING_ID + " = " + dm_infoParking.getId(), null);
                        cursorFreeState.moveToFirst();

                        if (cursorFreeState.getCount() > 0) {
                            while (!cursorFreeState.isAfterLast()) {
                                counterReservedState++;
                                cursorFreeState.moveToNext();
                            }
                        }
                        cursorFreeState.close();
                        int totalState = cursorInfo.getInt(9);
                        Log.i(TAG, "getMyParking: " + totalState + " ====>" + counterReservedState);
                        dm_infoParking.setFreeState(totalState - counterReservedState);

                    }

                    dm_infoParkings.add(dm_infoParking);
                }

                if (cursorInfo.getString(3).equals(DB_OpenHelper.HASHIEEI_PARKING_TYPE)) {

                    dm_infoParking.setId(cursorInfo.getInt(0));
                    dm_infoParking.setLongitude(cursorInfo.getDouble(1));
                    dm_infoParking.setLatitude(cursorInfo.getDouble(2));
                    dm_infoParking.setParkingType(cursorInfo.getString(3));
                    dm_infoParking.setNameParkingImage(cursorInfo.getString(4));
                    dm_infoParking.setParkingName(cursorInfo.getString(5));
                    dm_infoParking.setTimeWork(cursorInfo.getString(6));
                    dm_infoParking.setFloors(cursorInfo.getInt(7));
                    dm_infoParking.setParkingTypeOmoomi(cursorInfo.getString(8));
                    dm_infoParking.setTotalQuantityState(cursorInfo.getInt(9));


                    int counterReservedState = 0;
//                calculate free state parking
                    {
                        Cursor cursorFreeState = db.rawQuery("SELECT * FROM " + TABLE_RESERVE_PARKING + " WHERE " + CLM_PARKING_ID + " = " + dm_infoParking.getId(), null);
                        cursorFreeState.moveToFirst();

                        if (cursorFreeState.getCount() > 0) {
                            while (!cursorFreeState.isAfterLast()) {
                                counterReservedState++;
                                cursorFreeState.moveToNext();
                            }
                        }
                        cursorFreeState.close();
                        int totalState = cursorInfo.getInt(9);
                        Log.i(TAG, "getMyParking: " + totalState + " ====>" + counterReservedState);
                        dm_infoParking.setFreeState(totalState - counterReservedState);

                    }

                    dm_infoParkings.add(dm_infoParking);
                }
                cursorInfo.moveToNext();
            }
        }
        cursorInfo.close();
        db.close();

        Collections.sort(dm_infoParkings, new Comparator<Dm_InfoParking>() {
            @Override
            public int compare(Dm_InfoParking dm_infoParking, Dm_InfoParking t1) {
                return Integer.valueOf(t1.getFreeState()).compareTo(dm_infoParking.getFreeState());
            }
        });


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dm_infoParkings;
    }

    public Dm_InfoParking getSpecialParking(int parkingId) {
        Dm_InfoParking dm_infoParking = new Dm_InfoParking();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_INFO_PARKING + " WHERE " + CLM_ID + " = " + parkingId, null);
        cursorInfo.moveToFirst();
        if (cursorInfo.getCount() > 0) {
            dm_infoParking = new Dm_InfoParking();
            while (!cursorInfo.isAfterLast()) {


                dm_infoParking.setId(cursorInfo.getInt(0));
                dm_infoParking.setLongitude(cursorInfo.getDouble(1));
                dm_infoParking.setLatitude(cursorInfo.getDouble(2));
                dm_infoParking.setParkingType(cursorInfo.getString(3));
                dm_infoParking.setNameParkingImage(cursorInfo.getString(4));
                dm_infoParking.setParkingName(cursorInfo.getString(5));
                dm_infoParking.setTimeWork(cursorInfo.getString(6));
                dm_infoParking.setFloors(cursorInfo.getInt(7));
                dm_infoParking.setTotalQuantityState(cursorInfo.getInt(8));


                int counterReservedState = 0;
//                calculate free state parking
//                {
//                    if (dm_infoParking.getParkingType().equals(HASHIEEI_PARKING_TYPE)) {
//                        Cursor cursorFreeState = db.rawQuery("SELECT * FROM " + TABLE_RESERVE_PARKING_HASHIEEI + " WHERE " + CLM_PARKING_ID + " = " + dm_infoParking.getId(), null);
//                        cursorFreeState.moveToFirst();
//
//                        if (cursorFreeState.getCount() > 0) {
//                            while (!cursorFreeState.isAfterLast()) {
//                                counterReservedState++;
//                                cursorFreeState.moveToNext();
//                            }
//                        }
//                        cursorFreeState.close();
//
//                    } else if (dm_infoParking.getParkingType().equals(OMOOMI_PARKING_TYPE)) {
                Cursor cursorFreeState = db.rawQuery("SELECT * FROM " + TABLE_RESERVE_PARKING + " WHERE " + CLM_PARKING_ID + " = " + dm_infoParking.getId(), null);
                cursorFreeState.moveToFirst();

                if (cursorFreeState.getCount() > 0) {
                    while (!cursorFreeState.isAfterLast()) {
                        counterReservedState++;
                        cursorFreeState.moveToNext();
                    }
                }
                cursorFreeState.close();

                int totalState = cursorInfo.getInt(8);
                Log.i(TAG, "getMyParking: " + totalState + " ====>" + counterReservedState);
                dm_infoParking.setFreeState(totalState - counterReservedState);

                cursorInfo.moveToNext();
            }
        }
        cursorInfo.close();
        db.close();
        return dm_infoParking;
    }

    public List<Dm_ReserveParking> setReservedParking(int parkingId) {
        List<Dm_ReserveParking> dm_reserveParkings = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_RESERVE_PARKING + " WHERE " + CLM_PARKING_ID + " = " + parkingId, null);
        cursorInfo.moveToFirst();
        if (cursorInfo.getCount() > 0) {
            Dm_ReserveParking dm_reserveParking = new Dm_ReserveParking();
            while (!cursorInfo.isAfterLast()) {

                dm_reserveParking.setParkingId(cursorInfo.getInt(1));
                dm_reserveParking.setNumberState(cursorInfo.getInt(2));
                dm_reserveParking.setFloorParking(cursorInfo.getInt(3));
                dm_reserveParking.setParkingType(cursorInfo.getString(4));
                dm_reserveParking.setTimeReserve(cursorInfo.getString(5));
                dm_reserveParking.setPricePayment(cursorInfo.getString(6));

                dm_reserveParkings.add(dm_reserveParking);
                cursorInfo.moveToNext();
            }
        }
        cursorInfo.close();
        db.close();
        return dm_reserveParkings;
    }

//    public List<Dm_FileInDB> getFileByName(String FileName) {
//
//
//        List<Dm_FileInDB> Works = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MY_FILES + " WHERE " + CLM_BASE_NAME + " LIKE '%" + aes_db.decrypt(FileName) + "%'", null);
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            while (!cursor.isAfterLast()) {
//                Dm_FileInDB work = new Dm_FileInDB();
//
//
//                work.setId(cursor.getInt(0));
//                work.setServerId(cursor.getInt(1));
//                work.setFileName(aes_db.decrypt(String.valueOf(cursor.getString(2))));
//                work.setEncryptedFileName(aes_db.decrypt(String.valueOf(cursor.getString(3))));
//                work.setEncryptedKey(aes_db.decrypt(String.valueOf(cursor.getString(4))));
//                work.setFileType(aes_db.decrypt(String.valueOf(cursor.getString(5))));
//                work.setFileSizeLong(cursor.getInt(6));
//                work.setFileSizeString(aes_db.decrypt(String.valueOf(cursor.getString(7))));
//                work.setEncryptedDate(aes_db.decrypt(String.valueOf(cursor.getString(8))));
//
////                work.setId(cursor.getInt(0));
////                work.setServerId(cursor.getInt(1));
////                work.setFileName(cursor.getString(2));
////                work.setEncryptedFileName(cursor.getString(3));
////                work.setEncryptedKey(cursor.getString(4));
////                work.setFileType(cursor.getString(5));
////                work.setFileSizeLong(cursor.getInt(6));
////                work.setFileSizeString(cursor.getString(7));
////                work.setEncryptedDate(cursor.getString(8));
//
//                Works.add(work);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        db.close();
//        return Works;
//    }

//    public void deleteFile(String encryptedFileName) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor_delete_file = db.rawQuery("DELETE FROM " + TABLE_MY_FILES + " WHERE " + CLM_ENCRYPTED_FILE_NAME + " = '" + aes_db.encrypt(encryptedFileName) + "'", null);
//        cursor_delete_file.moveToFirst();
//        if (cursor_delete_file.getCount() > 0) {
//            Log.i(TAG, "deleteFile: " + 0);
//            while (!cursor_delete_file.isAfterLast()) {
//                cursor_delete_file.moveToNext();
//                Log.i(TAG, "deleteFile: " + 1);
//            }
//        }
//        cursor_delete_file.close();
//
//        db.close();
//    }

    public String getDbPath() {
        return context.getDatabasePath(DB_NAME).getPath();
    }
}
