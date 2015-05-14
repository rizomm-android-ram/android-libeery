package com.rizomm.ram.libeery.dao.wsDao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rizomm.ram.libeery.dao.IBeersDAO;
import com.rizomm.ram.libeery.event.DAOResponseEvent;
import com.rizomm.ram.libeery.event.IDaoResponseListener;
import com.rizomm.ram.libeery.event.RandomBeerResponseListener;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.model.Glass;
import com.rizomm.ram.libeery.model.Labels;
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
 * Created by Amaury on 13/05/2015.
 */
public class WsBeerDAOImpl implements IBeersDAO {

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://robin.grabarski.fr/libeery/web/app_dev.php/api/")
            .setConverter(new GsonConverter(gson))
            .build();

    private LibeeryRestService service = restAdapter.create(LibeeryRestService.class);

    private List<Beer> listBeersByName = null;
    private Beer randomBeer = null;

    private List<IDaoResponseListener> daoResponseEventListenersList = new ArrayList<>();

    @Override
    public synchronized void addDaoResponseEventListener(IDaoResponseListener listener){
        if(!daoResponseEventListenersList.contains(listener)){
            daoResponseEventListenersList.add(listener);
        }
    }

    @Override
    public List<Beer> getAllBeers() {
        List<Beer> allBeersList = new ArrayList<>();
        Beer b1 = new Beer();
        b1.setName("Corona");
        Beer b2 = new Beer();
        b2.setName("Leffe");
        Beer b3 = new Beer();
        b3.setName("Trolls");
        Beer b4 = new Beer();
        b4.setName("Rince");
        Beer b5 = new Beer();
        b5.setName("Heineken");
        Beer b6 = new Beer();
        b6.setName("Karmelit");
        Beer b7 = new Beer();
        b7.setName("Delirium");

        Glass g = new Glass();
        g.setName("Verre à bière");

        Labels l = new Labels();
        l.setIcon("http://upload.wikimedia.org/wikipedia/commons/0/07/AKE_Duff_Beer_IMG_5244_edit.jpg");
        l.setMedium("http://upload.wikimedia.org/wikipedia/commons/0/07/AKE_Duff_Beer_IMG_5244_edit.jpg");

        Category c = new Category();
        c.setName("Categorie 1");

        Style s1 = new Style();
        s1.setName("A bulle");
        s1.setCategory(c);

        b1.setStyle(s1);
        b2.setStyle(s1);
        b3.setStyle(s1);
        b4.setStyle(s1);
        b5.setStyle(s1);
        b6.setStyle(s1);
        b7.setStyle(s1);
        b1.setGlass(g);
        b2.setGlass(g);
        b3.setGlass(g);
        b4.setGlass(g);
        b5.setGlass(g);
        b6.setGlass(g);
        b7.setGlass(g);
        b1.setLabels(l);
        b2.setLabels(l);
        b3.setLabels(l);
        b4.setLabels(l);
        b5.setLabels(l);
        b6.setLabels(l);
        b7.setLabels(l);
        allBeersList.add(b1);
        allBeersList.add(b2);
        allBeersList.add(b3);
        allBeersList.add(b4);
        allBeersList.add(b5);
        allBeersList.add(b6);
        allBeersList.add(b7);
        return allBeersList;
    }

    @Override
    public List<Beer> getFavoriteBeers() {
        List<Beer> allBeersList = new ArrayList<>();
        Beer b1 = new Beer();
        b1.setName("Chouffe");
        Beer b2 = new Beer();
        b2.setName("Grimbergen");
        Beer b3 = new Beer();
        b3.setName("1664");
        Beer b4 = new Beer();
        b4.setName("Kro");

        Glass g = new Glass();
        g.setName("Verre à bière");
        Category c = new Category();
        c.setName("Categorie 1");
        Style s1 = new Style();
        s1.setName("A bulle");
        s1.setCategory(c);
        Labels l = new Labels();
        l.setIcon("https://s3.amazonaws.com/brewerydbapi/beer/nxmBqg/upload_UqTqav-icon.png");

        b1.setStyle(s1);
        b2.setStyle(s1);
        b3.setStyle(s1);
        b4.setStyle(s1);

        b1.setGlass(g);
        b2.setGlass(g);
        b3.setGlass(g);
        b4.setGlass(g);

        b1.setLabels(l);
        b2.setLabels(l);
        b3.setLabels(l);
        b4.setLabels(l);

        allBeersList.add(b1);
        allBeersList.add(b2);
        allBeersList.add(b3);
        allBeersList.add(b4);
        return allBeersList;
    }

    @Override
    public List<Beer> getBeersByName(String name) {
//        Glass g = new Glass();
//        g.setName("Verre à bière");
//        Category c = new Category();
//        c.setName("Categorie 1");
//        Style s = new Style();
//        s.setCategory(c);
//        s.setName("Style 1");
//        Labels l = new Labels();
//        l.setIcon("http://www.saveur-biere-entreprise.com/fournisseur-produit-4365-p-biere-verre-perdu-mort-subite-framboise.php");
//        Beer b = new Beer().builder().abv((new Float(12.5))).name("Mort subite").glass(g).style(s).build();
//        return b;

        service.getBeersByName(name, new Callback<List<Beer>>() {
            @Override
            public void success(List<Beer> beers, Response response) {
                listBeersByName = beers;
            }

            @Override
            public void failure(RetrofitError error) {
                listBeersByName = null;
                System.out.println(error.toString());
            }
        });
        return listBeersByName;
    }

    @Override
    public Beer getRandomBeer() {
//        int i = (int)(Math.random() * 1);
//
//        if(i ==0){
//            Glass g = new Glass();
//            g.setName("Verre à bière");
//            Category c = new Category();
//            c.setName("Categorie 1");
//            Style s = new Style();
//            s.setCategory(c);
//            s.setName("Style 1");
//            Labels l = new Labels();
//            l.setIcon("https://nickshell1983.files.wordpress.com/2010/03/killians-irish-red.jpg");
//            Beer b = new Beer().builder().abv((new Float(12.5))).name("Killians").glass(g).style(s).labels(l).build();
//            return b;
//        }else{
//            Glass g = new Glass();
//            g.setName("Verre à binouse");
//            Category c = new Category();
//            c.setName("Categorie 2");
//            Style s = new Style();
//            s.setCategory(c);
//            s.setName("Style 2");
//            Labels l = new Labels();
//            l.setIcon("http://www.guidedesbieres.com/photos/3524-bouteille-de-pelforth-brune-et-son-verre.jpg");
//            Beer b = new Beer().builder().abv((new Float(6.7))).name("Pelforth").glass(g).style(s).labels(l).build();
//            return b;
//        }
        service.getRandomBeer(new Callback<Beer>() {
            @Override
            public void success(Beer beer, Response response) {
                randomBeer = beer;
                fireRandomBeerResponse(randomBeer);
            }

            @Override
            public void failure(RetrofitError error) {
                randomBeer = null;
                System.out.println(error.toString());
            }
        });

        return randomBeer;
    }

    /**
     * Propage l'événement de réponse d'une bière aléatoire.
     * @param b La bière trouvée.
     */
    private synchronized void fireRandomBeerResponse(Beer b) {
        DAOResponseEvent event = new DAOResponseEvent( this, b);
        Iterator listeners = daoResponseEventListenersList.iterator();
        while( listeners.hasNext() ) {
            ( (RandomBeerResponseListener) listeners.next() ).randomBeerResponse( event );
        }
    }
}
