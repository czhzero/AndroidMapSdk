package com.gezbox.map.overlay;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.gezbox.map.utils.LogHelper;
import com.gezbox.map.view.CustomMapView;

/**
 * Created by chenzhaohua on 16/7/27.
 * <p>
 * 圆形图层类，用于在地图画圆
 */
public class CircleLayer {

    private AMap mAmap;


    public CircleLayer(CustomMapView mapView) {
        mAmap = mapView.getMap();
    }


    public void addCircle(double lat, double lon, double radius, int strokeColor,
                          int fillColor, float strokeWidth) {

        LogHelper.d("CircleLayer addCircle : " + "lat = " + lat + "lon = " + lon + "radius ="
                + radius + "strokeColor = " + strokeColor + "fillColor = " + fillColor
                + "strokeWidth = " + strokeWidth);

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(lat, lon));
        circleOptions.radius(radius);
        circleOptions.strokeColor(strokeColor);
        circleOptions.fillColor(fillColor);
        circleOptions.strokeWidth(strokeWidth);
        mAmap.addCircle(circleOptions);
    }

}
