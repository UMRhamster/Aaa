package com.cst.whut.aaa.MainFragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.cst.whut.aaa.DataProcess.PoiAdapter;
import com.cst.whut.aaa.DataProcess.PoiPlace;
import com.cst.whut.aaa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12421 on 2017/10/29.
 */

public class FoodFragment extends Fragment implements View.OnClickListener,OnGetRoutePlanResultListener{
    //百度地图
    private BaiduMap baiduMap;
    private MapView mapView;
    private LocationClient locationClient;
    //定位
    private BDLocation mylocation;
    private boolean isFirstLocate = true;
    //检索服务
    PoiSearch poiSearch;
    PoiOverlay poiOverlay;
    private PoiNearbySearchOption poiNearbySearchOption;
    private boolean isFirstOverlay = true;
    private PoiResult result = null;//获取结果，便于处理
    int index;
    //路线
    private RoutePlanSearch routeSearch;
    private PlanNode start;
    private PlanNode end;
    private WalkingRouteLine route;
    WalkingRouteOverlay option;
    private boolean hasRoute = false;
    //美食信息
    RelativeLayout food_info;
    private TextView food_id;
    private TextView food_address;
    private TextView food_phone;
    private Button food_go;
    //toolbar菜单
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        drawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        imageButton = (ImageButton)view.findViewById(R.id.food_toolbar_menu);
        imageButton.setOnClickListener(this);

        food_info = (RelativeLayout)view.findViewById(R.id.foodnearby_info);
        food_id = (TextView)view.findViewById(R.id.restruant_id);
        food_address = (TextView)view.findViewById(R.id.restruant_address);
        food_phone = (TextView)view.findViewById(R.id.restruant_phone);
        food_phone.setOnClickListener(this);
        food_go = (Button)view.findViewById(R.id.restruant_go);
        food_go.setOnClickListener(this);
        //百度地图
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationClient.registerLocationListener(new FoodFragment.MyLocationListener());
        mapView = (MapView)view.findViewById(R.id.bdmap_food);
        baiduMap = mapView.getMap();
        //地图点击事件
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                food_info.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        //覆盖物点击事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                index = bundle.getInt("index");
                food_id.setText(result.getAllPoi().get(index).name);
                food_address.setText(result.getAllPoi().get(index).address);
                food_phone.setText(result.getAllPoi().get(index).phoneNum);
                food_info.setVisibility(View.VISIBLE);
                return false;
            }
        });
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //检索
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        //路径规划
        routeSearch = RoutePlanSearch.newInstance();
        routeSearch.setOnGetRoutePlanResultListener(this);
        //权限
        List<String> permissionList = new ArrayList<>();
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
        option.setScanSpan(2000);  //2秒间隔刷新位置信息
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.restruant_go:
                start = PlanNode.withLocation(new LatLng(mylocation.getLatitude(),mylocation.getLongitude()));
                end = PlanNode.withLocation(result.getAllPoi().get(index).location);
                routeSearch.walkingSearch(new WalkingRoutePlanOption().from(start).to(end));
                break;
            case R.id.restruant_phone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+food_phone.getText().toString()));
                startActivity(intent);
                break;
            case R.id.food_toolbar_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if(walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR){
            Toast.makeText(getActivity(),"未找到结果",Toast.LENGTH_SHORT).show();
        }
        if(walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR){
            return;
        }
        if(walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
            if(hasRoute){
                option.removeFromMap();
            }
            route = walkingRouteResult.getRouteLines().get(0);
            option = new WalkingRouteOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(option);
            option.setData(route);
            option.addToMap();
            option.zoomToSpan();
            hasRoute = true;
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

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
                mylocation = bdLocation;
                //周边搜索
                poiNearbySearchOption = new PoiNearbySearchOption()
                        .pageCapacity(10)
                        .keyword("美食")
                        .location(new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude()))
                        .radius(2000);
                poiSearch.searchNearby(poiNearbySearchOption);
            }
        }
    }

    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if(poiResult == null){
                return;
            }
            result = poiResult;
            if(isFirstOverlay){
                //地图上绘点
                baiduMap.clear();//先清除之前绘的点，防止重复绘点
                poiOverlay = new PoiOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(poiOverlay);
                poiOverlay.setData(poiResult);
                poiOverlay.addToMap();
                isFirstOverlay = false;
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
