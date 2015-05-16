package com.rizomm.ram.libeery.event.listener;

import com.rizomm.ram.libeery.event.DAOBeerResponseEvent;

/**
 * Created by Amaury on 14/05/2015.
 */
public interface RandomBeerResponseListener extends IDaoResponseListener {

    public void randomBeerResponse(DAOBeerResponseEvent event);

}
