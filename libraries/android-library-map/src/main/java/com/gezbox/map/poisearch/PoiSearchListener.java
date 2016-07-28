package com.gezbox.map.poisearch;

/**
 * Created by chenzhaohua on 16/7/26.
 * Poi检索回调接口
 */
public interface PoiSearchListener {

    void onSearchSuccess(PoiSearchResult result);
    void onSearchFailure(String message);

}
