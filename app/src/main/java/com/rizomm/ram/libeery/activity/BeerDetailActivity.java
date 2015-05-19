package com.rizomm.ram.libeery.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class BeerDetailActivity extends ActionBarActivity {

    @InjectView(R.id.detailView_beerName) TextView mBeerName;
    @InjectView(R.id.detailView_beerAlcoholLevel) TextView mBeerAlcoholLevel;
    @InjectView(R.id.detailView_beerCategory) TextView mBeerCategory;
    @InjectView(R.id.detailView_beerPicture) ImageView mBeerPicture;
    @InjectView(R.id.detailView_beerType) TextView mBeerType;
    @InjectView(R.id.detailView_beerDescription) TextView mBeerDescription;
    @InjectView(R.id.detailView_addFavoriteButton) FloatingActionButton mAddFavoriteButton;

    private Beer mCurrentBeer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);

        // Récupération des éléments graphiques :
        ButterKnife.inject(this);

        mCurrentBeer = (Beer)getIntent().getSerializableExtra(Constant.INTENT_DETAIL_DATA_1);
        updateViewContent(mCurrentBeer);

        // Récupération des ID des bières favorites :
        DAOFactory factory = new DAOFactory();
        IFavoriteBeersDAO dao = factory.getFavoriteBeersDao();
        if(dao instanceof LocalDBBeerDAOImpl){
            ((LocalDBBeerDAOImpl) dao).setContext(this);
        }
        List<String> idList = dao.getFavoriteBeersIds();

        /* Si la bière a afficher n'est pas déjà une bière favorite, on affiche le bouton d'ajout aux favoris : */
        if(!(mCurrentBeer instanceof FavoriteBeer) && !idList.contains(String.valueOf(mCurrentBeer.getId()))){
            mAddFavoriteButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Clic sur le bouton pour ajouter une bière aux favories.
     */
    @OnClick(R.id.detailView_addFavoriteButton)
    public void onAddFavoriteButtonClick(){
        FavoriteBeer fb = new FavoriteBeer();
        fb = fb.beerToFavoriteBeer(mCurrentBeer, FavoriteBeersLocalDBHelper.IMAGE_TYPE.REMOTE_SRC.getValue());
        addToFavorite(fb);
        Crouton.makeText(this, R.string.addedFavoriteBeerMessageOK, Style.CONFIRM).show();
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
                mBeerType.setText(R.string.non_applicable);
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
                                String fileName = imgFile.getAbsolutePath();
                                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(imgFile));

                                /* Rotation de l'image */
                                BitmapFactory.Options bounds = new BitmapFactory.Options();
                                bounds.inJustDecodeBounds = true;
                                BitmapFactory.decodeFile(fileName, bounds);

                                ExifInterface exif = new ExifInterface(fileName);
                                String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                                int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;

                                int rotationAngle = 0;
                                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
                                if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
                                if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
                                System.out.println(rotationAngle);

                                Matrix matrix = new Matrix();
                                matrix.setRotate(rotationAngle, (float) imageBitmap.getWidth() / 2, (float) imageBitmap.getHeight() / 2);
                                imageBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);

                                // Affichage de l'image :
                                mBeerPicture.setImageBitmap(imageBitmap);
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
                    mBeerCategory.setText(R.string.non_applicable);
                }
            }else{
                mBeerType.setText(R.string.non_applicable);
            }

            // Affichage de la description :
            if(beer.getDescription() != null && !beer.getDescription().isEmpty()){
                mBeerDescription.setText(beer.getDescription());
            }else{
                mBeerDescription.setText(R.string.non_applicable);
            }
        }else{
            // Si randomBeer est null :
            mBeerType.setText(R.string.non_applicable);
            mBeerCategory.setText(R.string.non_applicable);
            mBeerName.setText(R.string.non_applicable);
            mBeerAlcoholLevel.setText(R.string.non_applicable);
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
