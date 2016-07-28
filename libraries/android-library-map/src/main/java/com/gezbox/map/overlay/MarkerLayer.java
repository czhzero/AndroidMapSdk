package com.gezbox.map.overlay;

import android.text.TextUtils;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.gezbox.map.utils.LogHelper;
import com.gezbox.map.view.CustomMapView;

import java.util.List;

/**
 * Created by chenzhaohua on 16/7/27.
 *
 * 地图Marker类，用于在地图上添加地址的marker
 *
 */
public class MarkerLayer implements AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener,
        AMap.OnMarkerDragListener, AMap.OnMapLoadedListener, AMap.InfoWindowAdapter {

    private AMap mAmap;
    private int mFocusMarkerDrawableId;
    private int mCommonMarkerDrawableId;


    public MarkerLayer(CustomMapView mapView) {
        mAmap = mapView.getMap();
        mAmap.setOnMarkerDragListener(this);        // 设置marker可拖拽事件监听器
        mAmap.setOnMapLoadedListener(this);         // 设置amap加载成功事件监听器
        mAmap.setOnMarkerClickListener(this);       // 设置点击marker事件监听器
        mAmap.setOnInfoWindowClickListener(this);   // 设置点击infoWindow事件监听器
        mAmap.setInfoWindowAdapter(this);           // 设置自定义InfoWindow样式
    }


    /**
     * 添加marker
     *
     * @param lat
     * @param lon
     */
    public void addMarker(double lat, double lon) {
        addMarker(lat, lon, null);
    }


    /**
     * 添加marker
     *
     * @param lat
     * @param lon
     * @param title
     */
    public void addMarker(double lat, double lon, String title) {

        LogHelper.d("MarkerLayer addMarker : " + "lat = " + lat + "lon = " + lon + "title ="
                + title);


        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(lat, lon));

        if (!TextUtils.isEmpty(title)) {
            options.title(title);
        }

        options.icon(getCommonMarkerIcon());
        options.draggable(true);
        mAmap.addMarker(options);

    }

    /**
     * 设置某个marker为焦点
     *
     * @param lat
     * @param lon
     */
    public void focusMarker(double lat, double lon) {
        List<Marker> markers = mAmap.getMapScreenMarkers();

        if (markers == null || markers.size() == 0) {
            return;
        }

        //自己当前位置的marker不做任何处理
        if (isMyLocation(lat, lon)) {
            return;
        }

        for (Marker marker : markers) {
            LatLng latLng = marker.getPosition();

            //自己当前位置的marker不做任何处理
            if (isMyLocation(latLng.latitude, latLng.longitude)) {
                continue;
            }

            //选中的marker,改变icon,弹出info
            if (latLng.latitude == lat && latLng.longitude == lon) {
                marker.setIcon(getFocusMarkerIcon());
                marker.showInfoWindow();
            } else {
                marker.setIcon(getCommonMarkerIcon());
            }
        }
    }


    /**
     * 设置marker图片
     *
     * @param focusId  正选中marker时的图片
     * @param commonId 未选中marker时的图片
     */
    public void setMarkerDrawble(int focusId, int commonId) {
        mFocusMarkerDrawableId = focusId;
        mCommonMarkerDrawableId = commonId;
    }


    /**
     * 清除所有marker
     */
    public void clearAllMarker() {
        mAmap.clear();
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        LogHelper.d("onInfoWindowClick");

    }

    @Override
    public void onMapLoaded() {
        LogHelper.d("onMapLoaded");

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LogHelper.d("onMarkerClick");
        //TODO 增加自定义事件
        focusMarker(marker.getPosition().latitude, marker.getPosition().longitude);
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        LogHelper.d("onMarkerDragStart");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LogHelper.d("onMarkerDrag");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LogHelper.d("onMarkerDragEnd");

    }

    @Override
    public View getInfoWindow(Marker marker) {
        LogHelper.d("getInfoWindow");
        //TODO 增加自定义布局
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LogHelper.d("getInfoContents");
        return null;
    }


    /**
     * 判断是否为当前所在位置
     *
     * @param lat
     * @param lon
     * @return
     */
    private boolean isMyLocation(double lat, double lon) {
        if (mAmap.getMyLocation().getLatitude() == lat && mAmap.getMyLocation().getLongitude() == lon) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取focus状态的Icon
     *
     * @return
     */
    private BitmapDescriptor getFocusMarkerIcon() {
        if (mFocusMarkerDrawableId > 0) {
            return BitmapDescriptorFactory.fromResource(mFocusMarkerDrawableId);
        } else {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }
    }

    /**
     * 获取common状态的Icon
     *
     * @return
     */
    private BitmapDescriptor getCommonMarkerIcon() {
        if (mCommonMarkerDrawableId > 0) {
            return BitmapDescriptorFactory.fromResource(mCommonMarkerDrawableId);
        } else {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        }
    }


}
