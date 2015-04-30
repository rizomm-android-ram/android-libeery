package com.rizomm.ram.libeery.wrapper;

import com.google.gson.annotations.SerializedName;
import com.rizomm.ram.libeery.model.Glass;

import java.util.ArrayList;


/**
 * Created by Robin on 10/04/2015.
 */
public class GlassewareWrapper {

    private String message;
    @SerializedName("data")
    private ArrayList<Glass> glassware;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Glass> getGlassware() {
        return glassware;
    }

    public void setGlassware(ArrayList<Glass> glassware) {
        this.glassware = glassware;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
