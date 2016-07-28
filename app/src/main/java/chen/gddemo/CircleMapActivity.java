package chen.gddemo;

import android.graphics.Color;
import android.os.Bundle;

import com.gezbox.map.overlay.CircleLayer;
import com.gezbox.map.overlay.PolyLineLayer;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class CircleMapActivity extends BasicMapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testCircle();
    }


    private void testCircle() {
        CircleLayer circleLayer = new CircleLayer(mapView);
        circleLayer.addCircle(30.200641, 120.212713, 4000, Color.argb(50, 1, 1, 1), Color.argb(50, 1, 1, 1), 25);
    }
}
