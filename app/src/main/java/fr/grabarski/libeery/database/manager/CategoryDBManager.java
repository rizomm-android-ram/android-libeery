package fr.grabarski.libeery.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import fr.grabarski.libeery.database.helper.DatabaseHelper;
import fr.grabarski.libeery.model.Category;
import fr.grabarski.libeery.utils.Constant;


/**
 * Created by Robin on 12/04/2015.
 */
public class CategoryDBManager {

    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase database;

    public CategoryDBManager(Context context) {
        this.context = context;
    }

    public CategoryDBManager open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public void insert(int id, String name, Date create_date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper._ID, id);
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.CREATE_DATE, Constant.DATE_FORMAT.format(create_date));
        database.insert(DatabaseHelper.TABLE_CATEGORY, null, contentValues);
    }

    public int update(long _id, String name, Date create_date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.CREATE_DATE, Constant.DATE_FORMAT.format(create_date));
        int i = database.update(DatabaseHelper.TABLE_CATEGORY, contentValues, DatabaseHelper._ID + "=" + _id, null);

        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_CATEGORY, DatabaseHelper._ID + "=" + _id, null);
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String[] columns = new String[]{
                DatabaseHelper._ID,
                DatabaseHelper.NAME,
                DatabaseHelper.CREATE_DATE
        };
        Cursor cursor = database.query(DatabaseHelper.TABLE_CATEGORY, columns, null, null, null, null, null);

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

        return categories;
    }
}
