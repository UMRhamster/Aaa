package com.cst.whut.aaa.MainFragment;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.cst.whut.aaa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12421 on 2017/11/1.
 */

public class MapFragment extends Fragment implements View.OnClickListener,OnGetRoutePlanResultListener{
    //测试数据
    private String[] testData = {"清华大学","北京大学","上海大学","南京大学","浙江大学","武汉大学","武汉理工大学","武汉理工大学","武汉理工大学"
            ,"武汉理工大学","武汉理工大学","武汉理工大学","武汉理工大学","武汉理工大学","武汉理工大学","武汉理工大学"};
    //搜索服务
    private RoutePlanSearch routeSearch;
    private PlanNode start;
    private PlanNode end;
    private RouteLine route;

    private BDLocation mylocation;

    BitmapDescriptor mCurrentMarker;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private MapView mapView;
    public LocationClient locationClient;

    private TextView mapSearch_tv;
    private EditText mapSearch_et;
    private ListView mapSearch_lv;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapSearch_tv = (TextView)view.findViewById(R.id.mapsearch_tv);
        mapSearch_tv.setOnClickListener(this);
        mapSearch_et = (EditText)view.findViewById(R.id.mapsearch_et);
        mapSearch_et.setOnClickListener(this);
        mapSearch_lv = (ListView)view.findViewById(R.id.mapsearch_lv);
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        mapView = (MapView)view.findViewById(R.id.bdmap);
        baiduMap = mapView.getMap();

        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        List<String> permissionList = new ArrayList<>();
        //路径规划
        routeSearch = RoutePlanSearch.newInstance();
        routeSearch.setOnGetRoutePlanResultListener(this);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);;
            ActivityCompat.requestPermissions(getActivity(),permissions,1);
        }else{
            requestLocation();
        }
        return view;
    }


    private void requestLocation(){
        initLocation();
        locationClient.start();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(2000);  //5秒间隔刷新位置信息
        option.setIsNeedAddress(true);   //需要获取当前位置详细信息
        option.setOpenGps(true);   //设置GPS可用
        locationClient.setLocOption(option);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getActivity(),"需要同意所有权限才能使用",Toast.LENGTH_SHORT).show();
                            try {
                                finalize();
                                return;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(getActivity(),"发生未知错误",Toast.LENGTH_SHORT).show();
                    try {
                        finalize();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatus mapStatus = new MapStatus.Builder()
                    .target(ll)
                    .zoom(16f)
                    .build();
            MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
//            baiduMap.animateMapStatus(update);
//            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());;
        locationBuilder.longitude(location.getLongitude());
        MyLocationData myLocationData = locationBuilder.build();
        baiduMap.setMyLocationData(myLocationData);
    }
    //步行路线查询
//    private void walkSearch(BDLocation location){
//        WalkingRoutePlanOption walkingPlan = new WalkingRoutePlanOption();
//        walkingPlan.from(PlanNode.withLocation(new LatLng(location.getAltitude(),location.getLongitude())));
//    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if(walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR){
            Toast.makeText(getActivity(),"未找到结果",Toast.LENGTH_SHORT).show();
        }
        if(walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR){
            return;
        }
        if(walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
            route = walkingRouteResult.getRouteLines().get(0);
            WalkingRouteOverlay option = new WalkingRouteOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(option);

            option.setData(walkingRouteResult.getRouteLines().get(0));
            option.addToMap();
            option.zoomToSpan();
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mapsearch_tv:
                if(mapSearch_et.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"终点不能为空",Toast.LENGTH_SHORT).show();
                    //listview设置
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.listcell_mapsearch,testData);
                    mapSearch_lv.setAdapter(adapter);
                    mapSearch_lv.setVisibility(View.VISIBLE);
                    //
                    return;
            }
                start = PlanNode.withCityNameAndPlaceName(mylocation.getCity(),mylocation.getAddrStr());
                Toast.makeText(getActivity(),mylocation.getAddrStr()+"\n"+mylocation.getAddress(),Toast.LENGTH_SHORT).show();
                end = PlanNode.withCityNameAndPlaceName("武汉",mapSearch_et.getText().toString());
                routeSearch.walkingSearch(new WalkingRoutePlanOption().from(start).to(end));
                break;
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //Toast.makeText(getActivity(),String.valueOf(bdLocation.getLongitude()),Toast.LENGTH_SHORT).show();
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
                mylocation = bdLocation;
            }
        }
    }

    public boolean mapSearch_lvStatus() {
        if(mapSearch_lv.getVisibility() == View.VISIBLE){
            mapSearch_lv.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isFirstLocate = true;
        Log.d("Tsetfragment","onResume");
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Tsetfragment","onResume");
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapSearch_lv.setVisibility(View.GONE);
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
