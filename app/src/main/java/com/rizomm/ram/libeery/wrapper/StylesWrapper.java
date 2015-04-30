package com.rizomm.ram.libeery.wrapper;

import com.google.gson.annotations.SerializedName;
import com.rizomm.ram.libeery.model.Style;

import java.util.ArrayList;

/**
 * Created by Robin on 10/04/2015.
 */
public class StylesWrapper {

    private String message;
    @SerializedName("data")
    private ArrayList<Style> styles;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Style> getStyles() {
        return styles;
    }

    public void setStyles(ArrayList<Style> styles) {
        this.styles = styles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
