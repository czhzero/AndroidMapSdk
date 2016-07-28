package com.gezbox.map.geocoder;

import java.util.List;

/**
 * Created by chenzhaohua on 16/7/26.
 * <p>
 * 地址解析结果
 */
public class GeoCoderResult {

    public List<GeoCoderItem> geoList;

    public static class GeoCoderItem {
        public double latitude;                //纬度
        public double longitude;               //经度
        public String address;                 //地址
        public String city;                    //城市
    }


    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();

        if (geoList == null || geoList.size() == 0) {
            return "";
        }

        for (GeoCoderResult.GeoCoderItem item : geoList) {

            if (item == null) {
                continue;
            }

            buffer.append(item.address + "\n");
            buffer.append(item.city + "\n");
            buffer.append(item.latitude + "\n");
            buffer.append(item.longitude + "\n");
        }

        return buffer.toString();

    }


}
