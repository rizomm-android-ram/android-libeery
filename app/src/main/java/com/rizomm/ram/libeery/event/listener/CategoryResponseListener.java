package com.rizomm.ram.libeery.event.listener;

import com.rizomm.ram.libeery.event.DAOCategoryResponseEvent;

/**
 * Created by Amaury on 15/05/2015.
 */
public interface CategoryResponseListener extends IDaoResponseListener {

    public void onCategoryResponse(DAOCategoryResponseEvent event);
}
