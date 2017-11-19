package com.cst.whut.aaa.DataProcess;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by 12421 on 2017/11/19.
 */

public class PoiPlace {
    private String placeName;
    private String placeAddress;
    private LatLng latLng;

    public PoiPlace(){}
    public PoiPlace(String placeName,String placeAddress){
        this.placeName = placeName;
        this.placeAddress = placeAddress;
    }
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
