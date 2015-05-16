package com.rizomm.ram.libeery.event.listener;

import com.rizomm.ram.libeery.event.DAOStyleResponseEvent;

/**
 * Created by Amaury on 15/05/2015.
 */
public interface StyleResponseListener extends IDaoResponseListener {

    public void onStyleResponse(DAOStyleResponseEvent event);
}
