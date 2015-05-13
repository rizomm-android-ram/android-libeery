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
 * Created by Amaury on 26/04/2015.
 * ViewHolder contenant le style d'une ligne pour la liste des toutes les bières mais dans une vue paysage
 */
public class ListAllBeersViewHolderLandscape implements ViewHolder {

    private Context context;
    private View view;

    @InjectView(R.id.listAllBeers_BeerName) TextView beerName;
    @InjectView(R.id.listAllBeers_BeerStyle) TextView beerStyle;

    public ListAllBeersViewHolderLandscape(Context context) {
        this.context = context;
        createView();
    }

    private void createView() {
        view = LayoutInflater.from(context).inflate(R.layout.list_all_beers_landscape, null);
        ButterKnife.inject(this, view);
    }

    public void updateView(Beer beer){
        beerName.setText(beer.getName());
        beerStyle.setText(beer.getStyle().getName());
    }

    public View getView(){
        return view;
    }

}
