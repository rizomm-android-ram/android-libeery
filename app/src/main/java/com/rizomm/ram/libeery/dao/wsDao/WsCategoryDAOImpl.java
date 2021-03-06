package com.rizomm.ram.libeery.dao.wsDao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rizomm.ram.libeery.dao.ICategoryDAO;
import com.rizomm.ram.libeery.event.DAOCategoryResponseEvent;
import com.rizomm.ram.libeery.event.listener.CategoryResponseListener;
import com.rizomm.ram.libeery.event.listener.IDaoResponseListener;
import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.service.LibeeryRestService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Amaury on 15/05/2015.
 */
public class WsCategoryDAOImpl implements ICategoryDAO {

    private Gson mGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private RestAdapter mRestAdapter = new RestAdapter.Builder()
            .setEndpoint("http://robin.grabarski.fr/libeery/web/app_dev.php/api/")
            .setConverter(new GsonConverter(mGson))
            .build();

    private LibeeryRestService mService = mRestAdapter.create(LibeeryRestService.class);

    List<Category> categoryList = null;
    private List<IDaoResponseListener> mDaoResponseEventListenersList = new ArrayList<>();

    @Override
    public synchronized void addDaoResponseEventListener(IDaoResponseListener listener){
        if(!mDaoResponseEventListenersList.contains(listener)){
            mDaoResponseEventListenersList.add(listener);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        mService.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                fireCategoryResponse(categories);
            }

            @Override
            public void failure(RetrofitError error) {
                categoryList = null;
                System.out.println(error.toString());
            }
        });
        return categoryList;
    }

    /**
     * Propage un événeemnt de récupération des catégories.
     * @param list
     */
    private synchronized void fireCategoryResponse(List<Category> list) {
        DAOCategoryResponseEvent event = new DAOCategoryResponseEvent( this, list);
        Iterator listeners = mDaoResponseEventListenersList.iterator();
        while( listeners.hasNext() ) {
            ( (CategoryResponseListener) listeners.next() ).onCategoryResponse( event );
        }
    }
}
