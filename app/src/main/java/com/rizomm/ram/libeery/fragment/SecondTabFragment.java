package com.rizomm.ram.libeery.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.adapter.ListFavoriteBeersAdapter;
import com.rizomm.ram.libeery.database.manager.BeerDBManager;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Style;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Fragment représentant le second onglet de l'application.
 */
public class SecondTabFragment extends Fragment {

    private ListFavoriteBeersAdapter listFavoriteBeersAdapter;
    private List<Beer> listFavoriteBeers;

    @InjectView(R.id.listFavoriteBeers) AbsListView listViewFavoriteBeers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second_tab, container, false);

        // Récupération des éléments graphiques :
        ButterKnife.inject(this, rootView);

        // Récupération de la liste des bières :
        getFavoriteBeerList();

        listFavoriteBeersAdapter = new ListFavoriteBeersAdapter(getActivity(), listFavoriteBeers);
        listViewFavoriteBeers.setAdapter(listFavoriteBeersAdapter);

        return rootView;
    }

    /**
     * Récupère la liste des bières.
     */
     private void getFavoriteBeerList(){
        listFavoriteBeers = new ArrayList<>();
        Beer b1 = new Beer();
        b1.setName("Karmelit");
        Beer b2 = new Beer();
        b2.setName("Rince");
         Beer b3 = new Beer();
         b3.setName("Corona");
        Style s1 = new Style();
        s1.setName("A bulles");
        b1.setStyle(s1);
        b2.setStyle(s1);
        b3.setStyle(s1);
        listFavoriteBeers.add(b1);
        listFavoriteBeers.add(b2);
    }

}
