package com.rizomm.ram.libeery.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amaury on 15/05/2015.
 */
public class FavoriteBeersLocalDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Libeery.DB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_FAVORITE = "TBL_FAVORITE";

    public static final String COL_ID = "_id";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_STYLE = "style";
    public static final String COL_CATEGORY = "category";
    public static final String COL_NAME = "name";
    public static final String COL_ICON_SRC = "icon_source";
    public static final String COL_MEDIUM_SRC = "medium_source";
    public static final String COL_LARGE_SRC = "large_source";
    public static final String COL_IMG_SRC_TYPE = "image_src_type";
    public static final String COL_DEGRE = "degre";

    /**
     * Enumération pour définir le type de source :<br />
     * LOCAL_SRC : quand l'image a été prise depuis l'appareil photo et est donc stockée en local<br />
     * REMOTE_SRC : quand l'image est issue d'un site web.
     */
    public enum IMAGE_TYPE {
        LOCAL_SRC(1), REMOTE_SRC(2);

        private final int value;
        private IMAGE_TYPE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final String CREATE_TBL_FAVORITE = "create table "+ TABLE_FAVORITE + "("
            + COL_ID + " INTEGER, "
            + COL_NAME + " TEXT,"
            + COL_CATEGORY + " TEXT, "
            + COL_STYLE + " TEXT, "
            + COL_DEGRE + " FLOAT, "
            + COL_DESCRIPTION + " TEXT, "
            + COL_IMG_SRC_TYPE+ " INT, "
            + COL_ICON_SRC + " TEXT, "
            + COL_MEDIUM_SRC + " TEXT, "
            + COL_LARGE_SRC + " TEXT); ";

    public FavoriteBeersLocalDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("123 Creation bdd");
        System.out.println(CREATE_TBL_FAVORITE);
        sqLiteDatabase.execSQL(CREATE_TBL_FAVORITE);
        sqLiteDatabase.execSQL("INSERT INTO "+TABLE_FAVORITE+" VALUES ('1','LEFFE','Belge','Blonde','12.5','Une bière connue','2','','','')" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        System.out.println("123 update bdd");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
    }
}
