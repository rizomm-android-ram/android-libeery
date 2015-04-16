package fr.grabarski.libeery.model;

import java.util.Date;


/**
 * Created by Robin on 10/04/2015.
 */

@lombok.Data
public class Glass {

    private int id;
    private String name;
    private Date createDate;

    public Glass() {
    }

    public Glass(int id, String name, Date create_date) {
        this.id = id;
        this.name = name;
        this.createDate = create_date;
    }

}
