package com.rizomm.ram.libeery.dao;

import com.rizomm.ram.libeery.event.listener.IDaoResponseListener;
import com.rizomm.ram.libeery.model.FavoriteBeer;

import java.util.List;

/**
 * Created by Amaury on 13/05/2015.
 */
public interface IFavoriteBeersDAO {

    public void addDaoResponseEventListener(IDaoResponseListener listener);

    /**
     * Récupère la liste des bières favorites.
     * @return
     */
    public List<FavoriteBeer> getFavoriteBeers();

    /**
     * Supprime une bière des favories
     * @param beerToDelete
     */
    public void deleteBeer(FavoriteBeer beerToDelete);

    /**
     * Ajoute une bière aux favories.
     * @param beerToAdd
     */
    public void addBeerToFavorite(FavoriteBeer beerToAdd);

}
