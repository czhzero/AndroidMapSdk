package com.gezbox.map.geocoder;


/**
 * Created by chenzhaohua on 16/7/26.
 *
 * 逆地址解析结果
 */
public class ReGeoCoderResult {

    public String city;             //城市
    public String address;          //详细地址
    public String buildding;        //建筑物


    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();

        buffer.append(city + "\n");
        buffer.append(address + "\n");
        buffer.append(buildding + "\n");

        return buffer.toString();
    }
}
