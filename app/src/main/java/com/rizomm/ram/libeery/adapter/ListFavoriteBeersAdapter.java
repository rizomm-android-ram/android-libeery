package com.rizomm.ram.libeery.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rizomm.ram.libeery.model.Beer;
import com.rizomm.ram.libeery.model.FavoriteBeer;
import com.rizomm.ram.libeery.viewHolder.ListFavoriteBeersViewHolder;
import com.rizomm.ram.libeery.viewHolder.ListFavoriteBeersViewHolderLandscape;
import com.rizomm.ram.libeery.viewHolder.ViewHolder;

import java.util.List;

/**
 * Created by Amaury on 30/04/2015.
 * Adapter permettant de gérer la liste des bières favorites.
 */
public class ListFavoriteBeersAdapter extends BaseAdapter {

    private Context mContext;
    private List<FavoriteBeer> mDataSource;
    private ViewHolder mViewHolder;
    private View mCellView;
    private Beer mBeer;

    public ListFavoriteBeersAdapter(Context context, List<FavoriteBeer> beerList){
        this.mContext = context;
        this.mDataSource = beerList;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mCellView = view;
        mBeer = mDataSource.get(i);

        if(mCellView == null){
            // Si on est en mode paysage :
            if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                // On utilise la vue paysage
                mViewHolder = new ListFavoriteBeersViewHolderLandscape(mContext);
            }else{
                // Si on est en mode portrait, on utilise la vue portrait
                mViewHolder = new ListFavoriteBeersViewHolder(mContext);
            }
            mCellView = mViewHolder.getView();
            mCellView.setTag(mViewHolder);
        }

        mViewHolder = (ViewHolder) mCellView.getTag();
        mViewHolder.updateView(mBeer);
        return mCellView;
    }
}
