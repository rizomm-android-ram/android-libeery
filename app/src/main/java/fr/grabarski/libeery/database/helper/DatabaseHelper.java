package fr.grabarski.libeery.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Robin on 14/04/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Libeery.DB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_GLASS = "Glass";
    public static final String TABLE_CATEGORY = "Category";
    public static final String TABLE_STYLE = "Style";

    public static final String _ID = "_id";
    public static final String CREATE_DATE = "create_date";
    public static final String UPDATE_DATE = "update_date";
    public static final String NAME = "name";

    public static final String CATEGORY_ID = "category_id";
    public static final String SHORT_NAME = "short_name";
    public static final String DESCRIPTION = "description";
    public static final String IBU_MIN = "ibu_min";
    public static final String IBU_MAX = "ibu_max";
    public static final String ABV_MIN = "abv_min";
    public static final String ABV_MAX = "abv_max";
    public static final String SRM_MIN = "srm_min";
    public static final String SRM_MAX = "srm_max";
    public static final String OG_MIN = "og_min";
    public static final String FG_MIN = "fg_min";
    public static final String FG_MAX = "fg_max";



    private static final String CREATE_TABLE_GLASS = "create table " + TABLE_GLASS + "("
            + _ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, "
            + CREATE_DATE + " DATE "
            +");";

    private static final String CREATE_TABLE_CATEGORY = "create table " + TABLE_CATEGORY + "("
            + _ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, "
            + CREATE_DATE + " DATE "
            +");";

    private static final String CREATE_TABLE_STYLE = "create table " + TABLE_STYLE + "("
            + _ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, "
            + CATEGORY_ID + " INTEGER, "
            + SHORT_NAME + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + IBU_MIN + " FLOAT, "
            + IBU_MAX + " FLOAT, "
            + ABV_MIN + " FLOAT, "
            + ABV_MAX + " FLOAT, "
            + SRM_MIN + " FLOAT, "
            + SRM_MAX + " FLOAT, "
            + OG_MIN + " FLOAT, "
            + FG_MIN + " FLOAT, "
            + FG_MAX + " FLOAT, "
            + CREATE_DATE + " DATE, "
            + UPDATE_DATE + " DATE "
            +");";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GLASS);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_STYLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_STYLE);

        onCreate(db);
    }
}
