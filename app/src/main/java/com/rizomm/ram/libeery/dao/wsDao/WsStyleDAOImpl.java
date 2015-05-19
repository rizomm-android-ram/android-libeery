package com.rizomm.ram.libeery.dao.wsDao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rizomm.ram.libeery.dao.IStyleDAO;
import com.rizomm.ram.libeery.event.DAOStyleResponseEvent;
import com.rizomm.ram.libeery.event.listener.IDaoResponseListener;
import com.rizomm.ram.libeery.event.listener.StyleResponseListener;
import com.rizomm.ram.libeery.model.Style;
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
public class WsStyleDAOImpl implements IStyleDAO {

    private Gson mGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private RestAdapter mRestAdapter = new RestAdapter.Builder()
            .setEndpoint("http://robin.grabarski.fr/libeery/web/app_dev.php/api/")
            .setConverter(new GsonConverter(mGson))
            .build();

    private LibeeryRestService mService = mRestAdapter.create(LibeeryRestService.class);

    List<Style> styleList = null;
    private List<IDaoResponseListener> mDaoResponseEventListenersList = new ArrayList<>();

    @Override
    public synchronized void addDaoResponseEventListener(IDaoResponseListener listener){
        if(!mDaoResponseEventListenersList.contains(listener)){
            mDaoResponseEventListenersList.add(listener);
        }
    }

    @Override
    public List<Style> getStyleByCategory(int categoryId) {
        mService.getStyleByCategory(categoryId, new Callback<List<Style>>() {
            @Override
            public void success(List<Style> styles, Response response) {
                fireStyleResponse(styles);
            }

            @Override
            public void failure(RetrofitError error) {
                styleList = null;
                System.out.println(error.toString());
            }
        });
        return styleList;
    }

    /**
     * Propage un événement de récupération des styles
     * @param list
     */
    private synchronized void fireStyleResponse(List<Style> list) {
        DAOStyleResponseEvent event = new DAOStyleResponseEvent( this, list);
        Iterator listeners = mDaoResponseEventListenersList.iterator();
        while( listeners.hasNext() ) {
            ( (StyleResponseListener) listeners.next() ).onStyleResponse(event);
        }
    }
}
