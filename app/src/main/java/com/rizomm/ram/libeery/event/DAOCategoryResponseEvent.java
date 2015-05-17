package com.rizomm.ram.libeery.event;

import com.rizomm.ram.libeery.model.Category;

import java.util.EventObject;
import java.util.List;

import lombok.Getter;

/**
 * Created by Amaury on 15/05/2015.
 */
public class DAOCategoryResponseEvent extends EventObject {

    @Getter
    private List<Category> categoryList;

    public DAOCategoryResponseEvent(Object source, List<Category> list) {
        super(source);
        categoryList = list;
    }
}
