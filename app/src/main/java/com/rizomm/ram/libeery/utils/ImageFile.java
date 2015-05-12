package com.rizomm.ram.libeery.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;

/**
 * Created by Amaury on 10/05/2015.
 */
public class ImageFile {

    @Getter
    private String currentPhotoPath;
    @Getter
    private File image;

    /**
     * Créé un fichier.
     * @return Le fichier créé.
     * @throws IOException
     */
    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    /**
     * Ajoute une image à la galerie Android
     * @param currentActivity L'activité en cours d'exécution.
     */
    public void galleryAddPic(Activity currentActivity) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        currentActivity.sendBroadcast(mediaScanIntent);
    }

}
