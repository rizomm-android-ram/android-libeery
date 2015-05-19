package com.rizomm.ram.libeery.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.dao.DAOFactory;
import com.rizomm.ram.libeery.dao.ICategoryDAO;
import com.rizomm.ram.libeery.dao.IFavoriteBeersDAO;
import com.rizomm.ram.libeery.dao.IStyleDAO;
import com.rizomm.ram.libeery.dao.localDB.LocalDBBeerDAOImpl;
import com.rizomm.ram.libeery.database.helper.FavoriteBeersLocalDBHelper;
import com.rizomm.ram.libeery.event.DAOCategoryResponseEvent;
import com.rizomm.ram.libeery.event.DAOStyleResponseEvent;
import com.rizomm.ram.libeery.event.listener.CategoryResponseListener;
import com.rizomm.ram.libeery.event.listener.StyleResponseListener;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.Category;
import com.rizomm.ram.libeery.model.FavoriteBeer;
import com.rizomm.ram.libeery.model.Style;
import com.rizomm.ram.libeery.utils.Constant;
import com.rizomm.ram.libeery.utils.ImageFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import de.keyboardsurfer.android.widget.crouton.Crouton;

public class AddBeerActivity extends ActionBarActivity implements CategoryResponseListener, StyleResponseListener {

    @InjectView(R.id.addView_beerName) EditText beerName;
    @InjectView(R.id.addView_beerType) Spinner beerType;
    @InjectView(R.id.addView_beerCategory) Spinner beerCategory;
    @InjectView(R.id.addView_beerAlcoholLevel) EditText beerAlcoholLevel;
    @InjectView(R.id.addView_beerPicture) ImageView beerPicture;
    @InjectView(R.id.addView_beerDescription) EditText beerDescription;
    @InjectView(R.id.addView_validateButton) Button validateButton;

    private ImageFile imageFile;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        // Récupération des éléments graphiques via ButterKnife
        ButterKnife.inject(this);

        // Récupération de la liste des categories :
        getCategoryList();
    }

    /**
     * Récupère la liste des catégories
     */
    private void getCategoryList(){
        DAOFactory factory = new DAOFactory();
        ICategoryDAO dao = factory.getCategoryDao();
        dao.addDaoResponseEventListener(this);
        dao.getAllCategories();
    }

    /**
     * Rempli le spinner Categorie avec des données
     * @param categoryStringList La liste des categories à afficher.
     */
    private void populateCategorySpinner(List<String> categoryStringList){
        String[] spinnerArray = new String[categoryStringList.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categoryStringList.toArray(spinnerArray));
        beerCategory.setAdapter(adapter);
    }

    /**
     * Rempli le spinner Style avec des données
     * @param styleStringList La liste des styles à afficher.
     */
    private void populateStyleSinner(List<String> styleStringList){
        String[] spinnerArray = new String[styleStringList.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, styleStringList.toArray(spinnerArray));
        beerType.setAdapter(adapter);
    }

    @OnItemSelected(R.id.addView_beerCategory)
    public void onCategoryChange(int position){
        Category c = categoryList.get(position);
        getStyleList(c.getId());
    }

    /**
     * Récupère la liste des styles d'une catégorie.
     * @param categoryId
     */
    private void getStyleList(int categoryId){
        DAOFactory factory = new DAOFactory();
        IStyleDAO dao = factory.getStyleDAO();
        dao.addDaoResponseEventListener(this);
        dao.getStyleByCategory(categoryId);
    }

    /**
     * Clic sur le bouton pour prendre une photo.
     */
    @OnClick(R.id.addView_beerPicture)
    public void onTakePictureButtonClick(){
        PackageManager pm = this.getApplicationContext().getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageFile = new ImageFile();

            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = imageFile.createImageFile();
                } catch (IOException ex) {
                    Crouton.makeText(this, R.string.addedBeerMessageKO, de.keyboardsurfer.android.widget.crouton.Style.ALERT).show();
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, Constant.TAKE_PICTURE_REQUEST);
                }
            }
        }else{
            Crouton.makeText(this, R.string.errorNoCameraAvailable, de.keyboardsurfer.android.widget.crouton.Style.ALERT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageBitmap = null;
        if (requestCode == Constant.TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {

            try {
                String fileName = imageFile.getImage().getAbsolutePath();
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.fromFile(imageFile.getImage()));

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
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Si on a réussi à créer un bitmap de l'image, on l'affiche :
            if(imageBitmap != null){
                beerPicture.setImageBitmap(imageBitmap);
            }

            // Ajout de l'image à la galerie Android :
            if(imageFile != null){
                imageFile.galleryAddPic(this);
            }
        }
    }

    public String pathFromUri(Uri imageUri) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        return filePath ;
    }

    /**
     * Clic sur le bouton de validation du formulaire
     */
    @OnClick(R.id.addView_validateButton)
    public void onValidateButtonClick(){
        Intent resultIntent = new Intent();

        if(!beerName.getText().toString().isEmpty()){
            DAOFactory daoFactory = new DAOFactory();
            IFavoriteBeersDAO dao = daoFactory.getFavoriteBeersDao();
            if(dao instanceof LocalDBBeerDAOImpl){
                ((LocalDBBeerDAOImpl)dao).setContext(getApplicationContext());
            }

            String path = "";
            Float alcoholLevel = Float.valueOf("0.0");
            String desc = "";
            String cat = "";
            String style = "";

            if(imageFile != null && imageFile.getCurrentPhotoPath() != null ){
                path = imageFile.getImage().getAbsolutePath();
            }
            if(beerAlcoholLevel.getText() != null && !beerAlcoholLevel.getText().toString().isEmpty()){
                alcoholLevel = Float.valueOf(beerAlcoholLevel.getText().toString());
            }
            if(beerCategory.getSelectedItem() != null && !beerCategory.getSelectedItem().toString().isEmpty()){
                cat = beerCategory.getSelectedItem().toString();
            }
            if(beerType.getSelectedItem() != null && !beerType.getSelectedItem().toString().isEmpty()){
                style = beerType.getSelectedItem().toString();
            }
            if(beerDescription.getText() != null && !beerDescription.getText().toString().isEmpty()){
                desc = beerDescription.getText().toString();
            }

            // Création de l'objet bière :
            Category c = new Category();
            c.setName(cat);

            Style s = new Style();
            s.setName(style);
            s.setCategory(c);

            Beer b = Beer.builder()
                    .abv(alcoholLevel)
                    .name(beerName.getText().toString())
                    .label_icon(path)
                    .label_large(path)
                    .label_medium(path)
                    .description(desc)
                    .style(s).build();
            FavoriteBeer fb = new FavoriteBeer();
            fb = fb.beerToFavoriteBeer(b, FavoriteBeersLocalDBHelper.IMAGE_TYPE.LOCAL_SRC.getValue());

            // Ajout de la bière à la liste des favorites :
            dao.addBeerToFavorite(fb);

            // On informe que l'action s'est bien déroulée :
            setResult(Constant.RESULT_CODE_OK, resultIntent.putExtra("123456", fb));
        }else{
            setResult(Constant.RESULT_CODE_KO);
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_beer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        List<String> list = new ArrayList<>();
        for(Style s : event.getStyleList()){
            if(s.getName() != null && !s.getName().isEmpty()){
                list.add(s.getName());
            }
        }
        populateStyleSinner(list);
    }
}
