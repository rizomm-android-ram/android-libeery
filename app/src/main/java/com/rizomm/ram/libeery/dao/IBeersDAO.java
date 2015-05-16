package com.rizomm.ram.libeery.dao;

import com.rizomm.ram.libeery.event.listener.IDaoResponseListener;
import com.rizomm.ram.libeery.model.Beer;

import java.util.List;

/**
 * Created by Amaury on 13/05/2015.
 */
public interface IBeersDAO {

    public void addDaoResponseEventListener(IDaoResponseListener listener);

    /**
     * récupère la liste de toutes les bières.
     * @return
     */
    public List<Beer> getAllBeers();

    /**
     * Récupère la liste des bières respectant le pattern fourni.
     * @param name
     * @return
     */
    public List<Beer> getBeersByName(String name);

    /**
     * Récupère une bière aléatoire
     * @return
     */
    public Beer getRandomBeer();

}
