package com.rizomm.ram.libeery.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.activity.BeerDetailActivity;
import com.rizomm.ram.libeery.adapter.ListAllBeersAdapter;
import com.rizomm.ram.libeery.dao.DAOFactory;
import com.rizomm.ram.libeery.dao.IBeersDAO;
import com.rizomm.ram.libeery.dao.ICategoryDAO;
import com.rizomm.ram.libeery.dao.IStyleDAO;
import com.rizomm.ram.libeery.event.DAOBeerResponseEvent;
import com.rizomm.ram.libeery.event.DAOCategoryResponseEvent;
import com.rizomm.ram.libeery.event.DAOStyleResponseEvent;
import com.rizomm.ram.libeery.event.listener.BeerResponseListener;
import com.rizomm.ram.libeery.event.listener.CategoryResponseListener;
import com.rizomm.ram.libeery.event.listener.StyleResponseListener;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.model.Style;
import com.rizomm.ram.libeery.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

/**
 * Fragment représentant le premier onglet de l'application.
 */
public class FirstTabFragment extends Fragment implements CategoryResponseListener, StyleResponseListener, BeerResponseListener {

    private ListAllBeersAdapter listAllBeersAdapter;
    private List<Beer> allBeersList ;
    private List<Category> categoryList;
    private List<Style> styleList;
    private DAOFactory daoFactory = new DAOFactory();
    private IBeersDAO dao ;

    @InjectView(R.id.listAllBeers) AbsListView listAllBeers;
    @InjectView(R.id.firstTab_beerCategory) Spinner mBeerCategory;
    @InjectView(R.id.firstTab_beerType) Spinner mBeerStyle;
    @InjectView(R.id.firstTab_beerName) EditText mBeerName;
    @InjectView(R.id.firstTab_searchButton) Button mSearchButton;
    @InjectView(R.id.firstTab_showAreaButton) Button mShowSearchAreaButton;
    @InjectView(R.id.firstTab_searchArea) LinearLayout mSearchArea;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first_tab, container, false);

        // Récupération des éléments graphiques :
        ButterKnife.inject(this, rootView);

        // Ajout d'un listener au DAO gérant les bières :
        dao = daoFactory.getBeerDao();
        dao.addDaoResponseEventListener(this);

        allBeersList = new ArrayList<>();
        listAllBeersAdapter = new ListAllBeersAdapter(getActivity(), allBeersList);
        listAllBeers.setAdapter(listAllBeersAdapter);

        // Récupération de la liste des catégories :
        getCategoryList();

        return rootView;
    }

    /**
     * Clic sur le bouton de recherche.
     */
    @OnClick(R.id.firstTab_searchButton)
    public void onSearchButtonClick(){
        mSearchArea.setVisibility(View.GONE);
        mShowSearchAreaButton.setVisibility(View.VISIBLE);
        if(mBeerName.getText() != null && !mBeerName.getText().toString().isEmpty()){
            getAllBeerByNameList(mBeerName.getText().toString());
        }
    }

    /**
     * Clic sur le bouton pour afficher la zone de recherche.
     */
    @OnClick(R.id.firstTab_showAreaButton)
    public void onShowSearchAreaButtonClic(){
        mShowSearchAreaButton.setVisibility(View.GONE);
        mSearchArea.setVisibility(View.VISIBLE);
    }

    /**
     * Clic sur un item de la liste.
     */
    @OnItemClick(R.id.listAllBeers)
    public void onItemSelected(int position){
        // On démarre l'activité de détail d'une bière :
        Intent detailIntent = new Intent(getActivity(), BeerDetailActivity.class);
        detailIntent.putExtra(Constant.INTENT_DETAIL_DATA_1, (Beer) listAllBeers.getItemAtPosition(position));
        startActivity(detailIntent);
    }

    /**
     * Récupère la liste des catégories.
     */
    private void getCategoryList(){
        DAOFactory factory = new DAOFactory();
        ICategoryDAO dao = factory.getCategoryDao();
        dao.addDaoResponseEventListener(this);
        dao.getAllCategories();
    }

    /**
     * Rempli la liste déroulante des catégories.
     * @param list
     */
    private void populateCategorySpinner(List<String> list){
        String[] spinnerArray = new String[list.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, list.toArray(spinnerArray));
        mBeerCategory.setAdapter(adapter);
    }

    /**
     * Lors d'un changement dans la liste des catégories.
     * @param position
     */
    @OnItemSelected(R.id.firstTab_beerCategory)
    public void onCategoryChange(int position){
        Category c = categoryList.get(position);
        getStyleList(c.getId());
    }

    /**
     * Récupère la liste des styles correspondants à une catégorie
     * @param id L'id de la catégorie.
     */
    private void getStyleList(int id){
        DAOFactory factory = new DAOFactory();
        IStyleDAO dao = factory.getStyleDAO();
        dao.addDaoResponseEventListener(this);
        dao.getStyleByCategory(id);
    }

    /**
     * Rempli la liste déruolante des styles.
     */
    private void populateStyleSinner(List<String> styleList){
        String[] spinnerArray = new String[styleList.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, styleList.toArray(spinnerArray));
        mBeerStyle.setAdapter(adapter);
    }

    /**
     * Lors d'un changement dans la liste des catégories.
     * @param position
     */
    @OnItemSelected(R.id.firstTab_beerType)
    public void onStyleChange(int position){
        mSearchArea.setVisibility(View.GONE);
        mShowSearchAreaButton.setVisibility(View.VISIBLE);
        Style s = styleList.get(position);
        getAllBeerByStyleList(s);
    }

    /**
     * Récupère la liste des bières par rapport à un style.
     * @param style
     */
    private void getAllBeerByStyleList(Style style){
        if(allBeersList == null){
            allBeersList = new ArrayList<>();
        }
        allBeersList = dao.getBeersByStyle(style);
    }

    /**
     * Récupère la liste des bières par rapport à un nom.
     * @param name
     */
    private void getAllBeerByNameList(String name){
        if(allBeersList == null){
            allBeersList = new ArrayList<>();
        }
        allBeersList = dao.getBeersByName(name);
    }

    @Override
    public void onCategoryResponse(DAOCategoryResponseEvent event) {
        categoryList = event.getCategoryList();
        List<String> list = new ArrayList<>();
        for(Category c : event.getCategoryList()){
            if(c.getName() != null && !c.getName().isEmpty()){
                list.add(c.getName());
            }
        }
        populateCategorySpinner(list);
    }

    @Override
    public void onStyleResponse(DAOStyleResponseEvent event) {
        styleList = event.getStyleList();
        List<String> list = new ArrayList<>();
        for(Style s : event.getStyleList()){
            if(s.getName() != null && !s.getName().isEmpty()){
                list.add(s.getName());
            }
        }
        populateStyleSinner(list);
    }

    @Override
    public void onBeerResponse(DAOBeerResponseEvent event) {
        if(allBeersList == null){
            allBeersList = new ArrayList<>();
        }

        allBeersList.clear();
        for(Beer b : event.getBeerList()){
            allBeersList.add(b);
        }

        // On reconstruit l'adapter :
        listAllBeersAdapter = new ListAllBeersAdapter(getActivity(), allBeersList);
        listAllBeers.setAdapter(listAllBeersAdapter);

        // on informe l'adaper que la source de données à changée :
        listAllBeersAdapter.notifyDataSetChanged();

    }
}
