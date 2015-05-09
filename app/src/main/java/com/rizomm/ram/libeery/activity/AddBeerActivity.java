package com.rizomm.ram.libeery.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.rizomm.ram.libeery.R;
import com.rizomm.ram.libeery.utils.Constant;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddBeerActivity extends ActionBarActivity {

    @InjectView(R.id.addView_beerName) EditText beerName;
    @InjectView(R.id.addView_beerType) EditText beerType;
    @InjectView(R.id.addView_beerCategory) EditText beerCategory;
    @InjectView(R.id.addView_beerAlcoholLevel) EditText beerAlcoholLevel;
    @InjectView(R.id.addView_validateButton) Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        // Récupération des éléments graphiques via ButterKnife
        ButterKnife.inject(this);
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
