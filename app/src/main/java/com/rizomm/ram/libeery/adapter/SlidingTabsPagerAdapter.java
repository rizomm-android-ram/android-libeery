package com.rizomm.ram.libeery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rizomm.ram.libeery.activity.MainActivity;
import com.rizomm.ram.libeery.fragment.FirstTabFragment;
import com.rizomm.ram.libeery.fragment.SecondTabFragment;

/**
 * Created by Amaury on 26/04/2015.
 * Adapter permettant de gérer les slides de l'application.
 * Un slide est un onglet d'un tab.
 */
public class SlidingTabsPagerAdapter extends FragmentPagerAdapter {

    private FirstTabFragment mFirstTabFragment;
    private SecondTabFragment mSecondTabFragment;

    public SlidingTabsPagerAdapter(FragmentManager fm, MainActivity parent) {
        super(fm);
        mFirstTabFragment = new FirstTabFragment();
        mSecondTabFragment = new SecondTabFragment();
        mSecondTabFragment.setParent(parent);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int i) {
        // En fonction de l'item demandé, on retourne le 1er ou le 2nd fragment :
        switch (i){
            case 0 : return mFirstTabFragment;
            case 1 : return mSecondTabFragment;
            default:return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Si on demande le 1er slide, on affiche la vue HelloWorld :
        if(position == 0){
            return "Accueil";
        }

        // Sinon, alors on demande le 2nd slide donc on affiche la vue des contacts :
        return "Favoris";
    }
}
