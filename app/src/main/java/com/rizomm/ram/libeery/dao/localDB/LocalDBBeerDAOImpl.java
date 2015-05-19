package com.rizomm.ram.libeery.dao.localDB;

import android.content.Context;

import com.rizomm.ram.libeery.dao.IFavoriteBeersDAO;
import com.rizomm.ram.libeery.database.manager.FavoriteBeersLocalDBManager;
import com.rizomm.ram.libeery.event.listener.IDaoResponseListener;
import com.rizomm.ram.libeery.model.FavoriteBeer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amaury on 15/05/2015.
 */
public class LocalDBBeerDAOImpl implements IFavoriteBeersDAO {

    private Context mContext;
    private List<IDaoResponseListener> mDaoResponseEventListenersList = new ArrayList<>();

    @Override
    public void addDaoResponseEventListener(IDaoResponseListener listener) {
        if(!mDaoResponseEventListenersList.contains(listener)){
            mDaoResponseEventListenersList.add(listener);
        }
    }

    public void setContext(Context context){
        this.mContext = context;
    }

    @Override
    public List<FavoriteBeer> getFavoriteBeers() {
        List<FavoriteBeer> allBeersList ;
        FavoriteBeersLocalDBManager manager = new FavoriteBeersLocalDBManager(mContext);
        manager.open();
        allBeersList = manager.getAllfavoriteBeers();
        manager.close();
        return allBeersList ;
    }

    @Override
    public void deleteBeer(FavoriteBeer beerToDelete) {
        FavoriteBeersLocalDBManager manager = new FavoriteBeersLocalDBManager(mContext);
        manager.open();
        manager.delete(beerToDelete);
        manager.close();
    }

    @Override
    public void addBeerToFavorite(FavoriteBeer beerToAdd) {
        FavoriteBeersLocalDBManager manager = new FavoriteBeersLocalDBManager(mContext);
        manager.open();
        manager.insert(beerToAdd, beerToAdd.getSrcType());
        manager.close();
    }

    @Override
    public List<String> getFavoriteBeersIds() {
        List<String> idList ;
        FavoriteBeersLocalDBManager manager = new FavoriteBeersLocalDBManager(mContext);
        manager.open();
        idList = manager.getFavoriteBeersIds();
        manager.close();
        return idList;
    }
}
