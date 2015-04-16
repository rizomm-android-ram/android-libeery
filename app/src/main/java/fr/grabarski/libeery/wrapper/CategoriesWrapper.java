package fr.grabarski.libeery.wrapper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import fr.grabarski.libeery.model.Category;

/**
 * Created by Robin on 10/04/2015.
 */
public class CategoriesWrapper {

    private String message;
    @SerializedName("data")
    private ArrayList<Category> categories;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
