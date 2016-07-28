package com.gezbox.map.geocoder;

/**
 * Created by chenzhaohua on 16/7/26.
 *
 * 地址解析回调
 *
 */
public interface GeoCoderListener {

    void onGeoCoderSuccess(GeoCoderResult result);

    void onGeoCoderFailure(String message);

    void onReGeoCoderSuccess(ReGeoCoderResult result);

    void onReGeoCoderFailure(String message);

}
