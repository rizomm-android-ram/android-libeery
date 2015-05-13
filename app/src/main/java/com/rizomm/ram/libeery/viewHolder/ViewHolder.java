package com.rizomm.ram.libeery.viewHolder;

import android.view.View;

import com.rizomm.ram.libeery.model.Beer;

/**
 * Created by Amaury on 13/05/2015.
 */
public interface ViewHolder {

    /**
     * Met à jour une bière dans la liste.
     * @param beer
     */
    public void updateView(Beer beer);

    /**
     * Récupère la vue
     * @return
     */
    public View getView();

}
