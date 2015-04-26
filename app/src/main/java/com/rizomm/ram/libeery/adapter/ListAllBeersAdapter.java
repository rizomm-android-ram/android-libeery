package com.rizomm.ram.libeery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.viewHolder.ListAllBeersViewHolder;

import java.util.List;

/**
 * Created by Amaury on 26/04/2015.
 * Adapter permettant de gérer la liste de toutes les bières.
 */
public class ListAllBeersAdapter extends BaseAdapter {

    private Context context;
    private List<Beer> dataSource;
    private ListAllBeersViewHolder listAllBeersViewHolder;
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
            listAllBeersViewHolder = new ListAllBeersViewHolder(context);
            cellView = listAllBeersViewHolder.getView();
            cellView.setTag(listAllBeersViewHolder);
        }
        listAllBeersViewHolder = (ListAllBeersViewHolder) cellView.getTag();
        listAllBeersViewHolder.updateView(beer);
        return cellView;
    }
}
