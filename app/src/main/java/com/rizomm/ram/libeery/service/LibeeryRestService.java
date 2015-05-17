package com.rizomm.ram.libeery.service;

import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.model.Glass;
import com.rizomm.ram.libeery.model.Style;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Robin on 12/05/2015.
 */
public interface LibeeryRestService {

    @GET("/beers/{id}")
    void getBeerById(@Path("id") int beerId, Callback<Beer> beerCallback);

    @GET("/beers/{style_id}/by/style")
    void getBeersByStyle(@Path("style_id") int styleId, Callback<List<Beer>> beersCallback);

    @GET("/count/beers")
    void getCountBeer(Callback<String> beerCountCallback);

    @GET("/random/beer")
    void getRandomBeer(Callback<Beer> beerCallback);

    @GET("/beers/{name}/by/name")
    void getBeersByName(@Path("name") String name, Callback<List<Beer>> beersCallback);

    @GET("/categories")
    void getCategories(Callback<List<Category>> categoriesCallback);

    @GET("/categories/{id}")
    void getCategoryById(@Path("id") int categoryId, Callback<Category> categoryCallback);

    @GET("/styles")
    void getStyles(Callback<List<Style>> stylesCallback);

    @GET("/styles/{id}")
    void getStyleById(@Path("id") int styleId, Callback<Style> styleCallback);

    @GET("/styles/{style_id}/by/category")
    void getStyleByCategory(@Path("style_id") int categoryId, Callback<List<Style>> stylesCallback);

    @GET("/glassware")
    void getGlassware(Callback<List<Glass>> glasswareCallback);

    @GET("/glasses/{id}")
    void getGlassById(@Path("id") int glassId, Callback<Glass> glassCallback);

}
