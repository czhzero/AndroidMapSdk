package com.gezbox.map.overlay;

import android.graphics.Color;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.PolylineOptions;
import com.gezbox.map.utils.LogHelper;
import com.gezbox.map.view.CustomMapView;

/**
 * Created by chenzhaohua on 16/7/27.
 *
 * 绘制直线类，用于在地图上绘制直线
 *
 */
public class PolyLineLayer {

    private AMap mAMap;

    private final static int COMMON_LINE = 0;
    private final static int DOTTED_LINE = 1;
    private final static int GEODESIC_LINE = 2;


    private final static int DEFAULT_COLOR = Color.RED;
    private final static int DEFAULT_WIDTH = 2;
    private final static int DEFAULT_STYLE = COMMON_LINE;


    public PolyLineLayer(CustomMapView mapView) {
        mAMap = mapView.getMap();

    }


    public void addLine(double startLat, double startLon, double endLat, double endLon) {

        LogHelper.d("PolyLineLayer addLine : " + "startLat = " + startLat + "startLon = "
                + startLon + "endLat = " + endLat + "endLon = " + endLon);

        PolylineOptions options = new PolylineOptions();
        options.add(new LatLng(startLat, startLon), new LatLng(endLat, endLon));
        options.color(DEFAULT_COLOR);
        options.width(DEFAULT_WIDTH);
        mAMap.addPolyline(options);
        mAMap.invalidate();
    }


    //TODO 可以增加自定义属性，同时点也可以多个, 若要修改已经绘制成功的线条
    //TODO 则需要改变PolyLine对象, 建议在上层应用中，不直接使用高德包的内容


}
