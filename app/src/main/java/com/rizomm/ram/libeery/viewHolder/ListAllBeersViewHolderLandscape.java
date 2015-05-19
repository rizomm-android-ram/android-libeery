package com.rizomm.ram.libeery.viewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.model.Beer;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Amaury on 26/04/2015.
 * ViewHolder contenant le style d'une ligne pour la liste des toutes les bières mais dans une vue paysage
 */
public class ListAllBeersViewHolderLandscape implements ViewHolder {

    private Context mContext;
    private View mView;

    @InjectView(R.id.listAllBeers_BeerName) TextView beerName;
    @InjectView(R.id.listAllBeers_BeerIcon) ImageView beerIcon;

    public ListAllBeersViewHolderLandscape(Context context) {
        this.mContext = context;
        createView();
    }

    private void createView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.list_all_beers_landscape, null);
        ButterKnife.inject(this, mView);
    }

    public void updateView(Beer beer){
        // Si la donné nom existe :
        if(beer.getName() != null && !beer.getName().isEmpty()){
            beerName.setText(beer.getName());
        }else{
            beerName.setText("NA");
        }

        // Si la donnée icone existe :
        String pict = null;
        if(beer.getLabels() != null && beer.getLabels().getIcon() != null && !beer.getLabels().getIcon().isEmpty()){
            pict = beer.getLabels().getIcon();
        }
        if(pict == null && beer.getLabel_icon() != null && !beer.getLabel_icon().isEmpty()) {
            pict = beer.getLabel_icon();
        }
        if(pict == null){
            pict = "vide";
        }
        // Ajout de l'icone de la bière en utilisant Picasso :
        Picasso.with(mContext)
                .load(pict)
                .error(R.drawable.empty_bottle)
                .placeholder(R.drawable.empty_bottle)
                .into(beerIcon);
    }

    public View getView(){
        return mView;
    }

}
