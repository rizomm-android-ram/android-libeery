package fr.grabarski.libeery.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Robin on 03/04/2015.
 */

@Data
public class Category {

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
