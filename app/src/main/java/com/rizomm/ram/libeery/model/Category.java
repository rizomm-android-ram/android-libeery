package com.rizomm.ram.libeery.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Created by Robin on 03/04/2015.
 */

@Data
public class Category implements Serializable{

    private int id;
    private String name;
    private Date createDate;

    public Category() { }

    public Category(int id, String name, Date create_date) {
        this.id = id;
        this.name = name;
        this.createDate = create_date;
    }
}
