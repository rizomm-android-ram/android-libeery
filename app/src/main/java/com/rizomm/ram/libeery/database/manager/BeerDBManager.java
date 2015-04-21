package com.rizomm.ram.libeery.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rizomm.ram.libeery.database.helper.BeerDBHelper;
import com.rizomm.ram.libeery.database.helper.DatabaseHelper;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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

    public void insert(Beer beer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BeerDBHelper.BEER_ID, beer.getId());
        contentValues.put(BeerDBHelper.NAME, beer.getName());
        contentValues.put(BeerDBHelper.DESCRIPTION, beer.getDescription());
        contentValues.put(BeerDBHelper.ABV, beer.getAbv());
        contentValues.put(BeerDBHelper.AVAILABLE_ID, beer.getAvailableId());
        contentValues.put(BeerDBHelper.STYLE_ID, beer.getStyleId());
        contentValues.put(BeerDBHelper.IS_ORGANIC, beer.getIsOrganic());
        contentValues.put(BeerDBHelper.STATUS, beer.getStatus());
        contentValues.put(BeerDBHelper.STATUS_DISPLAY, beer.getStatusDisplay());
        contentValues.put(BeerDBHelper.ORIGINAL_GRAVITY, beer.getOriginalGravity());
        contentValues.put(BeerDBHelper.CREATE_DATE, Constant.DATE_FORMAT.format(beer.getCreateDate()));
        contentValues.put(BeerDBHelper.UPDATE_DATE, Constant.DATE_FORMAT.format(beer.getUpdateDate()));
        contentValues.put(BeerDBHelper.GLASS_ID, beer.getGlass().getId());
        database.insert(BeerDBHelper.TABLE_NAME, null, contentValues);
    }

    public int update(long _id, String id, String name, String description, Float abv, int available_id, int style_id, boolean is_organic, String status, String status_display, Float original_gravity, Date createDate, Date updateDate, int glass_id) {
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
        contentValues.put(BeerDBHelper.CREATE_DATE, dateFormat.format(createDate));
        contentValues.put(BeerDBHelper.UPDATE_DATE, dateFormat.format(updateDate));
        contentValues.put(BeerDBHelper.GLASS_ID, glass_id);

        int i = database.update(BeerDBHelper.TABLE_NAME, contentValues, BeerDBHelper._ID + "=" + _id, null);

        return i;
    }

    public void delete(long _id) {
        database.delete(BeerDBHelper.TABLE_NAME, BeerDBHelper._ID + "=" + _id, null);
    }

    public ArrayList<Beer> getBeers() {
        ArrayList<Beer> persons = new ArrayList<>();
        String[] columns = new String[] {
                DatabaseHelper._ID,
                DatabaseHelper.BEER_ID,
                DatabaseHelper.NAME,
                DatabaseHelper.DESCRIPTION,
                DatabaseHelper.ABV,
                DatabaseHelper.AVAILABLE_ID,
                DatabaseHelper.STYLE_ID,
                DatabaseHelper.IS_ORGANIC,
                DatabaseHelper.STATUS,
                DatabaseHelper.STATUS_DISPLAY,
                DatabaseHelper.ORIGINAL_GRAVITY,
                DatabaseHelper.GLASS_ID,
                DatabaseHelper.CREATE_DATE,
                DatabaseHelper.UPDATE_DATE,

        };

        Cursor cursor = database.query(DatabaseHelper.TABLE_BEER, columns, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Beer beer = new Beer();
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper._ID));
                    String beer_id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BEER_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                    String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION));
                    float abv = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.ABV));
                    int availableId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.AVAILABLE_ID));
                    int styleId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.STYLE_ID));
                    String isOrganic = cursor.getString(cursor.getColumnIndex(DatabaseHelper.IS_ORGANIC));
                    String status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.STATUS));
                    String statusDisplay = cursor.getString(cursor.getColumnIndex(DatabaseHelper.STATUS_DISPLAY));
                    float originalGravity = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.ORIGINAL_GRAVITY));
                    String createDate_str = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CREATE_DATE));
                    String updateDate_str = cursor.getString(cursor.getColumnIndex(DatabaseHelper.UPDATE_DATE));
                    int glassID = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.GLASS_ID));

                    Date createDate = new Date();
                    Date updateDate = new Date();
                    try {
                        createDate = Constant.DATE_FORMAT.parse(createDate_str);
                        updateDate = Constant.DATE_FORMAT.parse(updateDate_str);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    persons.add( Beer.builder()
                                    .id(id)
                                    .beer_id(beer_id)
                                    .name(name)
                                    .description(description)
                                    .abv(abv)
                                    .availableId(availableId)
                                    .styleId(styleId)
                                    .isOrganic(isOrganic)
                                    .status(status)
                                    .statusDisplay(statusDisplay)
                                    .originalGravity(originalGravity)
                                    .createDate(createDate)
                                    .updateDate(updateDate)
                                    .build()
                    );
                } while (cursor.moveToNext());
            }
        }

        return persons;

    }
}
