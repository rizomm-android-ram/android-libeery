package com.rizomm.ram.libeery.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.dao.DAOFactory;
import com.rizomm.ram.libeery.dao.IFavoriteBeersDAO;
import com.rizomm.ram.libeery.dao.localDB.LocalDBBeerDAOImpl;
import com.rizomm.ram.libeery.database.helper.FavoriteBeersLocalDBHelper;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.FavoriteBeer;
import com.rizomm.ram.libeery.utils.Constant;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BeerDetailActivity extends ActionBarActivity {

    @InjectView(R.id.detailView_beerName) TextView mBeerName;
    @InjectView(R.id.detailView_beerAlcoholLevel) TextView mBeerAlcoholLevel;
    @InjectView(R.id.detailView_beerCategory) TextView mBeerCategory;
    @InjectView(R.id.detailView_beerPicture) ImageView mBeerPicture;
    @InjectView(R.id.detailView_beerType) TextView mBeerType;
    @InjectView(R.id.detailView_beerDescription) TextView mBeerDescription;
    @InjectView(R.id.detailView_addFavoriteButton) FloatingActionButton mAddFavoriteButton;

    private Beer currentBeer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);

        // Récupération des éléments graphiques :
        ButterKnife.inject(this);

        currentBeer = (Beer)getIntent().getSerializableExtra(Constant.INTENT_DETAIL_DATA_1);
        updateViewContent(currentBeer);

        /* Si la bière a afficher n'est pas déjà une bière favorite, on affiche le bouton d'ajout aux favoris : */
        if(!(currentBeer instanceof FavoriteBeer)){
            mAddFavoriteButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Clic sur le bouton pour ajouter une bière aux favories.
     */
    @OnClick(R.id.detailView_addFavoriteButton)
    public void onAddFavoriteButtonClick(){
        FavoriteBeer fb = new FavoriteBeer();
        fb = fb.beerToFavoriteBeer(currentBeer, FavoriteBeersLocalDBHelper.IMAGE_TYPE.REMOTE_SRC.getValue());
        addToFavorite(fb);
    }

    /**
     * Ajoute une bière aux favories.
     * @param fb La bière à ajouter.
     */
    private void addToFavorite(FavoriteBeer fb){
        DAOFactory factory = new DAOFactory();
        IFavoriteBeersDAO dao = factory.getFavoriteBeersDao();
        if(dao instanceof LocalDBBeerDAOImpl){
            ((LocalDBBeerDAOImpl) dao).setContext(getApplication());
        }
        dao.addBeerToFavorite(fb);
        // On masque le bouton :
        mAddFavoriteButton.setVisibility(View.INVISIBLE);
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

            // Récupération de la source pour l'image :
            String mediumIcon = null;
            if(beer.getLabels() != null && beer.getLabels().getMedium() != null && !beer.getLabels().getMedium().isEmpty()){
                mediumIcon = beer.getLabels().getMedium();
            }
            if(mediumIcon == null && beer.getLabel_medium() != null && !beer.getLabel_medium().isEmpty()){
                mediumIcon = beer.getLabel_medium();
            }

            // Affichage de la photo :
            if(mediumIcon != null){
                // Si on affiche une bière favorite :
                if(beer instanceof FavoriteBeer){
                    // Si la bière favorite est une bière ajoutée manuellement :
                    if(((FavoriteBeer) beer).getSrcType() == FavoriteBeersLocalDBHelper.IMAGE_TYPE.LOCAL_SRC.getValue()){
                        // On retire l'attribut "file:" présent au début du nom du fichier :
                        mediumIcon = mediumIcon.replace("file:","");
                        // On récupère l'image :
                        File imgFile = new  File(mediumIcon);
                        if(imgFile.exists()){
                            Bitmap myBitmap = null;
                            try {
                                // Affichage de l'image :
                                mBeerPicture.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(imgFile)));
                            } catch (IOException e) {
                                e.printStackTrace();
                                // Affichage d'une image par défaut :
                                mBeerPicture.setImageResource(R.drawable.empty_bottle);
                            }
                        }
                    }else{
                        // Si la bière favorite est une bière publique ajoutée au favories :
                        printWithPicasso(mediumIcon);
                    }
                }else{
                    // Si on affiche une bière publique :
                    printWithPicasso(mediumIcon);
                }
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

            // Affichage de la description :
            if(beer.getDescription() != null && !beer.getDescription().isEmpty()){
                mBeerDescription.setText(beer.getDescription());
            }else{
                mBeerDescription.setText("NA");
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

    /**
     * Affiche une image avec Picasso.
     */
    private void printWithPicasso(String src){
        Picasso.with(this)
                .load(src)
                .error(R.drawable.empty_bottle)
                .placeholder(R.drawable.empty_bottle)
                .into(mBeerPicture);
    }
}
