package com.rizomm.ram.libeery.dao;

import com.rizomm.ram.libeery.event.listener.IDaoResponseListener;
import com.rizomm.ram.libeery.model.Category;

import java.util.List;

/**
 * Created by Amaury on 13/05/2015.
 */
public interface ICategoryDAO {

    public void addDaoResponseEventListener(IDaoResponseListener listener);

    /**
     * Récupère la liste de toutes les catégories.
     * @return
     */
    public List<Category> getAllCategories();

}
