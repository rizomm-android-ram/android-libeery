package fr.grabarski.libeery.service;


import fr.grabarski.libeery.wrapper.BeerWrapper;
import fr.grabarski.libeery.wrapper.CategoriesWrapper;
import fr.grabarski.libeery.wrapper.CategoryWrapper;
import fr.grabarski.libeery.wrapper.GlassWrapper;
import fr.grabarski.libeery.wrapper.GlassewareWrapper;
import fr.grabarski.libeery.wrapper.StyleWrapper;
import fr.grabarski.libeery.wrapper.StylesWrapper;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Robin on 09/04/2015.
 */
public interface BreweryDBService {

    @GET("/beers/")
    void getBeersByStyleId(@Query("styleId") int styleId, @Query("key") String apiKey, Callback<BeerWrapper> beerWrapperCallback);

    @GET("/beer/{id}")
    void getBeerById(@Path("id") String beerId, @Query("key") String apiKey, Callback<BeerWrapper> beerWrapperCallback);

    @GET("/beer/random")
    void getBeerRandom(@Query("key") String apiKey, Callback<BeerWrapper> beerWrapperCallback);

    @GET("/categories/")
    void getCategories(@Query("key") String apiKey, Callback<CategoriesWrapper> categoriesWrapperCallback);

    @GET("/category/{id}")
    void getCategoryById(@Path("id") int categoryId, @Query("key") String apiKey, Callback<CategoryWrapper> categoryWrapperCallback);

    @GET("/styles/")
    void getStyles(@Query("key") String apiKey, Callback<StylesWrapper> stylesWrapperCallback);

    @GET("/style/{id}")
    void getStyleById(@Path("id") int styleId, @Query("key") String apiKey, Callback<StyleWrapper> styleWrapperCallback);

    @GET("/glassware/")
    void getGlassware(@Query("key") String apiKey, Callback<GlassewareWrapper> glassewareWrapperCallback);

    @GET("/glass/{id}")
    void getGlassById(@Path("id") int glassId, @Query("key") String apiKey, Callback<GlassWrapper> glassWrapperCallback);

}
