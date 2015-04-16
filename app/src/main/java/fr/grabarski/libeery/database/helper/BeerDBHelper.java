package fr.grabarski.libeery.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.grabarski.libeery.utils.Constant;

/**
 * Created by Robin on 11/04/2015.
 */
public class BeerDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Beer";

    public static final String _ID = "_id";
    public static final String BEER_ID = "beer_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ABV = "abv";
    public static final String AVAILABLE_ID = "available_id";
    public static final String STYLE_ID = "style_id";
    public static final String IS_ORGANIC = "is_organic";
    public static final String STATUS = "status";
    public static final String STATUS_DISPLAY = "status_display";
    public static final String ORIGINAL_GRAVITY = "original_gravity";
    public static final String CREATE_DATE = "create_date";
    public static final String UPDATE_DATE = "update_date";
    public static final String GLASS_ID = "glass_id";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BEER_ID + "TEXT NOT NULL"
            + NAME + " TEXT NOT NULL, "
            + DESCRIPTION + " TEXT, "
            + ABV + " FLOAT, "
            + AVAILABLE_ID + " INTEGER, "
            + STYLE_ID + " INTEGER, "
            + IS_ORGANIC + " INTEGER, "
            + STATUS + " TEXT, "
            + STATUS_DISPLAY + " TEXT, "
            + ORIGINAL_GRAVITY + " FLOAT, "
            + CREATE_DATE + " DATE, "
            + UPDATE_DATE + " DATE, "
            + GLASS_ID + " INTEGER, "
            +");";

    public BeerDBHelper(Context context) {
        super(context, DatabaseHelper.DB_NAME, null, DatabaseHelper.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
