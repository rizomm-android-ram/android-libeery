package com.rizomm.ram.libeery.event;

import com.rizomm.ram.libeery.model.Beer;

import java.util.EventObject;
import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Amaury on 14/05/2015.
 */
@Data
public class DAOResponseEvent extends EventObject {

    @Getter
    private Beer currentBeer;
    @Getter
    private List<Beer> beerList;

    public DAOResponseEvent(Object source, Beer b) {
        super(source);
        currentBeer = b;
    }

    public DAOResponseEvent(Object source, List<Beer> list) {
        super(source);
        beerList = list;
    }
}
