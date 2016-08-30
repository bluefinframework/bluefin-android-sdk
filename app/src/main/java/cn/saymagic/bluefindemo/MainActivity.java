package cn.saymagic.bluefindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executors;

import cn.saymagic.bluefinsdk.Bluefin;
import cn.saymagic.bluefinsdk.callback.BluefinCallback;
import cn.saymagic.bluefinsdk.callback.BluefinJobWatcher;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;


public class MainActivity extends AppCompatActivity implements BluefinCallback {

    private static final String TAG = "FishListActivity";

    private static final String PACKAGE_EXAMPLE_ONE = "cn.saymagic.bluefindemo";
    private static final String PACKAGE_EXAMPLE_TWO = "com.netease.mail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bluefin.init(this, "http://10.242.8.21:2556/", Executors.newSingleThreadExecutor());
        setContentView(R.layout.activity_main);
    }

    public void listAllVersion(View view) {
//        Bluefin.listAllVersion(PACKAGE_EXAMPLE_ONE, new BluefinJobWatcher<List<BluefinApkData>>() {
//            @Override
//            public void onResult(List<BluefinApkData> bluefinApkDatas, Exception e) {
//                Log.i(TAG, "onResult: " + String.valueOf(bluefinApkDatas) + " e: " + String.valueOf(e));
//                Toast.makeText(MainActivity.this, String.valueOf(bluefinApkDatas), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void checkNewestVersion(View view) {
        Bluefin.checkNewestVersion(PACKAGE_EXAMPLE_TWO, new BluefinJobWatcher<BluefinApkData>() {
            @Override
            public void onResult(BluefinApkData data, Exception e) {
                Log.i(TAG, "onResult: " + String.valueOf(data) + " e: " + String.valueOf(e));
                Toast.makeText(MainActivity.this, String.valueOf(data), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void listAllApks(View view) {
        Bluefin.listAllApks(new BluefinJobWatcher<List<BluefinApkData>>() {
            @Override
            public void onResult(List<BluefinApkData> bluefinApkDatas, Exception e) {
                Log.i(TAG, "onResult: " + String.valueOf(bluefinApkDatas) + " e: " + String.valueOf(e));
                Toast.makeText(MainActivity.this, String.valueOf(bluefinApkDatas), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void simpleUpdate(View view) {
        Bluefin.simpleUpdate(PACKAGE_EXAMPLE_ONE);
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

    @Override
    public void onListAllApkResult(String id, List<BluefinApkData> datas, Exception e) {
        Log.i(TAG, "onListAllApkResult-id: " + id + " result: " + String.valueOf(datas) + " e: " + String.valueOf(e));
    }

}
