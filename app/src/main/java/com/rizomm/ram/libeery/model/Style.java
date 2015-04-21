package com.rizomm.ram.libeery.model;

import java.util.Date;

import lombok.Data;

/**
 * Created by Robin on 03/04/2015.
 */

@lombok.Data
public class Style {

    private int id;
    private Category category;
    private String name;
    private String shortname;
    private String description;
    private float ibuMin;
    private float ibuMax;
    private float abvMin;
    private float abvMax;
    private float srmMin;
    private float srmMax;
    private float ogMin;
    private float fgMin;
    private float fgMax;
    private Date createDate;
    private Date updateDate;

    public Style(){ }

    public Style(int id, int category_id, String name, Date createDate, String shortName, String description, float ibuMin, float ibuMax, float abvMin, float abvMax, float srmMin, float srmMax, float ogMin, float fgMin, float fgMax, Date updateDate) {
        this.id = id;
        this.id = category_id;
        this.name = name;
        this.createDate = createDate;
        this.shortname = shortName;
        this.description = description;
        this.ibuMin = ibuMin;
        this.ibuMax = ibuMax;
        this.abvMin = abvMin;
        this.abvMax = abvMax;
        this.srmMin = srmMin;
        this.srmMax = srmMax;
        this.ogMin = ogMin;
        this.fgMin = fgMin;
        this.fgMax = fgMax;
        this.updateDate = updateDate;
    }

}
