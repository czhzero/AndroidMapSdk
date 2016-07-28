package chen.gddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class LauncherActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_10).setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.btn_1:
                intent.setClass(this, LocationActivity.class);
                break;
            case R.id.btn_2:
            case R.id.btn_3:
                intent.setClass(this, PoiSearchActivity.class);
                break;
            case R.id.btn_4:
            case R.id.btn_5:
                intent.setClass(this, GeoCoderActivity.class);
                break;
            case R.id.btn_6:
                intent.setClass(this, BasicMapActivity.class);
                break;
            case R.id.btn_7:
                intent.setClass(this, LineMapActivity.class);
                break;
            case R.id.btn_8:
                intent.setClass(this, CircleMapActivity.class);
                break;
            case R.id.btn_9:
                intent.setClass(this, MarkerMapActivity.class);
                break;
            case R.id.btn_10:
                intent.setClass(this, DriverRouteMapActivity.class);
                break;
        }

        startActivity(intent);

    }
}
