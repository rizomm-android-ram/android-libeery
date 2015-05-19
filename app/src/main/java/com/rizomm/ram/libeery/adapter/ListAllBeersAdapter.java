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

    private Context mContext;
    private List<Beer> mDataSource;
    private ViewHolder mViewHolder;
    private View mCellView;
    private Beer mBeer;

    public ListAllBeersAdapter(Context context, List<Beer> beerList){
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
                mViewHolder = new ListAllBeersViewHolderLandscape(mContext);
            }else{
                // Si on est en mode portrait, on utilise la vue portrait
                mViewHolder = new ListAllBeersViewHolder(mContext);
            }
            mCellView = mViewHolder.getView();
            mCellView.setTag(mViewHolder);
        }
        mViewHolder = (ViewHolder) mCellView.getTag();
        mViewHolder.updateView(mBeer);
        return mCellView;
    }
}
