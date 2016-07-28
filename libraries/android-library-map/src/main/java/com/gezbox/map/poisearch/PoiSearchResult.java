package com.gezbox.map.poisearch;

import java.util.List;

/**
 * Created by chenzhaohua on 16/7/26.
 * Poi检索结果类
 */
public class PoiSearchResult {

    public List<SearchResultItem> searchResultItems;                //搜索结果
    public List<SuggestionResultItem> suggestionResultItems;        //搜索不到后的推荐结果

    public static class SearchResultItem {
        public String name;
        public String city;
        public String address;
        public double latitude;
        public double longitude;
    }

    public static class SuggestionResultItem {
        public String cityName;     //城市名称
        public int suggestionNum;   //推荐数目 d
    }

}
