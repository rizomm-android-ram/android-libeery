package com.rizomm.ram.libeery.dao;

import com.rizomm.ram.libeery.event.listener.IDaoResponseListener;
import com.rizomm.ram.libeery.model.Style;

import java.util.List;

/**
 * Created by Amaury on 13/05/2015.
 */
public interface IStyleDAO {

    public void addDaoResponseEventListener(IDaoResponseListener listener);

    /**
     * Récupère la liste des styles d'une catégorie.
     * @param categoryId
     * @return
     */
    public List<Style> getStyleByCategory(int categoryId);
}
