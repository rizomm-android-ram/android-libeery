package com.rizomm.ram.libeery.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rizomm.ram.libeery.database.helper.DatabaseHelper;
import com.rizomm.ram.libeery.model.Style;
import com.rizomm.ram.libeery.utils.Constant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Robin on 15/04/2015.
 */
public class StyleDBManager {

    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase database;

    public StyleDBManager(Context context) {
        this.context = context;
    }

    public StyleDBManager open()  throws SQLException {
        System.out.println("open database");
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public void insert(int id, int category_id, String name, Date createDate, String shortName, String description, float ibuMin, float ibuMax, float abvMin, float abvMax, float srmMin, float srmMax, float ogMin, float fgMin, float fgMax, Date updateDate){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper._ID, id);
        contentValues.put(DatabaseHelper.CATEGORY_ID, category_id);
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.CREATE_DATE, Constant.DATE_FORMAT.format(createDate));
        contentValues.put(DatabaseHelper.SHORT_NAME, shortName);
        contentValues.put(DatabaseHelper.DESCRIPTION, description);
        contentValues.put(DatabaseHelper.IBU_MIN, ibuMin);
        contentValues.put(DatabaseHelper.IBU_MAX, ibuMax);
        contentValues.put(DatabaseHelper.ABV_MIN, abvMin);
        contentValues.put(DatabaseHelper.ABV_MAX, abvMax);
        contentValues.put(DatabaseHelper.SRM_MIN, srmMin);
        contentValues.put(DatabaseHelper.SRM_MAX, srmMax);
        contentValues.put(DatabaseHelper.OG_MIN, ogMin);
        contentValues.put(DatabaseHelper.FG_MIN, fgMin);
        contentValues.put(DatabaseHelper.FG_MAX, fgMax);
        if (updateDate != null) {
            contentValues.put(DatabaseHelper.UPDATE_DATE, Constant.DATE_FORMAT.format(updateDate));
        }

        database.insert(DatabaseHelper.TABLE_STYLE, null, contentValues);
    }

    public int update(long _id, int category_id, String name, Date createDate, String shortName, String description, float ibuMin, float ibuMax, float abvMin, float abvMax, float srmMin, float srmMax, float ogMin, float fgMin, float fgMax, Date updateDate){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CATEGORY_ID, category_id);
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.CREATE_DATE, Constant.DATE_FORMAT.format(createDate));
        contentValues.put(DatabaseHelper.SHORT_NAME, shortName);
        contentValues.put(DatabaseHelper.DESCRIPTION, description);
        contentValues.put(DatabaseHelper.IBU_MIN, ibuMin);
        contentValues.put(DatabaseHelper.IBU_MAX, ibuMax);
        contentValues.put(DatabaseHelper.ABV_MIN, abvMin);
        contentValues.put(DatabaseHelper.ABV_MAX, abvMax);
        contentValues.put(DatabaseHelper.SRM_MIN, srmMin);
        contentValues.put(DatabaseHelper.SRM_MAX, srmMax);
        contentValues.put(DatabaseHelper.OG_MIN, ogMin);
        contentValues.put(DatabaseHelper.FG_MIN, fgMin);
        contentValues.put(DatabaseHelper.FG_MAX, fgMax);
        if (updateDate != null) {
            contentValues.put(DatabaseHelper.UPDATE_DATE, Constant.DATE_FORMAT.format(updateDate));
        }

        int i = database.update(DatabaseHelper.TABLE_STYLE, contentValues, DatabaseHelper._ID + "=" + _id, null);

        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_STYLE, DatabaseHelper._ID + "=" + _id, null);
    }

    public ArrayList<Style> getStyles() {
        this.open();
        ArrayList<Style> styles = new ArrayList<>();
        String[] columns = new String[]{
                DatabaseHelper._ID,
                DatabaseHelper.CATEGORY_ID,
                DatabaseHelper.NAME,
                DatabaseHelper.CREATE_DATE,
                DatabaseHelper.SHORT_NAME,
                DatabaseHelper.DESCRIPTION,
                DatabaseHelper.IBU_MIN,
                DatabaseHelper.IBU_MAX,
                DatabaseHelper.ABV_MIN,
                DatabaseHelper.ABV_MAX,
                DatabaseHelper.SRM_MIN,
                DatabaseHelper.SRM_MAX,
                DatabaseHelper.OG_MIN,
                DatabaseHelper.FG_MIN,
                DatabaseHelper.FG_MAX,
                DatabaseHelper.UPDATE_DATE
        };

        Cursor cursor = database.query(DatabaseHelper.TABLE_STYLE, columns, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper._ID));
                    int category_id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CATEGORY_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                    String createDateStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CREATE_DATE));
                    String shortName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SHORT_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION));
                    float ibuMin = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.IBU_MIN));
                    float ibuMax = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.IBU_MAX));
                    float abvMin = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.ABV_MIN));
                    float abvMax = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.ABV_MIN));
                    float srmMin = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.SRM_MIN));
                    float srmMax = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.SRM_MAX));
                    float ogMin = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.OG_MIN));
                    float fgMin = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.FG_MIN));
                    float fgMax = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.FG_MAX));
                    String updateDateStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.UPDATE_DATE));
                    Date createDate = new Date();
                    Date updateDate = new Date();

                    try {
                        createDate = Constant.DATE_FORMAT.parse(createDateStr);
                        if (updateDateStr != null) {
                            updateDate = Constant.DATE_FORMAT.parse(updateDateStr);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    styles.add(new Style(id, category_id, name, createDate, shortName, description, ibuMin, ibuMax, abvMin, abvMax, srmMin, srmMax, ogMin, fgMin, fgMax, updateDate));
                } while (cursor.moveToNext());
            }
        }
        this.close();
        return styles;
    }
}
