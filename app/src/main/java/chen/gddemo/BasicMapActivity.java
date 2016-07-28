package chen.gddemo;

import android.app.Activity;
import android.os.Bundle;

import com.gezbox.map.setting.MapSettings;
import com.gezbox.map.view.CustomMapView;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class BasicMapActivity extends Activity {


    protected CustomMapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = (CustomMapView) findViewById(R.id.v_map);
        mapView.onCreate(savedInstanceState);
        mapView.setMyLocationEnabled(true);

        MapSettings settings = new MapSettings(mapView);
        settings.setScaleControlsEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.zoomTo(15);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPauseAndRelease();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }



}
