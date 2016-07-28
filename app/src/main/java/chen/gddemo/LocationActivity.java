package chen.gddemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gezbox.map.location.Location;
import com.gezbox.map.location.LocationClient;
import com.gezbox.map.location.LocationListener;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class LocationActivity extends Activity {

    private LocationClient client;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tv_result= (TextView) findViewById(R.id.tv_result);


        client = new LocationClient(this);
        client.setLocationListener(new LocationListener() {
            @Override
            public void onLocateSuccess(Location location) {
                tv_result.setText(location.toResultString());
            }

            @Override
            public void onLocateFailure(Location location) {
                tv_result.setText(location.toResultString());
            }
        });

        client.startLocate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.releaseLocate();
        }
    }
}
