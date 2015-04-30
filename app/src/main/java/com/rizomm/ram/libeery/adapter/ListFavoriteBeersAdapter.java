package com.rizomm.ram.libeery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.viewHolder.ListFavoriteBeersViewHolder;

import java.util.List;

/**
 * Created by Amaury on 30/04/2015.
 * Adapter permettant de gérer la liste des bières favorites.
 */
public class ListFavoriteBeersAdapter extends BaseAdapter {

    private Context context;
    private List<Beer> dataSource;
    private ListFavoriteBeersViewHolder listFavoriteBeersViewHolder;
    private View cellView;
    private Beer beer;

    public ListFavoriteBeersAdapter(Context context, List<Beer> beerList){
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
            listFavoriteBeersViewHolder = new ListFavoriteBeersViewHolder(context);
            cellView = listFavoriteBeersViewHolder.getView();
            cellView.setTag(listFavoriteBeersViewHolder);
        }
        listFavoriteBeersViewHolder = (ListFavoriteBeersViewHolder) cellView.getTag();
        listFavoriteBeersViewHolder.updateView(beer);
        return cellView;
    }
}
