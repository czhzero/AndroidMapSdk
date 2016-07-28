package com.gezbox.map.geocoder;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.gezbox.map.utils.LogHelper;

import java.util.ArrayList;

/**
 * Created by chenzhaohua on 16/7/26.
 * <p>
 * 地址解析与反地址解析类
 */
public class GeoCoderClient implements GeocodeSearch.OnGeocodeSearchListener {

    private GeocodeSearch mGeocoderSearch;
    private GeoCoderListener mListener;


    public GeoCoderClient(Context context) {
        mGeocoderSearch = new GeocodeSearch(context.getApplicationContext());
        mGeocoderSearch.setOnGeocodeSearchListener(this);
    }

    /**
     * 设置搜索结果监听
     *
     * @param listener
     */
    public void setOnGeocodeSearchListener(GeoCoderListener listener) {
        mListener = listener;
    }


    /**
     * 地址解析
     *
     * @param keyword 地址
     * @param city    城市 (中文或者中文全拼,citycode,adcode)
     *
     */
    public void getLatlon(String keyword, String city) {
        LogHelper.d("GeoCoderClient getLatlon : " + "keyword = " + keyword + "city = " + city);
        GeocodeQuery query = new GeocodeQuery(keyword, city);
        mGeocoderSearch.getFromLocationNameAsyn(query);
    }


    /**
     * 逆地址解析
     *
     * @param lat
     * @param lon
     */
    public void getAddress(double lat, double lon) {
        LogHelper.d("GeoCoderClient getAddress : " + "lat = " + lat + "lon = " + lon);
        LatLonPoint lp = new LatLonPoint(lat, lon);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(lp, 200, GeocodeSearch.AMAP);
        mGeocoderSearch.getFromLocationAsyn(query);

    }

    /**
     * 接受逆地址解析结果
     *
     * @param regeocodeResult
     * @param rCode           1000为正常
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {

        if (regeocodeResult == null || mListener == null) {
            return;
        }

        LogHelper.d("GeoCoderClient onRegeocodeSearched : " + "\n" + regeocodeResult.toString());

        if (rCode != 1000) {
            mListener.onReGeoCoderFailure("逆地址解析失败");
            return;
        }

        if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {

            ReGeoCoderResult result = new ReGeoCoderResult();
            result.address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            result.buildding = regeocodeResult.getRegeocodeAddress().getBuilding();
            result.city = regeocodeResult.getRegeocodeAddress().getCity();

            mListener.onReGeoCoderSuccess(result);

        } else {
            mListener.onReGeoCoderFailure("未解析到结果");
        }

    }

    /**
     * 接受地址解析结果
     *
     * @param geocodeResult
     * @param rCode         1000为正常
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {

        if (geocodeResult == null || mListener == null) {
            return;
        }

        LogHelper.d("GeoCoderClient onGeocodeSearched : " + "\n" + geocodeResult.toString());


        if (rCode != 1000) {
            mListener.onGeoCoderFailure("地址解析失败");
            return;
        }


        if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null
                && geocodeResult.getGeocodeAddressList().size() > 0) {

            GeoCoderResult result = new GeoCoderResult();
            result.geoList = new ArrayList<>();

            for (GeocodeAddress address : geocodeResult.getGeocodeAddressList()) {
                GeoCoderResult.GeoCoderItem item = new GeoCoderResult.GeoCoderItem();
                item.latitude = address.getLatLonPoint().getLatitude();
                item.longitude = address.getLatLonPoint().getLongitude();
                item.address = address.getFormatAddress();
                item.city = address.getCity();
                result.geoList.add(item);
            }

            mListener.onGeoCoderSuccess(result);

        } else {
            mListener.onGeoCoderFailure("未解析到结果");
        }


    }


}
