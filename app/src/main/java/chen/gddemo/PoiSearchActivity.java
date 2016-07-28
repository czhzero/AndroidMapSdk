package chen.gddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gezbox.map.poisearch.PoiSearchClient;
import com.gezbox.map.poisearch.PoiSearchListener;
import com.gezbox.map.poisearch.PoiSearchResult;

import java.util.List;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class PoiSearchActivity extends Activity {


    private Button btn_1;
    private Button btn_2;
    private EditText et_text;
    private TextView tv_result;

    private PoiSearchClient poiKeywordSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisearch);
        btn_1 = (Button) findViewById(R.id.btn_search);
        btn_2 = (Button) findViewById(R.id.btn_search2);
        et_text = (EditText) findViewById(R.id.et_search);
        tv_result = (TextView) findViewById(R.id.tv_result);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testPoiSearch(et_text.getText().toString().trim());

            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testNearBySearch(et_text.getText().toString().trim());

            }
        });
    }


    private void testPoiSearch(String address) {

        poiKeywordSearch = new PoiSearchClient(this);
        poiKeywordSearch.setPoiSearchListener(new PoiSearchListener() {
            @Override
            public void onSearchSuccess(PoiSearchResult result) {

                List<PoiSearchResult.SearchResultItem> listA = result.searchResultItems;
                List<PoiSearchResult.SuggestionResultItem> listB = result.suggestionResultItems;

                StringBuffer buffer = new StringBuffer();

                for (PoiSearchResult.SearchResultItem item : listA) {

                    buffer.append(item.city + "\n");
                    buffer.append(item.name + "\n");
                    buffer.append(item.address + "\n");
                    buffer.append(item.longitude + "\n");
                    buffer.append(item.latitude + "\n");

                }

                for (PoiSearchResult.SuggestionResultItem item : listB) {
                    buffer.append(item.cityName + "\n");
                    buffer.append(item.suggestionNum + "\n");
                }

                buffer.append("-------------------------------------------------------" + "\n");

                tv_result.setText(buffer.toString());


            }

            @Override
            public void onSearchFailure(String message) {

                tv_result.setText(message);

            }
        });

        poiKeywordSearch.startSearch(address, "", "杭州市", 0);
    }


    private void testNearBySearch(String address) {

        poiKeywordSearch = new PoiSearchClient(this);
        poiKeywordSearch.setPoiSearchListener(new PoiSearchListener() {
            @Override
            public void onSearchSuccess(PoiSearchResult result) {

                List<PoiSearchResult.SearchResultItem> listA = result.searchResultItems;
                List<PoiSearchResult.SuggestionResultItem> listB = result.suggestionResultItems;

                StringBuffer buffer = new StringBuffer();

                for (PoiSearchResult.SearchResultItem item : listA) {

                    buffer.append(item.city + "\n");
                    buffer.append(item.name + "\n");
                    buffer.append(item.address + "\n");
                    buffer.append(item.longitude + "\n");
                    buffer.append(item.latitude + "\n");
                }

                for (PoiSearchResult.SuggestionResultItem item : listB) {
                    buffer.append(item.cityName + "\n");
                    buffer.append(item.suggestionNum + "\n");
                }


                buffer.append("-------------------------------------------------------" + "\n");

                tv_result.setText(buffer.toString());


            }

            @Override
            public void onSearchFailure(String message) {

                tv_result.setText(message);

            }
        });

        poiKeywordSearch.startNearBySearch(30.21316, 120.214939, "", PoiSearchClient.DEFAULT_POI_TYPES.get(4), "杭州市", 0);
    }

}
