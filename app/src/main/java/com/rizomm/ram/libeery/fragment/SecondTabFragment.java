package com.rizomm.ram.libeery.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.activity.BeerDetailActivity;
import com.rizomm.ram.libeery.activity.MainActivity;
import com.rizomm.ram.libeery.adapter.ListFavoriteBeersAdapter;
import com.rizomm.ram.libeery.dao.DAOFactory;
import com.rizomm.ram.libeery.dao.IFavoriteBeersDAO;
import com.rizomm.ram.libeery.dao.localDB.LocalDBBeerDAOImpl;
import com.rizomm.ram.libeery.event.DatasetChangedEvent;
import com.rizomm.ram.libeery.event.listener.DatasetChangedListener;
import com.rizomm.ram.libeery.model.FavoriteBeer;
import com.rizomm.ram.libeery.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import lombok.Setter;

/**
 * Fragment représentant le second onglet de l'application.
 */
public class SecondTabFragment extends Fragment implements DatasetChangedListener{

    private ListFavoriteBeersAdapter mListFavoriteBeersAdapter;
    private List<FavoriteBeer> mListFavoriteBeers;
    private DAOFactory mDaoFactory = new DAOFactory();
    private MainActivity mParent;

    @InjectView(R.id.listFavoriteBeers) AbsListView listViewFavoriteBeers;

    public void setParent(MainActivity mParent) {
        this.mParent = mParent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second_tab, container, false);

        // Récupération des éléments graphiques :
        ButterKnife.inject(this, rootView);

        // Récupération de la liste des bières :
        getFavoriteBeerList();

        mListFavoriteBeersAdapter = new ListFavoriteBeersAdapter(getActivity(), mListFavoriteBeers);
        listViewFavoriteBeers.setAdapter(mListFavoriteBeersAdapter);

        if(mParent != null){
            mParent.addDaoResponseEventListener(this);
        }

        return rootView;
    }

    /**
     * Clic sur un item de la liste.
     */
    @OnItemClick(R.id.listFavoriteBeers)
    public void onItemSelected(int position){
        // On démarre l'activité de détail d'une bière :
        Intent detailIntent = new Intent(getActivity(), BeerDetailActivity.class);
        detailIntent.putExtra(Constant.INTENT_DETAIL_DATA_1, (FavoriteBeer) listViewFavoriteBeers.getItemAtPosition(position));
        startActivity(detailIntent);
    }

    /**
     * Long clic sur un item de la liste
     * @param position
     */
    @OnItemLongClick(R.id.listFavoriteBeers)
    public boolean onItemLongPress(final int position){
        final FavoriteBeer beerToDelete = (FavoriteBeer) listViewFavoriteBeers.getItemAtPosition(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_delete_beer).setMessage(getString(R.string.message_delete_beer) +" " + beerToDelete.getName() +" ?");
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.button_delete_beer_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Suppression de la bière de la BdD :
                DAOFactory factory = new DAOFactory();
                IFavoriteBeersDAO dao = factory.getFavoriteBeersDao();
                if(dao instanceof LocalDBBeerDAOImpl){
                    ((LocalDBBeerDAOImpl) dao).setContext(getActivity());
                }
                dao.deleteBeer(beerToDelete);

                // Suppression de la bière de la liste :
                mListFavoriteBeers.remove(position);

                // On notifie l'adapter que la source de données a changé.
                mListFavoriteBeersAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.button_delete_beer_ko, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }

    /**
     * Récupère la liste des bières favorites.
     */
     private void getFavoriteBeerList(){
         IFavoriteBeersDAO dao = mDaoFactory.getFavoriteBeersDao();
         if(dao instanceof LocalDBBeerDAOImpl){
             ((LocalDBBeerDAOImpl)dao).setContext(getActivity());
         }
        if(mListFavoriteBeers == null){
            mListFavoriteBeers = new ArrayList<>();
        }
         mListFavoriteBeers.clear();
         mListFavoriteBeers.addAll(dao.getFavoriteBeers());
    }

    @Override
    public void onDatasetChanged(DatasetChangedEvent event) {
        if(event.getFavoriteBeerAdded() != null){
            mListFavoriteBeers.add(event.getFavoriteBeerAdded());
        }
        mListFavoriteBeersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* A la reprise de l'application, on recharge la liste des bières favorites. */
        getFavoriteBeerList();
        mListFavoriteBeersAdapter.notifyDataSetChanged();
    }
}
