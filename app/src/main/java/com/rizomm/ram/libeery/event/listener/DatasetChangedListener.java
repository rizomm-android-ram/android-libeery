package com.rizomm.ram.libeery.event.listener;

import com.rizomm.ram.libeery.event.DAOCategoryResponseEvent;
import com.rizomm.ram.libeery.event.DatasetChangedEvent;

/**
 * Created by Amaury on 15/05/2015.
 */
public interface DatasetChangedListener extends IDaoResponseListener {

    public void onDatasetChanged(DatasetChangedEvent event);
}
