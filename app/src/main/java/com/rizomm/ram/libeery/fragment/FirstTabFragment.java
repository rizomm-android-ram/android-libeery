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
import com.rizomm.ram.libeery.adapter.ListAllBeersAdapter;
import com.rizomm.ram.libeery.dao.BeerDAOFactory;
import com.rizomm.ram.libeery.dao.IBeersDAO;
import com.rizomm.ram.libeery.database.manager.BeerDBManager;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Style;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Fragment représentant le premier onglet de l'application.
 */
public class FirstTabFragment extends Fragment {

    private ListAllBeersAdapter listAllBeersAdapter;
    private List<Beer> allBeersList ;
    private BeerDAOFactory daoFactory = new BeerDAOFactory();

    @InjectView(R.id.listAllBeers) AbsListView listAllBeers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first_tab, container, false);

        // Récupération des éléments graphiques :
        ButterKnife.inject(this, rootView);

        // Récupération de la liste des bières :
        getAllBeerList();

        listAllBeersAdapter = new ListAllBeersAdapter(getActivity(), allBeersList);
        listAllBeers.setAdapter(listAllBeersAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Récupère la liste des bières.
     */
    private void getAllBeerList(){
        IBeersDAO dao = daoFactory.getBeerDao();
        allBeersList = dao.getAllBeers();
    }
}
