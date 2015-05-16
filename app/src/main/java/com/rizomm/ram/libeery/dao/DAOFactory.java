package com.rizomm.ram.libeery.dao;

import com.rizomm.ram.libeery.dao.localDB.LocalDBBeerDAOImpl;
import com.rizomm.ram.libeery.dao.wsDao.WsBeerDAOImpl;
import com.rizomm.ram.libeery.dao.wsDao.WsCategoryDAOImpl;
import com.rizomm.ram.libeery.dao.wsDao.WsStyleDAOImpl;

/**
 * Created by Amaury on 13/05/2015.
 */
public class DAOFactory {

    /**
     * Récupère un DAO pour gérer les bières.
     * @return
     */
    public IBeersDAO getBeerDao(){
        return new WsBeerDAOImpl();
    }

    /**
     * Récupère un DAO pour gérer les bières favorites.
     * @return
     */
    public IFavoriteBeersDAO getFavoriteBeersDao(){ return new LocalDBBeerDAOImpl();}

    /**
     * Récupère un DAO permettant de gérer les catégories
     * @return
     */
    public ICategoryDAO getCategoryDao(){
        return new WsCategoryDAOImpl();
    }

    /**
     * Récupère un DAO permettant de gérer les styles.
     * @return
     */
    public IStyleDAO getStyleDAO(){
        return new WsStyleDAOImpl();
    }

}
