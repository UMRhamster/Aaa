package com.cst.whut.aaa.DataProcess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cst.whut.aaa.R;

import java.util.List;

/**
 * Created by 12421 on 2017/11/19.
 */

public class PoiAdapter extends BaseAdapter{
    private Context context;
    private List<PoiPlace> poiList;

    public PoiAdapter (List<PoiPlace> poiList, Context context){
        this.poiList = poiList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return poiList.size();
    }

    @Override
    public PoiPlace getItem(int position) {
        return poiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.listcell_mapsearch,null);
            viewHolder = new ViewHolder();
            viewHolder.placeName = (TextView)view.findViewById(R.id.poi_name);
            viewHolder.placeAddress = (TextView)view.findViewById(R.id.poi_address);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        PoiPlace poiPlace = getItem(position);
        viewHolder.placeName.setText(poiPlace.getPlaceName());
        viewHolder.placeAddress.setText(poiPlace.getPlaceAddress());

        return view;
    }
    class ViewHolder{
        TextView placeName;
        TextView placeAddress;
    }
    public Context getContext() {
        return context;
    }
}
