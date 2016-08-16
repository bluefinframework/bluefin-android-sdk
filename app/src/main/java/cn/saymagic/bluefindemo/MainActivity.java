package cn.saymagic.bluefindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.saymagic.bluefinsdk.Bluefin;
import cn.saymagic.bluefinsdk.callback.BluefinCallback;
import cn.saymagic.bluefinsdk.callback.BluefinJobWatcher;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;


public class MainActivity extends AppCompatActivity implements BluefinCallback {

    private static final String TAG = "FishListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bluefin.init(this, this, "http://10.240.141.70:2556/");
        setContentView(R.layout.activity_main);
    }

    public void listAllVersion(View view) {
        Bluefin.listAllVersion(new BluefinJobWatcher<List<BluefinApkData>>() {
            @Override
            public void onResult(List<BluefinApkData> bluefinApkDatas, Exception e) {
                Toast.makeText(MainActivity.this, String.valueOf(bluefinApkDatas), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkUpdate(View view) {
        Bluefin.checkUpdate(new BluefinJobWatcher<BluefinApkData>() {
            @Override
            public void onResult(BluefinApkData data, Exception e) {
                Log.i(TAG, "onResult: " + String.valueOf(data) + " e: " + String.valueOf(e));
                Toast.makeText(MainActivity.this, String.valueOf(data), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void simpleUpdate(View view) {
        Bluefin.simpleUpdate();
    }

    @Override
    public void onCheckUpdateResult(String id, BluefinApkData data, Exception e) {
        Log.i(TAG, "onCheckUpdateResult-id: " + id + " data: " + String.valueOf(data) + " e: " + String.valueOf(e));
    }

    @Override
    public void onListAllVersionResult(String id, List<BluefinApkData> datas, Exception e) {
        Log.i(TAG, "onListAllVersionResult-id: " + id + " data: " + String.valueOf(datas) + " e: " + String.valueOf(e));
    }

    @Override
    public void onRetraceResult(String id, String result, Exception e) {
        Log.i(TAG, "onRetraceResult-id: " + id + " result: " + String.valueOf(result) + " e: " + String.valueOf(e));
    }

    @Override
    public void onSimpleUpdateJobResult(String id, long result, Exception e) {
        Log.i(TAG, "onSimpleUpdateJobResult-id: " + id + " result: " + String.valueOf(result) + " e: " + String.valueOf(e));
    }

}
