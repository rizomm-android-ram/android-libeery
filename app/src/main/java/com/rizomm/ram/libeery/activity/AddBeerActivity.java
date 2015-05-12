package com.rizomm.ram.libeery.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.utils.Constant;
import com.rizomm.ram.libeery.utils.ImageFile;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddBeerActivity extends ActionBarActivity {

    @InjectView(R.id.addView_beerName) EditText beerName;
    @InjectView(R.id.addView_beerType) EditText beerType;
    @InjectView(R.id.addView_beerCategory) EditText beerCategory;
    @InjectView(R.id.addView_beerAlcoholLevel) EditText beerAlcoholLevel;
    @InjectView(R.id.addView_beerPicture) ImageView beerPicture;
    @InjectView(R.id.addView_validateButton) Button validateButton;

    private ImageFile imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        // Récupération des éléments graphiques via ButterKnife
        ButterKnife.inject(this);
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
                    Toast.makeText(this, R.string.addedBeerMessageKO, Toast.LENGTH_SHORT).show();
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, Constant.TAKE_PICTURE_REQUEST);
                }
            }
        }else{
            Toast.makeText(this, R.string.errorNoCameraAvailable, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageBitmap = null;
        if (requestCode == Constant.TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {

            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.fromFile(imageFile.getImage()));
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
            setResult(Constant.RESULT_CODE_OK);
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
}
