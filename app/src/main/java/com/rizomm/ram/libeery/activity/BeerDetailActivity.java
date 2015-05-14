package com.rizomm.ram.libeery.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.utils.Constant;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BeerDetailActivity extends ActionBarActivity {

    @InjectView(R.id.detailView_beerName) TextView mBeerName;
    @InjectView(R.id.detailView_beerAlcoholLevel) TextView mBeerAlcoholLevel;
    @InjectView(R.id.detailView_beerCategory) TextView mBeerCategory;
    @InjectView(R.id.detailView_beerPicture) ImageView mBeerPicture;
    @InjectView(R.id.detailView_beerType) TextView mBeerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);

        // Récupération des éléments graphiques :
        ButterKnife.inject(this);

        Beer currentBeer = (Beer)getIntent().getSerializableExtra(Constant.INTENT_DETAIL_DATA_1);
        updateViewContent(currentBeer);
    }

    /**
     * Mise à jour du contenu de la vue avec les données de la bière passée en paramètre
     */
    private void updateViewContent(Beer beer){
        if(beer != null){
            // Affichage du degré d'alcool :
            mBeerAlcoholLevel.setText(String.valueOf(beer.getAbv()));

            // Affichage du nom de la bière :
            if(beer.getName() != null && !beer.getName().isEmpty()){
                mBeerName.setText(beer.getName());
            }else{
                mBeerType.setText("NA");
            }

            // Affichage de la photo en utilisant Picasso :
            if(beer.getLabels() != null && beer.getLabels().getMedium() != null && !beer.getLabels().getMedium().isEmpty()){
                Picasso.with(this)
                        .load(beer.getLabels().getMedium())
                        .error(R.drawable.empty_bottle)
                        .placeholder(R.drawable.empty_bottle)
                        .into(mBeerPicture);
            }else{
                mBeerPicture.setImageResource(R.drawable.empty_bottle);
            }

            // Affichage du style :
            if(beer.getStyle() != null && !beer.getStyle().getName().isEmpty()){
                mBeerType.setText(beer.getStyle().getName());

                // Affichage de la catégorie :
                if(beer.getStyle().getCategory() != null && !beer.getStyle().getCategory().getName().isEmpty()){
                    mBeerCategory.setText(beer.getStyle().getCategory().getName());
                }else{
                    mBeerCategory.setText("NA");
                }
            }else{
                mBeerType.setText("NA");
            }
        }else{
            // Si randomBeer est null :
            mBeerType.setText("NA");
            mBeerCategory.setText("NA");
            mBeerName.setText("NA");
            mBeerAlcoholLevel.setText("NA");
            mBeerPicture.setImageResource(R.drawable.empty_bottle);
        }
    }
}
