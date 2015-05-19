package com.rizomm.ram.libeery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.adapter.SlidingTabsPagerAdapter;
import com.rizomm.ram.libeery.commonViews.SlidingTabLayout;
import com.rizomm.ram.libeery.event.DatasetChangedEvent;
import com.rizomm.ram.libeery.event.listener.DatasetChangedListener;
import com.rizomm.ram.libeery.event.listener.IDaoResponseListener;
import com.rizomm.ram.libeery.model.FavoriteBeer;
import com.rizomm.ram.libeery.utils.Constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class MainActivity extends ActionBarActivity {

    private SlidingTabsPagerAdapter mAdapter;

    @InjectView(R.id.sliding_tabs) SlidingTabLayout slidingTabLayout;
    @InjectView(R.id.viewPager) ViewPager viewPager;
    @InjectView(R.id.add_button) FloatingActionButton addButton;

    private List<IDaoResponseListener> mDaoResponseEventListenersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération des éléments graphiques via ButterKnife :
        ButterKnife.inject(this);

        // Ajout de l'adapter et de des vues qu'il contient au tabLayout
        mAdapter = new SlidingTabsPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mAdapter);
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_random_beer) {
            // On démarre l'activité permettant d'afficher une bière aléatoire :
            Intent randomBeerIntent = new Intent(this, RandomBeerActivity.class);
            startActivity(randomBeerIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Clic sur le bouton permettant d'ajouter une bière.
     */
    @OnClick(R.id.add_button)
    public void onAddButtonClick(){
        Intent addBeerIntent = new Intent(this, AddBeerActivity.class);
        startActivityForResult(addBeerIntent, Constant.ADD_BEER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.ADD_BEER_REQUEST){
            if(resultCode == Constant.RESULT_CODE_OK){
                Crouton.makeText(this, R.string.addedBeerMessageOK, Style.CONFIRM).show();
                Bundle bundle = data.getExtras();
                FavoriteBeer fb = (FavoriteBeer)bundle.getSerializable(Constant.ADD_BEER_RESULT);
                fireSourceChanged(fb);
            }else{
                Crouton.makeText(this, R.string.addedBeerMessageKO, Style.ALERT).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Ajoute un listener à la liste des listeners.
     * @param listener
     */
    public void addDaoResponseEventListener(IDaoResponseListener listener) {
        if(!mDaoResponseEventListenersList.contains(listener)){
            mDaoResponseEventListenersList.add(listener);
        }
    }

    /**
     * Propage un événement indiquant que la source des données a changé.<br />
     * Typiquement quand on ajoute une bière favorite.
     * @param fb La bière ajoutée.
     */
    private synchronized void fireSourceChanged(FavoriteBeer fb) {
        DatasetChangedEvent event = new DatasetChangedEvent( this, fb);
        Iterator listeners = mDaoResponseEventListenersList.iterator();
        while( listeners.hasNext() ) {
            ( (DatasetChangedListener) listeners.next() ).onDatasetChanged(event);
        }
    }
}