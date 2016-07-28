package com.gezbox.map.route;

import android.content.Context;

import com.amap.api.maps2d.AMap;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.gezbox.map.utils.LogHelper;
import com.gezbox.map.view.CustomMapView;

/**
 * Created by chenzhaohua on 16/7/27.
 *
 * 路径规划类
 *
 */
public class RouteSearchClient implements RouteSearch.OnRouteSearchListener {


    private final static int TYPE_BUS = 1;
    private final static int TYPE_DRIVE = 2;
    private final static int TYPE_WALK = 3;

    private Context mContext;
    private RouteSearch mRouteSearch;
    private AMap mAmap;


    public RouteSearchClient(CustomMapView mapView) {
        mContext = mapView.getContext().getApplicationContext();
        mAmap = mapView.getMap();
        mRouteSearch = new RouteSearch(mContext);
        mRouteSearch.setRouteSearchListener(this);
    }


    /**
     * 搜索公交线路
     *
     * @param startLat
     * @param startLon
     * @param endLat
     * @param endLon
     * @param city
     */
    public void searchBusRoute(double startLat, double startLon,
                               double endLat, double endLon, String city) {
        LogHelper.d("RouteSearchClient searchBusRoute : " + "startLat = " + startLat + "startLon = "
                + startLon + "endLat =" + endLat + "endLon = " + endLon + "city = " + city);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                new LatLonPoint(startLat, startLon), new LatLonPoint(endLat, endLon));
        startSearchRoute(fromAndTo, TYPE_BUS, RouteSearch.BusDefault, city);
    }

    /**
     * 搜索驾车线路
     *
     * @param startLat
     * @param startLon
     * @param endLat
     * @param endLon
     */
    public void searchDriveRoute(double startLat, double startLon, double endLat, double endLon) {
        LogHelper.d("RouteSearchClient searchDriveRoute : " + "startLat = " + startLat + "startLon = "
                + startLon + "endLat =" + endLat + "endLon = " + endLon);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                new LatLonPoint(startLat, startLon), new LatLonPoint(endLat, endLon));
        startSearchRoute(fromAndTo, TYPE_DRIVE, RouteSearch.DrivingDefault, null);
    }


    /**
     * 搜索步行线路
     * @param startLat
     * @param startLon
     * @param endLat
     * @param endLon
     */
    public void searchWalkRoute(double startLat, double startLon, double endLat, double endLon) {
        LogHelper.d("RouteSearchClient searchWalkRoute : " + "startLat = " + startLat + "startLon = "
                + startLon + "endLat =" + endLat + "endLon = " + endLon);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                new LatLonPoint(startLat, startLon), new LatLonPoint(endLat, endLon));
        startSearchRoute(fromAndTo, TYPE_WALK, RouteSearch.WalkDefault, null);
    }


    private void startSearchRoute(RouteSearch.FromAndTo fromAndTo, int routeType, int mode, String city) {

        switch (routeType) {

            case TYPE_BUS:
                //第一个参数表示路径规划的起点和终点
                //第二个参数表示公交查询模式
                //第三个参数表示公交查询城市区号
                //第四个参数表示是否计算夜班车, 0表示不计算
                RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, mode,
                        city, 0);
                mRouteSearch.calculateBusRouteAsyn(query);

                break;
            case TYPE_DRIVE:
                //第一个参数表示路径规划的起点和终点
                //第二个参数表示驾车模式
                //第三个参数表示途经点
                //第四个参数表示避让区域
                //第五个参数表示避让道路
                RouteSearch.DriveRouteQuery query2 = new RouteSearch.DriveRouteQuery(fromAndTo,
                        mode, null, null, "");
                mRouteSearch.calculateDriveRouteAsyn(query2);
                break;
            case TYPE_WALK:
                RouteSearch.WalkRouteQuery query3 = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
                mRouteSearch.calculateWalkRouteAsyn(query3);
                break;

        }


    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int errorCode) {
        //TODO 等待增加回调接口处理，暂不处理
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode) {

        mAmap.clear();

        if (errorCode != 1000) {
            return;
        }

        if(driveRouteResult!=null && driveRouteResult.getPaths()!=null && driveRouteResult.getPaths().size()>0) {

            final DrivePath drivePath = driveRouteResult.getPaths().get(0);
            DriverRouteLayer layer = new DriverRouteLayer(
                    mAmap, drivePath,
                    driveRouteResult.getStartPos(),
                    driveRouteResult.getTargetPos(),null);
            layer.removeFromMap();
            layer.addToMap();
            layer.zoomToSpan();
        }

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int errorCode) {
        //TODO 等待增加回调接口处理，暂不处理
    }
}
