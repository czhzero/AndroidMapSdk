package com.gezbox.map.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.gezbox.map.utils.LogHelper;


/**
 * Created by chenzhaohua on 16/7/25.
 * <p>
 * 高德定位服务
 */
public class LocationClient implements AMapLocationListener {

    private Context mContext;
    private LocationListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;


    public LocationClient(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setLocationListener(LocationListener listener) {
        mListener = listener;
    }


    /**
     * 启动定位服务
     */
    public void startLocate() {
        LogHelper.d("LocationClient startLocate : once");
        mLocationOption = getDefaultOption().setOnceLocation(true);
        startLocate(mLocationOption);
    }


    /**
     * 启动定位服务
     */
    public void startLocate(int span) {
        LogHelper.d("LocationClient startLocate : " + span);
        mLocationOption = getDefaultOption().setInterval(span);
        startLocate(mLocationOption);
    }


    /**
     * 停止定位
     */
    public void stopLocate() {
        LogHelper.d("LocationClient stopLocate");
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    /**
     * 释放定位对象
     */
    public void releaseLocate() {
        LogHelper.d("LocationClient releaseLocate");
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationClient = null;
        }
    }


    /**
     * 启动定位服务
     */
    private void startLocate(AMapLocationClientOption option) {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(mContext);
            mLocationClient.setLocationListener(this);
        }

        mLocationClient.setLocationOption(option);

        if (!mLocationClient.isStarted()) {
            mLocationClient.startLocation();
        }
    }


    /**
     * 获取default option
     *
     * @return
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setNeedAddress(true);                //设置是否需要显示地址信息
        option.setLocationCacheEnable(true);        //设置是否开启缓存
        return option;
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation == null || mListener == null) {
            return;
        }

        LogHelper.d("LocationClient onLocationChanged : " + aMapLocation.toString());


        Location location = new Location();

        //basic
        location.setLocationType(aMapLocation.getLocationType());
        location.setLongitude(aMapLocation.getLongitude());
        location.setLatitude(aMapLocation.getLatitude());
        location.setAccuracy(aMapLocation.getAccuracy());
        location.setProvider(aMapLocation.getProvider());

        //error code
        location.setErrorCode(aMapLocation.getErrorCode());
        location.setErrorDetail(aMapLocation.getLocationDetail());
        location.setErrorInfo(aMapLocation.getErrorInfo());


        //gps
        location.setSpeed(aMapLocation.getSpeed());
        location.setBearing(aMapLocation.getBearing());
        location.setSatellites(aMapLocation.getSatellites());


        //net
        location.setCountry(aMapLocation.getCountry());
        location.setProvince(aMapLocation.getProvince());
        location.setCity(aMapLocation.getCity());
        location.setCityCode(aMapLocation.getCityCode());
        location.setDistrict(aMapLocation.getDistrict());
        location.setAdCode(aMapLocation.getAdCode());
        location.setAddress(aMapLocation.getAddress());
        location.setPoiName(aMapLocation.getPoiName());


        if (location.getErrorCode() == 0) {
            mListener.onLocateSuccess(location);
        } else {
            mListener.onLocateFailure(location);
        }


    }
}
