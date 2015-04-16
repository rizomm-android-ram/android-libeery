package fr.grabarski.libeery.wrapper;

import com.google.gson.annotations.SerializedName;

import fr.grabarski.libeery.model.Beer;

/**
 * Created by Robin on 09/04/2015.
 */
public class BeerWrapper {

    private String message;
    @SerializedName("data")
    private Beer beer;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
