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
import android.widget.ImageView;
import android.widget.TextView;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.dao.DAOFactory;
import com.rizomm.ram.libeery.dao.IBeersDAO;
import com.rizomm.ram.libeery.event.DAOBeerResponseEvent;
import com.rizomm.ram.libeery.event.listener.BeerResponseListener;
import com.rizomm.ram.libeery.model.Beer;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RandomBeerActivity extends ActionBarActivity implements BeerResponseListener {

    @InjectView(R.id.randomView_beerName) TextView mBeerName;
    @InjectView(R.id.randomView_beerAlcoholLevel) TextView mBeerAlcoholLevel;
    @InjectView(R.id.randomView_beerCategory) TextView mBeerCategory;
    @InjectView(R.id.randomView_beerPicture) ImageView mBeerPicture;
    @InjectView(R.id.randomView_beerType) TextView mBeerType;
    @InjectView(R.id.randomView_beerDescription) TextView mBeerDescription;

    private DAOFactory mDaoFactory = new DAOFactory();
    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer = null;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity


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
        updateViewContent(event.getCurrentBeer());
    }

    /**
     * Mise à jour du contenu de la vue avec les données d'une bière aléatoire
     */
    private void updateViewContent(Beer randomBeer){
        if(randomBeer != null){
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
}
