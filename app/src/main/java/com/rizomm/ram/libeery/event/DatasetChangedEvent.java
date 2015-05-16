package com.rizomm.ram.libeery.event;

import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.FavoriteBeer;

import java.util.EventObject;
import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Amaury on 14/05/2015.
 */
@Data
public class DatasetChangedEvent extends EventObject {

    @Getter
    FavoriteBeer favoriteBeerAdded;

    public DatasetChangedEvent(Object source) {
        super(source);
    }

    public DatasetChangedEvent(Object source, FavoriteBeer beer) {
        super(source);
        favoriteBeerAdded = beer;
    }
}
