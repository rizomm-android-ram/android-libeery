package com.rizomm.ram.libeery.viewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.model.Beer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

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
    @InjectView(R.id.listAllBeers_BeerIcon) ImageView beerIcon;

    public ListAllBeersViewHolderLandscape(Context context) {
        this.context = context;
        createView();
    }

    private void createView() {
        view = LayoutInflater.from(context).inflate(R.layout.list_all_beers_landscape, null);
        ButterKnife.inject(this, view);
    }

    public void updateView(Beer beer){
        // Si la donné nom existe :
        if(beer.getName() != null && !beer.getName().isEmpty()){
            beerName.setText(beer.getName());
        }else{
            beerName.setText("NA");
        }

        // Si la donnée icone existe :
        if(beer.getLabels() != null && beer.getLabels().getIcon() != null && !beer.getLabels().getIcon().isEmpty()){
            // Ajout de l'icone de la bière en utilisant Picasso :
            Picasso.with(context)
                    .load(beer.getLabels().getIcon())
                    .error(R.drawable.empty_bottle)
                    .placeholder(R.drawable.empty_bottle)
                    .into(beerIcon);
        }
    }

    public View getView(){
        return view;
    }

}
