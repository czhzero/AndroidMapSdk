package chen.gddemo;

import android.os.Bundle;

import com.gezbox.map.overlay.PolyLineLayer;
import com.gezbox.map.route.RouteSearchClient;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class DriverRouteMapActivity extends BasicMapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testDriverRoute();
    }


    private void testDriverRoute() {
        RouteSearchClient client = new RouteSearchClient(mapView);
        client.searchDriveRoute(30.200641, 120.212713, 30.211545, 120.211769);
    }
}
