package com.rizomm.ram.libeery.viewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.model.Beer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Amaury on 30/04/2015.
 * ViewHolder contenant le style d'une ligne pour la liste des bières favorites.
 */
public class ListFavoriteBeersViewHolder implements ViewHolder {

    private Context mContext;
    private View mView;

    @InjectView(R.id.listFavoriteBeers_BeerName) TextView beerName;
    @InjectView(R.id.listFavoriteBeers_BeerStyle) TextView beerStyle;

    public ListFavoriteBeersViewHolder(Context context) {
        this.mContext = context;
        createView();
    }

    private void createView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.list_favorite_beers, null);
        ButterKnife.inject(this, mView);
    }

    public void updateView(Beer beer){
        beerName.setText(beer.getName());
        beerStyle.setText(beer.getStyle().getName());
    }

    public View getView(){
        return mView;
    }

}
