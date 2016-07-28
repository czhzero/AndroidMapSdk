package com.gezbox.map.location;


/**
 * Created by chenzhaohua on 16/7/25.
 *
 * 定位结果类
 *
 */
public class Location {


    private int type;                       //定位类型
    private double longitude;               //经度
    private double latitude;                //纬度
    private float accuracy;                 //精度
    private String provider;                //提供者

    //仅GPS定位才会有
    private float speed;                    //速度
    private float bearing;                  //角度
    private int satellites;                 //卫星数目

    //仅网络定位才会有
    private String country;                 //国家
    private String province;                //省份
    private String city;                    //市
    private String cityCode;                //市编码
    private String district;                //区
    private String adCode;                  //区编码
    private String address;                 //地址
    private String poiName;                 //兴趣点

    //状态信息
    private int errorCode;                  //错误码, 0 表示成功
    private String errorInfo;               //错误信息
    private String errorDetail;             //错误详情



    @Override
    public String toString() {
        return "Location{" +
                "type=" + type +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", accuracy=" + accuracy +
                ", provider='" + provider + '\'' +
                ", speed=" + speed +
                ", bearing=" + bearing +
                ", satellites=" + satellites +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", district='" + district + '\'' +
                ", adCode='" + adCode + '\'' +
                ", address='" + address + '\'' +
                ", poiName='" + poiName + '\'' +
                ", errorCode=" + errorCode +
                ", errorInfo=" + errorInfo +
                ", errorDetail=" + errorDetail +
                '}';
    }

    public String toResultString() {

        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + getLocationType() + "\n");
            sb.append("经    度    : " + getLongitude() + "\n");
            sb.append("纬    度    : " + getLatitude() + "\n");
            sb.append("精    度    : " + getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + getProvider() + "\n");

            if (getProvider().equalsIgnoreCase(
                    android.location.LocationManager.GPS_PROVIDER)) {
                // 以下信息只有提供者是GPS时才会有
                sb.append("速    度    : " + getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : "
                        + getSatellites() + "\n");
            } else {
                // 提供者是GPS时是没有以下信息的
                sb.append("国    家    : " + getCountry() + "\n");
                sb.append("省            : " + getProvince() + "\n");
                sb.append("市            : " + getCity() + "\n");
                sb.append("城市编码 : " + getCityCode() + "\n");
                sb.append("区            : " + getDistrict() + "\n");
                sb.append("区域 码   : " + getAdCode() + "\n");
                sb.append("地    址    : " + getAddress() + "\n");
                sb.append("兴趣点    : " + getPoiName() + "\n");
            }
        } else {
            //定位失败
            sb.append("定位失败" + "\n");
            sb.append("错误码:" + getErrorCode() + "\n");
            sb.append("错误信息:" + getErrorInfo() + "\n");
            sb.append("错误描述:" + getErrorDetail() + "\n");
        }
        return sb.toString();
    }


    public int getLocationType() {
        return type;
    }

    public void setLocationType(int ype) {
        this.type = ype;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
