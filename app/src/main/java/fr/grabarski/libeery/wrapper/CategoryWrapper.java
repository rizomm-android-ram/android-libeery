package fr.grabarski.libeery.wrapper;

import com.google.gson.annotations.SerializedName;

import fr.grabarski.libeery.model.Category;

/**
 * Created by Robin on 10/04/2015.
 */
public class CategoryWrapper {

    private String message;
    @SerializedName("data")
    private Category category;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
