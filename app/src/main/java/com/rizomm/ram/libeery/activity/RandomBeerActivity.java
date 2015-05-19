package com.rizomm.ram.libeery.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.dao.DAOFactory;
import com.rizomm.ram.libeery.dao.IBeersDAO;
import com.rizomm.ram.libeery.dao.IFavoriteBeersDAO;
import com.rizomm.ram.libeery.dao.localDB.LocalDBBeerDAOImpl;
import com.rizomm.ram.libeery.database.helper.FavoriteBeersLocalDBHelper;
import com.rizomm.ram.libeery.event.DAOBeerResponseEvent;
import com.rizomm.ram.libeery.event.listener.BeerResponseListener;
import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.FavoriteBeer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class RandomBeerActivity extends ActionBarActivity implements BeerResponseListener {

    @InjectView(R.id.randomView_beerName) TextView mBeerName;
    @InjectView(R.id.randomView_beerAlcoholLevel) TextView mBeerAlcoholLevel;
    @InjectView(R.id.randomView_beerCategory) TextView mBeerCategory;
    @InjectView(R.id.randomView_beerPicture) ImageView mBeerPicture;
    @InjectView(R.id.randomView_beerType) TextView mBeerType;
    @InjectView(R.id.randomView_beerDescription) TextView mBeerDescription;
    @InjectView(R.id.randomView_addFavoriteButton) FloatingActionButton mAddFavoriteButton;

    private DAOFactory mDaoFactory = new DAOFactory();
    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer = null;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private Beer mCurrentBeer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_beer);

        // Récupération des éléments graphiques :
        ButterKnife.inject(this);

        // Affichage d'une bière aléatoire :
        getRandomBeer();

        // Récupération du capteur gérant l'accéléromètre :
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Si ce capteur existe :
        if(mAccelerometer != null){
            // On active l'accéléromètre :
            mSensorManager.registerListener(mSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            mAccel = 0.00f;
            mAccelCurrent = SensorManager.GRAVITY_EARTH;
            mAccelLast = SensorManager.GRAVITY_EARTH;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_random_beer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            getRandomBeer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAccelerometer != null){
            // On réactive l'accéléromètre :
            mSensorManager.registerListener(mSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // On désactive l'acceleromètre :
        mSensorManager.unregisterListener(mSensorEventListener, mAccelerometer);
    }

    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Que faire en cas de changement de précision ?
            // Rien à faire
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            // Que faire en cas d'évènements sur le capteur ?
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > 5) {
                // On recherche une nouvelle bière aléatoire :
                getRandomBeer();
            }
        }
    };

    /**
     * Appelle le DAO afin de récupérer une bière aléatoire.
     */
    private void getRandomBeer(){
        IBeersDAO dao = mDaoFactory.getBeerDao();
        dao.addDaoResponseEventListener(this);
        dao.getRandomBeer();
    }

    @Override
    public void onBeerResponse(DAOBeerResponseEvent event) {
        mCurrentBeer = event.getCurrentBeer();
        updateViewContent(mCurrentBeer);
    }

    /**
     * Mise à jour du contenu de la vue avec les données d'une bière aléatoire
     */
    private void updateViewContent(Beer randomBeer){
        if(randomBeer != null){
            // Récupération des ID des bières favorites :
            DAOFactory factory = new DAOFactory();
            IFavoriteBeersDAO dao = factory.getFavoriteBeersDao();
            if(dao instanceof LocalDBBeerDAOImpl){
                ((LocalDBBeerDAOImpl) dao).setContext(this);
            }
            List<String> idList = dao.getFavoriteBeersIds();

            /* Si la bière a afficher n'est pas déjà une bière favorite, on affiche le bouton d'ajout aux favoris : */
            if(!(randomBeer instanceof FavoriteBeer) && !idList.contains(String.valueOf(randomBeer.getId()))){
                mAddFavoriteButton.setVisibility(View.VISIBLE);
            }

            // Affichage du degré d'alcool :
            if (randomBeer.getAbv() != 0.0 ) {
                String degree = String.valueOf(randomBeer.getAbv());
                degree += "°";
                mBeerAlcoholLevel.setText(degree);
            } else {
                mBeerAlcoholLevel.setText(R.string.non_applicable);
            }

            // Affichage du nom de la bière :
            if(randomBeer.getName() != null && !randomBeer.getName().isEmpty()){
                mBeerName.setText(randomBeer.getName());
            }else{
                mBeerType.setText(R.string.non_applicable);
            }

            // Affichage de la photo en utilisant Picasso :
            if(randomBeer.getLabels() != null && randomBeer.getLabels().getMedium() != null && !randomBeer.getLabels().getMedium().isEmpty()){
                Picasso.with(this)
                        .load(randomBeer.getLabels().getMedium())
                        .error(R.drawable.empty_bottle)
                        .placeholder(R.drawable.empty_bottle)
                        .into(mBeerPicture);
            }else{
                if(randomBeer.getLabel_medium() != null && !randomBeer.getLabel_medium().isEmpty()){
                    Picasso.with(this)
                            .load(randomBeer.getLabel_medium())
                            .error(R.drawable.empty_bottle)
                            .placeholder(R.drawable.empty_bottle)
                            .into(mBeerPicture);
                }else{
                    mBeerPicture.setImageResource(R.drawable.empty_bottle);
                }
            }

            // Affichage du style :
            if(randomBeer.getStyle() != null && !randomBeer.getStyle().getName().isEmpty()){
                mBeerType.setText(randomBeer.getStyle().getName());

                // Affichage de la catégorie :
                if(randomBeer.getStyle().getCategory() != null && !randomBeer.getStyle().getCategory().getName().isEmpty()){
                    mBeerCategory.setText(randomBeer.getStyle().getCategory().getName());
                }else{
                    mBeerCategory.setText(R.string.non_applicable);
                }
            }else{
                mBeerType.setText(R.string.non_applicable);
            }

            // Affichage de la description :
            if(randomBeer.getDescription() != null && !randomBeer.getDescription().isEmpty()){
                mBeerDescription.setText(randomBeer.getDescription());
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
     * Clic sur le bouton pour ajouter la bière en cours aux favories
     */
    @OnClick(R.id.randomView_addFavoriteButton)
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
}
