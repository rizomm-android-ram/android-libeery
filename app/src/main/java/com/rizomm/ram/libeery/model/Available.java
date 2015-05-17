package com.rizomm.ram.libeery.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Robin on 03/04/2015.
 */
@Data
public class Available implements Serializable{

    private int id;
    private String name;
    private String description;

    public Available() { }

}
