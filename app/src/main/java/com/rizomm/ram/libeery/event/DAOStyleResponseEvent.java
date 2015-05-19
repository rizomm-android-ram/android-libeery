package com.rizomm.ram.libeery.event;

import com.rizomm.ram.libeery.model.Style;

import java.util.EventObject;
import java.util.List;

/**
 * Created by Amaury on 15/05/2015.
 */
public class DAOStyleResponseEvent extends EventObject {

    private List<Style> mStyleList;

    public DAOStyleResponseEvent(Object source, List<Style> list) {
        super(source);
        mStyleList = list;
    }

    public List<Style> getStyleList() {
        return mStyleList;
    }
}
