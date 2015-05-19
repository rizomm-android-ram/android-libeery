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
public class DAOBeerResponseEvent extends EventObject {

    @Getter
    private Beer mCurrentBeer;
    @Getter
    private List<Beer> mBeerList;

    public DAOBeerResponseEvent(Object source, Beer b) {
        super(source);
        mCurrentBeer = b;
    }

    public DAOBeerResponseEvent(Object source, List<Beer> list) {
        super(source);
        mBeerList = list;
    }

    public Beer getCurrentBeer() {
        return mCurrentBeer;
    }

    public List<Beer> getBeerList() {
        return mBeerList;
    }
}
