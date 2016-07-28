package com.gezbox.map.location;

/**
 * Created by chenzhaohua on 16/7/25.
 * 定位结果回调接口
 */
public interface LocationListener {

    void onLocateSuccess(Location location);

    void onLocateFailure(Location location);

}
