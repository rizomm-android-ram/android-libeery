package com.rizomm.ram.libeery.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.viewHolder.ListAllBeersViewHolder;
import com.rizomm.ram.libeery.viewHolder.ListAllBeersViewHolderLandscape;
import com.rizomm.ram.libeery.viewHolder.ViewHolder;

import java.util.List;

/**
 * Created by Amaury on 26/04/2015.
 * Adapter permettant de gérer la liste de toutes les bières.
 */
public class ListAllBeersAdapter extends BaseAdapter {

    private Context context;
    private List<Beer> dataSource;
    private ViewHolder viewHolder;
    private View cellView;
    private Beer beer;

    public ListAllBeersAdapter(Context context, List<Beer> beerList){
        this.context = context;
        this.dataSource = beerList;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        cellView = view;
        beer = dataSource.get(i);

        if(cellView == null){
            // Si on est en mode paysage :
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                // On utilise la vue paysage
                viewHolder = new ListAllBeersViewHolderLandscape(context);
            }else{
                // Si on est en mode portrait, on utilise la vue portrait
                viewHolder = new ListAllBeersViewHolder(context);
            }
            cellView = viewHolder.getView();
            cellView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) cellView.getTag();
        viewHolder.updateView(beer);
        return cellView;
    }
}
