package com.rizomm.ram.libeery.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.activity.BeerDetailActivity;
import com.rizomm.ram.libeery.adapter.ListFavoriteBeersAdapter;
import com.rizomm.ram.libeery.dao.BeerDAOFactory;
import com.rizomm.ram.libeery.dao.IBeersDAO;
import com.rizomm.ram.libeery.database.manager.BeerDBManager;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Style;
import com.rizomm.ram.libeery.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Fragment représentant le second onglet de l'application.
 */
public class SecondTabFragment extends Fragment {

    private ListFavoriteBeersAdapter listFavoriteBeersAdapter;
    private List<Beer> listFavoriteBeers;
    private BeerDAOFactory daoFactory = new BeerDAOFactory();

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
     * Clic sur un item de la liste.
     */
    @OnItemClick(R.id.listFavoriteBeers)
    public void onItemSelected(int position){
        // On démarre l'activité de détail d'une bière :
        Intent detailIntent = new Intent(getActivity(), BeerDetailActivity.class);
        detailIntent.putExtra(Constant.INTENT_DETAIL_DATA_1, (Beer) listViewFavoriteBeers.getItemAtPosition(position));
        startActivity(detailIntent);
    }

    /**
     * Récupère la liste des bières.
     */
     private void getFavoriteBeerList(){
         IBeersDAO dao = daoFactory.getBeerDao();
         listFavoriteBeers = dao.getFavoriteBeers();
    }

}
