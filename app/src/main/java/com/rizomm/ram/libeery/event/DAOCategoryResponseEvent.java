package com.rizomm.ram.libeery.event;

import com.rizomm.ram.libeery.model.Category;

import java.util.EventObject;
import java.util.List;

/**
 * Created by Amaury on 15/05/2015.
 */
public class DAOCategoryResponseEvent extends EventObject {

    private List<Category> mCategoryList;

    public DAOCategoryResponseEvent(Object source, List<Category> list) {
        super(source);
        mCategoryList = list;
    }

    public List<Category> getCategoryList() {
        return mCategoryList;
    }
}
