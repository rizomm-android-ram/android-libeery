package com.rizomm.ram.libeery.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * Created by Robin on 03/04/2015.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@Builder
public class Beer implements Serializable {

    private int id;
    private String beer_id;
    private String name;
    private String description;
    private float abv;
    private int availableId;
    private int styleId;
    private String isOrganic;
    private Labels labels;
    private String status;
    private String statusDisplay;
    private float originalGravity;
    private Date createDate;
    private Date updateDate;
    private Glass glass;
    private Available available;
    private Style style;

}
