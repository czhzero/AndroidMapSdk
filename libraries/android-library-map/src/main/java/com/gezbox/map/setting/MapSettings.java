package com.gezbox.map.setting;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.gezbox.map.view.CustomMapView;

/**
 * Created by chenzhaohua on 16/7/27.
 * 地图基本信息设置类
 */
public class MapSettings {

    private UiSettings mUiSettings;
    private AMap mAmap;

    public MapSettings(CustomMapView mapView) {

        mAmap = mapView.getMap();
        mUiSettings = mAmap.getUiSettings();
    }


    /**
     * 自定义小蓝点的属性(默认属性)
     */
    public void setMyLocationStyle() {
        //TODO 根据设计师需求设置
    }


    /**
     * 自定义小蓝点的属性
     *
     * @param drawableId      设置小蓝点的图标
     * @param strokeColor     设置圆形的边框颜色
     * @param radiusFillColor 设置圆形的填充颜色
     * @param strokeWidth     设置圆形的边框粗细
     */
    public void setMyLocationStyle(int drawableId, int strokeColor, int radiusFillColor, float strokeWidth) {

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(drawableId));
        myLocationStyle.strokeColor(strokeColor);
        myLocationStyle.radiusFillColor(radiusFillColor);
        // myLocationStyle.anchor(int,int)
        myLocationStyle.strokeWidth(strokeWidth);
        mAmap.setMyLocationStyle(myLocationStyle);
    }



    /**
     * 设置地图默认的定位按钮是否显示
     *
     * @param enabled
     */
    public void setMyLocationButtonEnabled(boolean enabled) {
        mUiSettings.setMyLocationButtonEnabled(enabled);
    }


    /**
     * 设置地图默认的比例尺是否显示
     *
     * @param enabled
     */
    public void setScaleControlsEnabled(boolean enabled) {
        mUiSettings.setScaleControlsEnabled(enabled);
    }

    /**
     * 设置地图默认的缩放按钮是否显示
     *
     * @param enabled
     */
    public void setZoomControlsEnabled(boolean enabled) {
        mUiSettings.setZoomControlsEnabled(enabled);
    }


    /**
     * 设置地图默认的指南针是否显示
     *
     * @param enabled
     */
    public void setCompassEnabled(boolean enabled) {
        mUiSettings.setCompassEnabled(enabled);
    }


    /**
     * 设置地图是否可以手势滑动
     *
     * @param enabled
     */
    public void setScrollGesturesEnabled(boolean enabled) {
        mUiSettings.setScrollGesturesEnabled(enabled);
    }

    /**
     * 设置地图是否可以手势缩放大小
     *
     * @param enabled
     */
    public void setZoomGesturesEnabled(boolean enabled) {
        mUiSettings.setZoomGesturesEnabled(enabled);
    }

    /**
     * 调整地图尺寸比例
     * @param value
     */
    public void zoomTo(float value) {
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(value));
    }


}
