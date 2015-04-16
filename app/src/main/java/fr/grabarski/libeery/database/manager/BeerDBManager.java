package fr.grabarski.libeery.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.grabarski.libeery.database.helper.BeerDBHelper;
import fr.grabarski.libeery.utils.Constant;

/**
 * Created by Robin on 11/04/2015.
 */
public class BeerDBManager {

    private BeerDBHelper beerDBHelper;
    private Context context;
    private SQLiteDatabase database;

    public BeerDBManager(Context c) { context = c;}

    public BeerDBManager open() throws SQLException {
        beerDBHelper = new BeerDBHelper(context);
        database = beerDBHelper.getWritableDatabase();
        return this;
    }

    public void close() { beerDBHelper.close();}

    public void insert(String id, String name, String description, Float abv, int available_id, int style_id, boolean is_organic, String status, String status_display, Float original_gravity, Date create_date, Date update_date, int glass_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BeerDBHelper.BEER_ID, id);
        contentValues.put(BeerDBHelper.NAME, name);
        contentValues.put(BeerDBHelper.DESCRIPTION, description);
        contentValues.put(BeerDBHelper.ABV, abv);
        contentValues.put(BeerDBHelper.AVAILABLE_ID, available_id);
        contentValues.put(BeerDBHelper.STYLE_ID, style_id);
        contentValues.put(BeerDBHelper.IS_ORGANIC, is_organic);
        contentValues.put(BeerDBHelper.STATUS, status);
        contentValues.put(BeerDBHelper.STATUS_DISPLAY, status_display);
        contentValues.put(BeerDBHelper.ORIGINAL_GRAVITY, original_gravity);
        contentValues.put(BeerDBHelper.CREATE_DATE, Constant.DATE_FORMAT.format(create_date));
        contentValues.put(BeerDBHelper.UPDATE_DATE, Constant.DATE_FORMAT.format(update_date));
        contentValues.put(BeerDBHelper.GLASS_ID, glass_id);
        database.insert(BeerDBHelper.TABLE_NAME, null, contentValues);
    }

    public int update(long _id, String id, String name, String description, Float abv, int available_id, int style_id, boolean is_organic, String status, String status_display, Float original_gravity, Date create_date, Date update_date, int glass_id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues contentValues = new ContentValues();
        contentValues.put(BeerDBHelper.BEER_ID, id);
        contentValues.put(BeerDBHelper.NAME, name);
        contentValues.put(BeerDBHelper.DESCRIPTION, description);
        contentValues.put(BeerDBHelper.ABV, abv);
        contentValues.put(BeerDBHelper.AVAILABLE_ID, available_id);
        contentValues.put(BeerDBHelper.STYLE_ID, style_id);
        contentValues.put(BeerDBHelper.IS_ORGANIC, is_organic);
        contentValues.put(BeerDBHelper.STATUS, status);
        contentValues.put(BeerDBHelper.STATUS_DISPLAY, status_display);
        contentValues.put(BeerDBHelper.ORIGINAL_GRAVITY, original_gravity);
        contentValues.put(BeerDBHelper.CREATE_DATE, dateFormat.format(create_date));
        contentValues.put(BeerDBHelper.UPDATE_DATE, dateFormat.format(update_date));
        contentValues.put(BeerDBHelper.GLASS_ID, glass_id);

        int i = database.update(BeerDBHelper.TABLE_NAME, contentValues, BeerDBHelper._ID + "=" + _id, null);

        return i;
    }

    public void delete(long _id) {
        database.delete(BeerDBHelper.TABLE_NAME, BeerDBHelper._ID + "=" + _id, null);
    }

//    public ArrayList<Beer> getBeers() {
//        ArrayList<Beer> persons = new ArrayList<>();
//        String[] columns = new String[] {
//                BeerDB._ID,
//                BeerDB.BEER_ID,
//                BeerDB.NAME,
//                BeerDB.DESCRIPTION,
//                BeerDB.ABV,
//                BeerDB.AVAILABLE_ID,
//                BeerDB.STYLE_ID,
//                BeerDB.IS_ORGANIC,
//                BeerDB.STATUS,
//                BeerDB.STATUS_DISPLAY,
//                BeerDB.ORIGINAL_GRAVITY,
//                BeerDB.CREATE_DATE,
//                BeerDB.UPDATE_DATE,
//                BeerDB.GLASS_ID
//        };
//        Cursor cursor = database.query(BeerDB.TABLE_NAME, columns, null, null, null, null, null);
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    Beer beer = new Beer();
//                    beer.setId(cursor.getString(cursor.getColumnIndex(BeerDB.BEER_ID)));
//                    beer.setName(cursor.getString(cursor.getColumnIndex(BeerDB.NAME)));
//                    beer.setDescription(cursor.getString(cursor.getColumnIndex(BeerDB.DESCRIPTION)));
//                    beer.setAbv(cursor.getFloat(cursor.getColumnIndex(BeerDB.ABV)));
//                    beer.setAvailableId(cursor.getInt(cursor.getColumnIndex(BeerDB.AVAILABLE_ID)));
//                    beer.setStyleId(cursor.getInt(cursor.getColumnIndex(BeerDB.STYLE_ID)));
//                    beer.setIsOrganic(cursor.getInt(cursor.getColumnIndex(BeerDB.IS_ORGANIC)));
//
//
//                    persons.add(beer);
//                } while (cursor.moveToNext());
//            }
//        }
//
//        return persons;
//
//    }
}
