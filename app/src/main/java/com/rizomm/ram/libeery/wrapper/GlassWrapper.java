package com.rizomm.ram.libeery.wrapper;

import com.google.gson.annotations.SerializedName;
import com.rizomm.ram.libeery.model.Glass;


/**
 * Created by Robin on 10/04/2015.
 */
public class GlassWrapper {

    private String message;
    @SerializedName("data")
    private Glass glass;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Glass getGlass() {
        return glass;
    }

    public void setGlass(Glass glass) {
        this.glass = glass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
