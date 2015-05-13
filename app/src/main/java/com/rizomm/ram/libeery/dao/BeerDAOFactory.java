package com.rizomm.ram.libeery.dao;

import com.rizomm.ram.libeery.dao.wsDao.WsBeerDAOImpl;

/**
 * Created by Amaury on 13/05/2015.
 */
public class BeerDAOFactory {

    /**
     * Récupère un DAO pour gérer les bières.
     * @return
     */
    public IBeersDAO getBeerDao(){
        return new WsBeerDAOImpl();
    }

}
