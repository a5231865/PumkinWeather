package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hence on 2016/7/31.
 */
public class weatherDatabase {

    //SQL
    public static final String LOG_TAG = "Log";
    weatherHelper helper;

    public weatherDatabase(Context context) {
        Log.d(LOG_TAG, "create database");
        helper = new weatherHelper(context);
        Log.d(LOG_TAG, "create database2");
        SQLiteDatabase db = helper.getWritableDatabase();
        Log.d(LOG_TAG, "create database3");
    }

    public boolean insertData(String cityNameC) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(weatherHelper.CITY_NAMEC, cityNameC);
        long resp = db.insert(weatherHelper.TABLE_NAME, null, contentValues);
        Log.d(LOG_TAG, "after insert " + String.valueOf(resp));
        if (resp < 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteData(String cityName){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(weatherHelper.TABLE_NAME,weatherHelper.CITY_NAMEC+"=?",new String[]{cityName});
    }

    public boolean querryData(String cityName) {
        String[] col = {weatherHelper.CITY_NAMEC};
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.d(LOG_TAG, "qurry data"+cityName);
        Cursor cursor;
        cursor = db.query(weatherHelper.TABLE_NAME, col, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String citye = cursor.getString(0);
            if (citye.equals(cityName)) {
                Log.d(LOG_TAG, "cursor found ");
                return true;
            }
        }
        Log.d(LOG_TAG, "cursor not found ");
        return false;
    }

    public ArrayList<String> getAlldata() {
        ArrayList<String> cityNamelist = new ArrayList<>();
        String[] col = {weatherHelper.CITY_NAMEC};
        String cityname;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor;
        Log.d(LOG_TAG, "start to get data");
        cursor = db.query(weatherHelper.TABLE_NAME, col, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Log.d(LOG_TAG, "cursor moved");
            cityname = cursor.getString(0);
            cityNamelist.add(cityname);
        }

        return cityNamelist;
    }

    static class weatherHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "pumkinweather";
        private static final String TABLE_NAME = "WEATHERTABLE";
        private static final String CITY_NAMEC = "CITYC";
        private static final int DATE_BASE_VER = 1;


        weatherHelper(Context context) {

            super(context, DATABASE_NAME, null, DATE_BASE_VER);
            SQLiteDatabase db = getWritableDatabase();
            Log.d(LOG_TAG, "create database inner");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d(LOG_TAG, "Start to create table");
            try {
                String sql = "CREATE TABLE " + TABLE_NAME + " (" + CITY_NAMEC + " VARCHAR(10) );";
                db.execSQL(sql);
                Log.d(LOG_TAG, "DB created");

            } catch (SQLiteException se) {
                se.printStackTrace();
                Log.d(LOG_TAG, "failed in creation DB" + se.toString());
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
