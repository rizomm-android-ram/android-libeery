package com.rizomm.ram.libeery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rizomm.ram.libeery.R;
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
import com.rizomm.ram.libeery.service.LibeeryRestService;
import com.rizomm.ram.libeery.utils.Constant;
import com.rizomm.ram.libeery.wrapper.BeerWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class MainActivity extends ActionBarActivity {

    private SlidingTabsPagerAdapter adapter;

    @InjectView(R.id.sliding_tabs) SlidingTabLayout slidingTabLayout;
    @InjectView(R.id.viewPager) ViewPager viewPager;
    @InjectView(R.id.add_button) FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération des éléments graphiques via ButterKnife :
        ButterKnife.inject(this);

        // Ajout de l'adapter et de des vues qu'il contient au tabLayout
        adapter = new SlidingTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://robin.grabarski.fr/libeery/web/app_dev.php/api/")
                .setConverter(new GsonConverter(gson))
                .build();

        LibeeryRestService service = restAdapter.create(LibeeryRestService.class);
//
//        service.getCountBeer(new Callback<String>() {
//            @Override
//            public void success(String countBeer, Response response) {
//                System.out.println(countBeer);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });
//
//        service.getBeerById(2, new Callback<Beer>() {
//            @Override
//            public void success(Beer beer, Response response) {
//                System.out.println(beer);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });
//
//        service.getRandomBeer(new Callback<Beer>() {
//            @Override
//            public void success(Beer beer, Response response) {
//                System.out.println(beer);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });

//        service.getBeersByName("leffe", new Callback<List<Beer>>() {
//            @Override
//            public void success(List<Beer> beers, Response response) {
//                for (Iterator<Beer> beerIterator = beers.iterator(); beerIterator.hasNext(); ) {
//                    Beer beer = beerIterator.next();
//                    System.out.println(beer);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });

        service.getBeersByStyle(42, new Callback<List<Beer>>() {
            @Override
            public void success(List<Beer> beers, Response response) {
                for (Iterator<Beer> beerIterator = beers.iterator(); beerIterator.hasNext(); ) {
                    Beer beer = beerIterator.next();
                    System.out.println(beer);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.toString());
            }
        });

//        service.getStyles(new Callback<List<Style>>() {
//            @Override
//            public void success(List<Style> styles, Response response) {
//                for (Iterator<Style> styleIterator = styles.iterator(); styleIterator.hasNext(); ) {
//                    Style style = styleIterator.next();
//                    System.out.println(style);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });
//
//        service.getStyleById(2, new Callback<Style>() {
//            @Override
//            public void success(Style style, Response response) {
//                System.out.println(style);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });

        service.getStyleByCategory(12, new Callback<List<Style>>() {
            @Override
            public void success(List<Style> styles, Response response) {
                for (Iterator<Style> styleIterator = styles.iterator(); styleIterator.hasNext(); ) {
                    Style style = styleIterator.next();
                    System.out.println(style);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.toString());
            }
        });

        service.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                for (Iterator<Category> categoryIterator = categories.iterator(); categoryIterator.hasNext(); ) {
                    Category category = categoryIterator.next();
                    System.out.println(category);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getUrl());
                System.out.println(error.toString());
            }
        });
//
//        service.getCategoryById(2, new Callback<Category>() {
//            @Override
//            public void success(Category category, Response response) {
//                System.out.println(category);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });
//
//        service.getGlassware(new Callback<List<Glass>>() {
//            @Override
//            public void success(List<Glass> glassware, Response response) {
//                for (Iterator<Glass> glassIterator = glassware.iterator(); glassIterator.hasNext(); ) {
//                    Glass glass = glassIterator.next();
//                    System.out.println(glass);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });
//
//        service.getGlassById(2, new Callback<Glass>() {
//            @Override
//            public void success(Glass glass, Response response) {
//                System.out.println(glass);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.toString());
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_random_beer) {
            // On démarre l'activité permettant d'afficher une bière aléatoire :
            Intent randomBeerIntent = new Intent(this, RandomBeerActivity.class);
            startActivity(randomBeerIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.add_button)
    /**
     * Clic sur le bouton permettant d'ajouter une bière.
     */
    public void onAddButtonClick(){
        Intent addBeerIntent = new Intent(this, AddBeerActivity.class);
        startActivityForResult(addBeerIntent, Constant.ADD_BEER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.ADD_BEER_REQUEST){
            if(resultCode == Constant.RESULT_CODE_OK){
                Toast.makeText(this, R.string.addedBeerMessageOK, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, R.string.addedBeerMessageKO, Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}