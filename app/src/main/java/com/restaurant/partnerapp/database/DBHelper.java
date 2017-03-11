package com.restaurant.partnerapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.camera2.DngCreator;

/**
 * Created by vJ on 3/7/17.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "reservations.db";

    public static final int DATABASE_VERSION = 1;
    private static DBHelper mInstance;


    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new DBHelper(context);
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE_AVAILABILITY_TABLE = "CREATE TABLE " +
                TableContract.TableAvailability.TABLE_NAME + " (" +
                TableContract.TableAvailability._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TableContract.TableAvailability.AVAILABLE + " boolean default 0 check("+ TableContract.TableAvailability.AVAILABLE + " in (0,1))," +
                TableContract.TableAvailability.CUSTOMER_FIRST_NAME + " TEXT," +
                TableContract.TableAvailability.CUSTOMER_LAST_NAME + " TEXT," +
                TableContract.TableAvailability.CUSTOMER_ID + " INTEGER," +
                TableContract.TableAvailability.TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_AVAILABILITY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
