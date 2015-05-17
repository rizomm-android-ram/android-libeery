package com.rizomm.ram.libeery.event;

import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.model.Style;

import java.util.EventObject;
import java.util.List;

import lombok.Getter;

/**
 * Created by Amaury on 15/05/2015.
 */
public class DAOStyleResponseEvent extends EventObject {

    @Getter
    private List<Style> styleList;

    public DAOStyleResponseEvent(Object source, List<Style> list) {
        super(source);
        styleList = list;
    }
}
