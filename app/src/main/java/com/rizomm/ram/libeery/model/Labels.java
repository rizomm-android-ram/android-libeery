package com.rizomm.ram.libeery.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Robin on 09/04/2015.
 */
@Data
public class Labels implements Serializable {

    private String icon;
    private String medium;
    private String large;

}
