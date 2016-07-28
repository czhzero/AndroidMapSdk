package com.gezbox.map.view;

import android.content.Context;
import android.util.AttributeSet;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;

/**
 * Created by chenzhaohua on 16/7/26.
 *
 * 使用CustomMapView过程注意事项:
 * 1.必须在Activity或者Fragment的生命周期中，依次调用onCreate, onResume, onPauseAndRelease,
 *   onSaveInstanceState, onDestroy方法。
 *
 */
public class CustomMapView extends MapView implements AMapLocationListener, LocationSource {

    private Context mContext;
    private AMap mAmap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    public CustomMapView(Context context) {
        super(context);
        init(context);
    }

    public CustomMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public CustomMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    @Deprecated
    public CustomMapView(Context context, AMapOptions aMapOptions) {
        super(context, aMapOptions);
        init(context);
    }



    private void init(Context context) {
        mContext = context.getApplicationContext();
        mAmap = getMap();
    }



    /**
     * onPause时,同时释放location资源
     */
    public void onPauseAndRelease() {
        onPause();
        deactivate();
    }


    /**
     * 设置是否可触发定位并显示定位层
     *
     * @param enabled
     */
    public void setMyLocationEnabled(boolean enabled) {
        mAmap.setLocationSource(this);  //设置定位监听
        mAmap.setMyLocationEnabled(enabled);
    }


    /**
     * 定位成功后的回调
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);  //显示系统小蓝点
            }
        }

    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mContext);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);

            mlocationClient.startLocation();
        }

    }



    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }




}
