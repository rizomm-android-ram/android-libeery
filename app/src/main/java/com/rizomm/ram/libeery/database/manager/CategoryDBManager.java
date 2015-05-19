package com.rizomm.ram.libeery.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rizomm.ram.libeery.database.helper.DatabaseHelper;
import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.utils.Constant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Robin on 12/04/2015.
 */
public class CategoryDBManager {

    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public CategoryDBManager(Context context) {
        this.mContext = context;
    }

    public CategoryDBManager open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public void insert(int id, String name, Date create_date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper._ID, id);
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.CREATE_DATE, Constant.DATE_FORMAT.format(create_date));
        mDatabase.insert(DatabaseHelper.TABLE_CATEGORY, null, contentValues);
    }

    public int update(long _id, String name, Date create_date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.CREATE_DATE, Constant.DATE_FORMAT.format(create_date));
        int i = mDatabase.update(DatabaseHelper.TABLE_CATEGORY, contentValues, DatabaseHelper._ID + "=" + _id, null);

        return i;
    }

    public void delete(long _id) {
        mDatabase.delete(DatabaseHelper.TABLE_CATEGORY, DatabaseHelper._ID + "=" + _id, null);
    }

    public ArrayList<Category> getCategories() {
        this.open();
        ArrayList<Category> categories = new ArrayList<>();
        String[] columns = new String[]{
                DatabaseHelper._ID,
                DatabaseHelper.NAME,
                DatabaseHelper.CREATE_DATE
        };
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_CATEGORY, columns, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper._ID));
                    String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                    String create_date_str = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CREATE_DATE));
                    Date create_date = new Date();
                    try {
                        create_date = Constant.DATE_FORMAT.parse(create_date_str);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    categories.add(new Category(id, name, create_date));
                } while (cursor.moveToNext());
            }
        }

        this.close();

        return categories;
    }
}
