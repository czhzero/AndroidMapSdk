package chen.gddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gezbox.map.geocoder.GeoCoderClient;
import com.gezbox.map.geocoder.GeoCoderListener;
import com.gezbox.map.geocoder.GeoCoderResult;
import com.gezbox.map.geocoder.ReGeoCoderResult;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class GeoCoderActivity extends Activity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button btn_search;
    private Button btn_search2;
    private TextView tv_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);
        editText1 = (EditText) findViewById(R.id.et_search);
        editText2 = (EditText) findViewById(R.id.et_lat);
        editText3 = (EditText) findViewById(R.id.et_lon);

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search2 = (Button) findViewById(R.id.btn_search2);

        tv_result = (TextView) findViewById(R.id.tv_result);

        final GeoCoderClient client = new GeoCoderClient(this);

        client.setOnGeocodeSearchListener(new GeoCoderListener() {
            @Override
            public void onGeoCoderSuccess(GeoCoderResult result) {

                StringBuffer buffer = new StringBuffer();

                if (result != null) {
                    for (GeoCoderResult.GeoCoderItem item : result.geoList) {
                        buffer.append(item.address + "\n");
                        buffer.append(item.city + "\n");
                        buffer.append(item.latitude + "\n");
                        buffer.append(item.longitude + "\n");
                    }
                }

                tv_result.setText(buffer.toString());


            }

            @Override
            public void onGeoCoderFailure(String message) {
                tv_result.setText(message);
            }

            @Override
            public void onReGeoCoderSuccess(ReGeoCoderResult result) {
                tv_result.setText(result.address + "\n" + result.buildding + "\n" + result.city);
            }

            @Override
            public void onReGeoCoderFailure(String message) {

                tv_result.setText(message);

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.getLatlon(editText1.getText().toString().trim(), "0571");
            }
        });

        btn_search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.getAddress(Double.valueOf(editText2.getText().toString()), Double.valueOf(editText3.getText().toString()));
            }
        });

    }


}
