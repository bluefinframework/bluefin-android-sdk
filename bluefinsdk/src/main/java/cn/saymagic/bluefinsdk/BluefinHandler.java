package cn.saymagic.bluefinsdk;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;

import cn.saymagic.bluefinsdk.callback.BluefinCallback;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;

/**
 * Created by saymagic on 16/6/25.
 * <p/>
 * translate data from background thread into user's callback.
 */
public class BluefinHandler extends Handler {

    private BluefinCallback mCallBack;

    public static final int CHECK_UPDATE_DONE = 0;
    public static final int CHECK_UPDATE_CANCLE = 1;
    public static final int CHECK_UPDATE_FAILED = 2;

    public static final int LIST_ALL_VERSION_DONE = 3;
    public static final int LIST_ALL_VERSION_CANCLE = 4;
    public static final int LIST_ALL_VERSION_FAILED = 5;

    public static final int RETRACE_DONE = 6;
    public static final int RETRACE_CANCLE = 7;
    public static final int RETRACE_FAILED = 8;

    public static final int SIMPLE_UPDATE_JOB_DONE = 9;
    public static final int SIMPLE_UPDATE_JOB_CANCLE = 10;
    public static final int SIMPLE_UPDATE_JOB_FAILED = 11;

    public static final int LIST_ALL_APKS_DONE = 12;
    public static final int LIST_ALL_APKS_CANCLE = 13;
    public static final int LIST_ALL_APKS_FAILED = 14;

    public BluefinHandler(Looper mainLooper, BluefinCallback callback) {
        super(mainLooper);
        this.mCallBack = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        int what = msg.what;
        Object obj = msg.obj;
        Bundle bundle = msg.getData();
        String jobId = bundle.getString("id");
        switch (what) {
            case CHECK_UPDATE_DONE:
            case CHECK_UPDATE_CANCLE: {
                mCallBack.onCheckUpdateResult(jobId, (BluefinApkData) obj, null);
                break;
            }
            case CHECK_UPDATE_FAILED: {
                mCallBack.onCheckUpdateResult(jobId, null, (Exception) obj);
                break;
            }
            case LIST_ALL_VERSION_DONE:
            case LIST_ALL_VERSION_CANCLE: {
                mCallBack.onListAllVersionResult(jobId, (List<BluefinApkData>) obj, null);
                break;
            }
            case LIST_ALL_VERSION_FAILED: {
                mCallBack.onListAllVersionResult(jobId, null, (Exception) obj);
                break;
            }
            case RETRACE_DONE:
            case RETRACE_CANCLE: {
                mCallBack.onRetraceResult(jobId, (String) obj, null);
                break;
            }
            case RETRACE_FAILED: {
                mCallBack.onRetraceResult(jobId, null, (Exception) obj);
                break;
            }
            case SIMPLE_UPDATE_JOB_DONE:
            case SIMPLE_UPDATE_JOB_CANCLE: {
                mCallBack.onSimpleUpdateJobResult(jobId, (Long) obj, null);
                break;
            }
            case SIMPLE_UPDATE_JOB_FAILED: {
                mCallBack.onSimpleUpdateJobResult(jobId, -1, (Exception) obj);
                break;
            }
            case LIST_ALL_APKS_DONE:
            case LIST_ALL_APKS_CANCLE: {
                mCallBack.onListAllApkResult(jobId, (List<BluefinApkData>) obj, null);
                break;
            }
            case LIST_ALL_APKS_FAILED: {
                mCallBack.onListAllApkResult(jobId, null, (Exception) obj);
                break;
            }
        }

    }
}