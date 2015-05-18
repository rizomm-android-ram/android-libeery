package com.rizomm.ram.libeery.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rizomm.ram.libeery.database.helper.BeerDBHelper;
import com.rizomm.ram.libeery.database.helper.DatabaseHelper;
import com.rizomm.ram.libeery.database.helper.FavoriteBeersLocalDBHelper;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.model.FavoriteBeer;
import com.rizomm.ram.libeery.model.Style;
import com.rizomm.ram.libeery.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amaury on 15/05/2015.
 */
public class FavoriteBeersLocalDBManager {

    private FavoriteBeersLocalDBHelper favoriteBeersLocalDBHelper;
    private Context context;
    private SQLiteDatabase database;

    public FavoriteBeersLocalDBManager(Context context) {
        this.context = context;
    }

    /**
     * Ouvre la connexion à la base de données.
     * @return
     * @throws SQLException
     */
    public FavoriteBeersLocalDBManager open() throws SQLException {
        favoriteBeersLocalDBHelper = new FavoriteBeersLocalDBHelper(context);
        database = favoriteBeersLocalDBHelper.getWritableDatabase();
        return this;
    }

    /**
     * Ferme la connexion à la base de données.
     */
    public void close() { favoriteBeersLocalDBHelper.close();}

    /**
     * Insère une nouvelle bière dans les favorites.
     * @param beer
     */
    public void insert(Beer beer, int type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteBeersLocalDBHelper.COL_ID, beer.getId());
        contentValues.put(FavoriteBeersLocalDBHelper.COL_NAME, beer.getName());
        contentValues.put(FavoriteBeersLocalDBHelper.COL_DESCRIPTION, beer.getDescription());
        contentValues.put(FavoriteBeersLocalDBHelper.COL_DEGRE, beer.getAbv());
        if(beer.getStyle() != null && beer.getStyle().getCategory() != null && beer.getStyle().getCategory().getName() != null){
            contentValues.put(FavoriteBeersLocalDBHelper.COL_CATEGORY, beer.getStyle().getCategory().getName());
        }else{
            contentValues.put(FavoriteBeersLocalDBHelper.COL_CATEGORY, "NA");
        }
        if(beer.getStyle() != null && beer.getStyle().getName() != null){
            contentValues.put(FavoriteBeersLocalDBHelper.COL_STYLE, beer.getStyle().getName());
        }else{
            contentValues.put(FavoriteBeersLocalDBHelper.COL_STYLE, "NA");
        }
        contentValues.put(FavoriteBeersLocalDBHelper.COL_ICON_SRC, beer.getLabel_icon());
        contentValues.put(FavoriteBeersLocalDBHelper.COL_MEDIUM_SRC, beer.getLabel_medium());
        contentValues.put(FavoriteBeersLocalDBHelper.COL_LARGE_SRC, beer.getLabel_large());
        contentValues.put(FavoriteBeersLocalDBHelper.COL_IMG_SRC_TYPE, type);
        database.insert(FavoriteBeersLocalDBHelper.TABLE_FAVORITE, null, contentValues);
    }

    /**
     * Récupère la liste de toutes les bières.
     * @return
     */
    public List<FavoriteBeer> getAllfavoriteBeers(){
        ArrayList<FavoriteBeer> favoriteBeerList = new ArrayList();
        String[] columns = new String[] {
                FavoriteBeersLocalDBHelper.COL_ID,
                FavoriteBeersLocalDBHelper.COL_NAME,
                FavoriteBeersLocalDBHelper.COL_CATEGORY,
                FavoriteBeersLocalDBHelper.COL_STYLE,
                FavoriteBeersLocalDBHelper.COL_DEGRE,
                FavoriteBeersLocalDBHelper.COL_DESCRIPTION,
                FavoriteBeersLocalDBHelper.COL_IMG_SRC_TYPE,
                FavoriteBeersLocalDBHelper.COL_ICON_SRC,
                FavoriteBeersLocalDBHelper.COL_MEDIUM_SRC,
                FavoriteBeersLocalDBHelper.COL_LARGE_SRC
        };

        Cursor cursor = database.query(FavoriteBeersLocalDBHelper.TABLE_FAVORITE, columns, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_ID));
                    String name = cursor.getString(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_NAME));
                    String category = cursor.getString(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_CATEGORY));
                    String style = cursor.getString(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_STYLE));
                    Float degre = cursor.getFloat(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_DEGRE));
                    String description = cursor.getString(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_DESCRIPTION));
                    int src_type = cursor.getInt(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_IMG_SRC_TYPE));
                    String src_icon = cursor.getString(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_ICON_SRC));
                    String src_medium = cursor.getString(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_MEDIUM_SRC));
                    String src_large = cursor.getString(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_LARGE_SRC));

                    Category c = new Category();
                    c.setName(category);

                    Style s = new Style();
                    s.setName(style);
                    s.setCategory(c);

                    // Création de l'objet FavoriteBeer :
                    Beer b = Beer.builder().id(_id)
                            .name(name)
                            .style(s)
                            .abv(degre)
                            .description(description)
                            .label_icon(src_icon)
                            .label_medium(src_medium)
                            .label_large(src_large)
                            .build();
                    FavoriteBeer favoriteBeer = new FavoriteBeer();
                    favoriteBeer = favoriteBeer.beerToFavoriteBeer(b, src_type);

                    // Ajout de la bière à la liste des favorites :
                    favoriteBeerList.add(favoriteBeer);

                }while (cursor.moveToNext());
            }
        }

        return favoriteBeerList;
    }

    /**
     * Supprime une bière de la base de données locale.
     * @param beerToDelete La bière a supprimer.
     */
    public void delete(FavoriteBeer beerToDelete){
        String where = "";
        String[] array = {};
        List<String> list = new ArrayList<>();

        // Le nom de la bière est un critère obligatoire.
        // S'il n'est pas présent, on ne supprime rien.
        // Construction de la clause where et du tableau d'arguments
        if(beerToDelete.getName() != null && !beerToDelete.getName().isEmpty()){
            where += FavoriteBeersLocalDBHelper.COL_NAME + "= ? ";
            list.add(beerToDelete.getName());

            if(beerToDelete.getStyle() != null && beerToDelete.getStyle().getName() != null && !beerToDelete.getStyle().getName().isEmpty()){
                where += "AND "+FavoriteBeersLocalDBHelper.COL_STYLE + " = ? ";
                list.add(beerToDelete.getStyle().getName());
            }
            if(beerToDelete.getStyle() != null && beerToDelete.getStyle().getCategory() != null && beerToDelete.getStyle().getCategory().getName() != null && !beerToDelete.getStyle().getCategory().getName().isEmpty()){
                where += "AND "+FavoriteBeersLocalDBHelper.COL_CATEGORY+ " = ? ";
                list.add(beerToDelete.getStyle().getCategory().getName());
            }
            if(beerToDelete.getDescription() != null && !beerToDelete.getDescription().isEmpty()){
                where += "AND "+FavoriteBeersLocalDBHelper.COL_DESCRIPTION + " = ? ";
                list.add(beerToDelete.getDescription());
            }
            // Ordre de suppression dans la table :
            database.delete(FavoriteBeersLocalDBHelper.TABLE_FAVORITE, where, list.toArray(array));
        }
    }

    /**
     * Récupère la liste des ID des bières favorites
     * @return
     */
    public List<String> getFavoriteBeersIds(){
        List<String> idList = new ArrayList<>();
        String[] columns = new String[] {FavoriteBeersLocalDBHelper.COL_ID};
        Cursor cursor = database.query(FavoriteBeersLocalDBHelper.TABLE_FAVORITE,columns, null, null,null,null,null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(FavoriteBeersLocalDBHelper.COL_ID));
                    idList.add(id);
                }while (cursor.moveToNext());
            }
        }
        return idList;
    }
}
