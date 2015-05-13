package com.rizomm.ram.libeery.wrapper;

import com.google.gson.annotations.SerializedName;
import com.rizomm.ram.libeery.model.Beer;

import java.util.List;

/**
 * Created by Robin on 09/04/2015.
 */
public class BeerWrapper {

    private Beer beer;

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

}
