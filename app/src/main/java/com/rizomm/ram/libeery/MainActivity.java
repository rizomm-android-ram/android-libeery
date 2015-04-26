package com.rizomm.ram.libeery;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rizomm.ram.libeery.adapter.SlidingTabsPagerAdapter;
import com.rizomm.ram.libeery.commonViews.SlidingTabLayout;
import com.rizomm.ram.libeery.database.manager.BeerDBManager;
import com.rizomm.ram.libeery.database.manager.CategoryDBManager;
import com.rizomm.ram.libeery.database.manager.GlassDBManager;
import com.rizomm.ram.libeery.database.manager.StyleDBManager;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.model.Glass;
import com.rizomm.ram.libeery.model.Style;
import com.rizomm.ram.libeery.service.BreweryDBService;
import com.rizomm.ram.libeery.utils.Constant;
import com.rizomm.ram.libeery.wrapper.CategoriesWrapper;
import com.rizomm.ram.libeery.wrapper.GlassewareWrapper;
import com.rizomm.ram.libeery.wrapper.StylesWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class MainActivity extends ActionBarActivity {

    private GlassDBManager glassDBManager;
    private CategoryDBManager categoryDBManager;
    private StyleDBManager styleDBManager;
    private BeerDBManager beerDBManager;
    private boolean isGlassFirstLaunch = true;
    private boolean isCategoryFirstLaunch = true;
    private boolean isStyleFirstLaunch = true;
    private boolean isBeerFirstLaunch = true;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private SlidingTabsPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération du viewPager défini dans la vue :
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        // Récupération du slidingtabLayout défini dans la vue :
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        adapter = new SlidingTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.brewerydb.com/v2")
                .setConverter(new GsonConverter(gson))
                .build();

        BreweryDBService service = restAdapter.create(BreweryDBService.class);

////        service.getBeerById("Fhj4Wo", Constant.API_KEY, new Callback<BeerWrapper>() {
////            @Override
////            public void success(BeerWrapper beerWrapper, Response response) {
////                System.out.println(beerWrapper.getBeer().getName());
////            }
////
////            @Override
////            public void failure(RetrofitError error) {
////                System.out.println(error.toString());
////            }
////        });
////
////       service.getBeerRandom(Constant.API_KEY, new Callback<BeerWrapper>() {
////           @Override
////           public void success(BeerWrapper beerWrapper, Response response) {
////               System.out.println(beerWrapper.getBeer().getName());
////           }
////
////           @Override
////           public void failure(RetrofitError error) {
////
////           }
////       });
//
//        service.getCategories(Constant.API_KEY, new Callback<CategoriesWrapper>() {
//            @Override
//            public void success(CategoriesWrapper categoriesWrapper, Response response) {
//                ArrayList<Category> categories = categoriesWrapper.getCategories();
//                buildCategories(categories);
////                for(Iterator<Category> categoryIterator = categories.iterator(); categoryIterator.hasNext(); ) {
////                    Category category = categoryIterator.next();
////                    System.out.println(category.getName());
////                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println("FAIL");
//            }
//        });
//
////        service.getCategoryById(2, Constant.API_KEY, new Callback<CategoryWrapper>() {
////            @Override
////            public void success(CategoryWrapper categoryWrapper, Response response) {
////                System.out.println(categoryWrapper.getCategory().getName());
////            }
////
////            @Override
////            public void failure(RetrofitError error) {
////
////            }
////        });
//
//        service.getStyles(Constant.API_KEY, new Callback<StylesWrapper>() {
//            @Override
//            public void success(StylesWrapper stylesWrapper, Response response) {
//                ArrayList<Style> styles = stylesWrapper.getStyles();
//                buildStyles(styles);
////                for(Iterator<Style> styleIterator = styles.iterator(); styleIterator.hasNext(); ) {
////                    Style style = styleIterator.next();
////                    System.out.println(style.getName());
////                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println("FAIL");
//            }
//        });
//
////        service.getStyleById(2, Constant.API_KEY, new Callback<StyleWrapper>() {
////            @Override
////            public void success(StyleWrapper styleWrapper, Response response) {
////                System.out.println(styleWrapper.getStyle().getName());
////            }
////
////            @Override
////            public void failure(RetrofitError error) {
////
////            }
////        });
//
//        service.getGlassware(Constant.API_KEY, new Callback<GlassewareWrapper>() {
//            @Override
//            public void success(GlassewareWrapper glassewareWrapper, Response response) {
//                ArrayList<Glass> glasseware = glassewareWrapper.getGlassware();
//                buildGlassware(glasseware);
////                for(Iterator<Glass> glassIterator = glasseware.iterator(); glassIterator.hasNext(); ) {
////                    Glass glass = glassIterator.next();
////                    System.out.println(glass.getName());
////                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println("FAIL");
//            }
//        });
//
////        service.getGlassById(8, Constant.API_KEY, new Callback<GlassWrapper>() {
////            @Override
////            public void success(GlassWrapper glassWrapper, Response response) {
////                System.out.println(glassWrapper.getGlass().getName());
////            }
////
////            @Override
////            public void failure(RetrofitError error) {
////
////            }
////        });

        System.out.println("======STYLES=====");
        styleDBManager = new StyleDBManager(this);
        List<Style> styles = styleDBManager.getStyles();
        for(Iterator<Style> styleIterator = styles.iterator(); styleIterator.hasNext(); ) {
            Style style = styleIterator.next();
            System.out.println(style);
        }

        System.out.println("====GLASSWARE====");
        glassDBManager = new GlassDBManager(this);
        List<Glass> glassware = glassDBManager.getGlassware();
        for(Iterator<Glass> glasswareIterator = glassware.iterator(); glasswareIterator.hasNext(); ) {
            Glass glass = glasswareIterator.next();
            System.out.println(glass);
        }

        System.out.println("===CATEGORIES===");
        categoryDBManager = new CategoryDBManager(this);
        List<Category> categories = categoryDBManager.getCategories();
        for(Iterator<Category> categoriyIterator = categories.iterator(); categoriyIterator.hasNext(); ) {
            Category category = categoriyIterator.next();
            System.out.println(category);
        }


        for(Iterator<Style> styleIterator = styles.iterator(); styleIterator.hasNext(); ) {
            Style style = styleIterator.next();

            System.out.println(style);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void buildGlassware(ArrayList<Glass> glasseware) {
        glassDBManager = new GlassDBManager(this);
        glassDBManager.open();
        System.out.println("isGlassDBFirstLaunch : " + isGlassFirstLaunch);
        if (isGlassFirstLaunch) {
            for(Iterator<Glass> glassIterator = glasseware.iterator(); glassIterator.hasNext(); ) {
                Glass glass = glassIterator.next();
                System.out.println(glass.getName());
                glassDBManager.insert(glass.getId(), glass.getName(), glass.getCreateDate());
            }
//            SharedPreferences settings = this.getSharedPreferences(Constant.PREFS_NAME, 0);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putBoolean("isGlassDBFirstLaunch", false);
//            isGlassFirstLaunch = false;
//            editor.commit();
            isGlassFirstLaunch = false;
        }

    }

    private void buildCategories(ArrayList<Category> categories) {
        categoryDBManager = new CategoryDBManager(this);
        categoryDBManager.open();
        System.out.println("isCategoryDBFirstLaunch : " + isCategoryFirstLaunch);
        if (isCategoryFirstLaunch) {
            for(Iterator<Category> categoryIterator = categories.iterator(); categoryIterator.hasNext(); ) {
                Category category = categoryIterator.next();
                System.out.println(category.getName());
                if (category.getName() != "") {
                    categoryDBManager.insert(category.getId(), category.getName(), category.getCreateDate());
                }
            }
//            SharedPreferences settings = this.getSharedPreferences(Constant.PREFS_NAME, 0);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putBoolean("isCategoryDBFirstLaunch", false);
//            isCategoryFirstLaunch = false;
//            editor.commit();
            isCategoryFirstLaunch = false;
        }

    }

    private void buildStyles(ArrayList<Style> styles) {
        styleDBManager = new StyleDBManager(this);
        styleDBManager.open();
        System.out.println("isStyleFirstLaunch : " + isStyleFirstLaunch);
        if (isStyleFirstLaunch) {
            for(Iterator<Style> styleIterator = styles.iterator(); styleIterator.hasNext(); ) {
                Style style = styleIterator.next();
                System.out.println(style.getName());
                if (style.getName() != "") {
                    styleDBManager.insert(style.getId(), style.getCategory().getId(),style.getName(), style.getCreateDate(), style.getShortname(), style.getDescription(), style.getIbuMin(), style.getIbuMax(), style.getAbvMin(), style.getAbvMax(), style.getSrmMin(), style.getSrmMax(), style.getOgMin(), style.getFgMin(), style.getFgMax(), style.getUpdateDate());
                }
            }
//            SharedPreferences settings = this.getSharedPreferences(Constant.PREFS_NAME, 0);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putBoolean("isStyleFirstLaunch", false);
//            isStyleFirstLaunch = false;
//            editor.commit();
            isStyleFirstLaunch = false;
        }

    }

    private void buildBeers(ArrayList<Beer> beers) {
        beerDBManager = new BeerDBManager(this);
        beerDBManager.open();
        System.out.println("isBeerFirstLaunch : " + isBeerFirstLaunch);
        if (isStyleFirstLaunch) {
            for(Iterator<Beer> beerIterator = beers.iterator(); beerIterator.hasNext(); ) {
                Beer beer = beerIterator.next();
                System.out.println(beer);
                if (beer.getName() != "") {
                    beerDBManager.insert(beer);
                }
            }
            isBeerFirstLaunch = false;
        }

    }
}