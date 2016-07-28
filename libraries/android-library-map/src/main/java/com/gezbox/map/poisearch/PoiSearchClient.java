package com.gezbox.map.poisearch;

import android.content.Context;
import android.text.TextUtils;


import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.gezbox.map.utils.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenzhaohua on 16/7/25.
 *
 * POI检索类
 * 1.普通检索
 * 2.附近检索
 *
 */
public class PoiSearchClient implements com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener {


    public static final List<String> DEFAULT_POI_TYPES = Arrays.asList("汽车服务", "汽车销售",
            "汽车维修", "摩托车服务", "餐饮服务", "购物服务", "生活服务", "体育休闲服务", "医疗保健服务",
            "住宿服务", "风景名胜", "商务住宅", "政府机构及社会团体", "科教文化服务", "交通设施服务",
            "金融保险服务", "公司企业", "道路附属设施", "地名地址信息", "公共设施");


    private PoiSearch.Query mQuery;
    private PoiSearch mPoiSearch;
    private Context mContext;
    private PoiSearchListener mListener;


    public PoiSearchClient(Context context) {
        mContext = context.getApplicationContext();
    }


    public void setPoiSearchListener(PoiSearchListener listener) {
        mListener = listener;
    }


    /**
     * 开始POI搜索
     *
     * @param keyWord 搜索关键字  (key 与 type 必须填一个)
     * @param type    poi搜索类型 (不设置，默认返回“餐饮服务”、“商务住宅”、“生活服务”)
     * @param city    poi搜索区域（空字符串代表全国）
     * @param page    返回第N页数据
     */
    public void startSearch(String keyWord, String type, String city, int page) {


        LogHelper.d("PoiSearchClient startSearch : " + "keyWord = " + keyWord + "type = " + type + "city ="
                + city + "page = " + page);

        //key 与 type 必须填一个
        if (TextUtils.isEmpty(keyWord) && TextUtils.isEmpty(type)) {
            return;
        }

        if (city == null) {
            city = "";
        }

        if (page < 0) {
            page = 0;
        }

        mQuery = new PoiSearch.Query(keyWord, type, city);
        mQuery.setPageSize(10);     //Max Page
        mQuery.setPageNum(page);
        mQuery.setCityLimit(true);

        mPoiSearch = new PoiSearch(mContext, mQuery);
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiSearch.searchPOIAsyn();
    }

    /**
     * 开始POI附近地址搜索
     *
     * @param lon           经度
     * @param lat           纬度
     * @param keyWord       搜索关键字  (key 与 type 必须填一个)
     * @param type          poi搜索类型 (不设置，默认返回“餐饮服务”、“商务住宅”、“生活服务”)
     * @param city          poi搜索区域（空字符串代表全国）
     * @param page          返回第N页数据
     */
    public void startNearBySearch(double lat, double lon, String keyWord, String type, String city, int page) {

        LogHelper.d("PoiSearchClient startNearBySearch : " + "lat = " + lat + "lon = " + lon + "keyWord = "
                + keyWord + "type = " + type + "city =" + city + "page = " + page);

        //key 与 type 必须填一个
        if (TextUtils.isEmpty(keyWord) && TextUtils.isEmpty(type)) {
            return;
        }

        if (city == null) {
            city = "";
        }

        if (page < 0) {
            page = 0;
        }

        mQuery = new PoiSearch.Query(keyWord, type, city);
        mQuery.setPageSize(10);     //Max Page
        mQuery.setPageNum(page);
        mQuery.setCityLimit(true);

        mPoiSearch = new PoiSearch(mContext, mQuery);
        mPoiSearch.setOnPoiSearchListener(this);
        LatLonPoint lp = new LatLonPoint(lat, lon);
        mPoiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true)); //搜索附近5000米
        mPoiSearch.searchPOIAsyn();

    }




    @Override
    public void onPoiSearched(PoiResult result, int rCode) {

        if (result == null || mListener == null) {
            return;
        }

        LogHelper.d("PoiSearchClient onPoiSearched : " + result.toString());

        if (rCode != 1000) {
            mListener.onSearchFailure("" + rCode);
            return;
        }


        if (result != null && result.getQuery() != null) {// 搜索poi的结果

            // 是否
            if (result.getQuery().equals(mQuery)) {

                PoiSearchResult poiResult = new PoiSearchResult();

                poiResult.searchResultItems = new ArrayList<>();
                poiResult.suggestionResultItems = new ArrayList<>();


                List<PoiItem> poiItems = result.getPois();
                List<SuggestionCity> suggestionCities = result.getSearchSuggestionCitys();


                if ((poiItems == null || poiItems.size() <= 0)
                        && (suggestionCities == null || suggestionCities.size() <= 0)) {
                    mListener.onSearchFailure("未搜索到结果");
                    return;
                }

                for(PoiItem index : poiItems) {
                    PoiSearchResult.SearchResultItem item = new PoiSearchResult.SearchResultItem();
                    item.address = index.getSnippet();
                    item.city = index.getCityName();
                    item.name = index.getTitle();
                    item.latitude = index.getLatLonPoint().getLatitude();
                    item.longitude = index.getLatLonPoint().getLongitude();
                    poiResult.searchResultItems.add(item);
                }


                for(SuggestionCity index:suggestionCities) {
                    PoiSearchResult.SuggestionResultItem item = new PoiSearchResult.SuggestionResultItem();
                    item.cityName = index.getCityName();
                    item.suggestionNum = index.getSuggestionNum();
                    poiResult.suggestionResultItems.add(item);
                }

                mListener.onSearchSuccess(poiResult);


            }
        } else {
            mListener.onSearchFailure("未搜索到结果");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
