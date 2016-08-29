package cn.saymagic.bluefinsdk;

import android.app.job.JobScheduler;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.saymagic.bluefinsdk.callback.BluefinCallback;
import cn.saymagic.bluefinsdk.callback.BluefinCallbackAdapter;
import cn.saymagic.bluefinsdk.callback.BluefinJobWatcher;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.job.CheckUpdateJob;
import cn.saymagic.bluefinsdk.job.JobService;
import cn.saymagic.bluefinsdk.job.ListAllVersionJob;
import cn.saymagic.bluefinsdk.job.ListApksJob;
import cn.saymagic.bluefinsdk.job.RetraceJob;
import cn.saymagic.bluefinsdk.job.SimpleUpdateJob;

/**
 * Created by saymagic on 16/6/21.
 */
public final class Bluefin {

    private static BluefinHolder sConfig;

    private static JobService sJobService;

    private static Handler sUIHandler;

    private static BluefinCallbackAdapter mCallback;

    private static boolean inited;

    public static void init(Context context) {
        init(context, null);
    }

    public static void init(Context context, BluefinCallback callback) {
        init(context, callback, "", Executors.newSingleThreadExecutor());
    }

    public static void init(Context context, BluefinCallback callback, String serverUrl, Executor executor) {
        if (inited) {
//            throw new RuntimeException("you are already init the Bluefin.");
            Log.e("Bluefin", "you are already init the Bluefin.");
        }
        if (context == null) {
            throw new NullPointerException("bluefin init: context can't be null.");
        }
        String identify = "";
        context = context.getApplicationContext();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            if (appInfo != null) {
                if (TextUtils.isEmpty(serverUrl)) {
                    serverUrl = appInfo.metaData == null ? "" : appInfo.metaData.getString("BLUEFIN_SERVER_URL");
                    //double check
                    if (TextUtils.isEmpty(serverUrl)) {
                        throw new NullPointerException("bluefi init: server url can't be null");
                    }
                }
                identify = appInfo.metaData == null ? "" : appInfo.metaData.getString("bluefinidentify");
            }
            if (TextUtils.isEmpty(identify)) {
                identify = String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sConfig = new BluefinHolder();
        sConfig.setContext(context);
        sConfig.setServerUrl(serverUrl);

        mCallback = new BluefinCallbackAdapter();
        if (callback != null) {
            mCallback.setCustomCallBack(callback);
        }

        sConfig.setPackageName(context.getPackageName());

        sUIHandler = new BluefinHandler(Looper.getMainLooper(), mCallback);
        sConfig.setHandler(sUIHandler);

        sJobService = new JobService(sConfig.getServerUrl(), sConfig.getHandler(), sConfig.getPackageName(), identify, sConfig.getContext(), executor);
        sJobService.start();
        inited = true;
    }

    /**
     * just search the lastest version apk in the server and return apk info,
     * this method do nothing about downloading.
     *
     * @return
     */
    public static String checkUpdate() {
        checkInit();
        return sJobService.enqueue(new CheckUpdateJob());
    }

    public static void checkUpdate(BluefinJobWatcher<BluefinApkData> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new CheckUpdateJob(), watcher, mCallback);
    }

    public static String simpleUpdate() {
        checkInit();
        return sJobService.enqueue(new SimpleUpdateJob());
    }

    public static String listAllVersion() {
        checkInit();
        return sJobService.enqueue(new ListAllVersionJob());
    }

    public static void listAllVersion(BluefinJobWatcher<List<BluefinApkData>> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new ListAllVersionJob(), watcher, mCallback);
    }

    public static String listAllVersion(String packageName) {
        checkInit();
        return sJobService.enqueue(new ListAllVersionJob(packageName));
    }

    public static void listAllVersion(String packageName, BluefinJobWatcher<List<BluefinApkData>> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new ListAllVersionJob(packageName), watcher, mCallback);
    }

    public static String retrace(String s) {
        checkInit();
        return sJobService.enqueue(new RetraceJob(s));
    }

    public static void retrace(String s, BluefinJobWatcher<String> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new RetraceJob(s), watcher, mCallback);
    }

    public static String retrace(File f) {
        checkInit();
        return sJobService.enqueue(new RetraceJob(f));
    }


    public static void retrace(File f, BluefinJobWatcher<String> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new RetraceJob(f), watcher, mCallback);
    }

    public static void listAllApks() {
        checkInit();
        sJobService.enqueue(new ListApksJob());
    }


    public static void listAllApks(BluefinJobWatcher<List<BluefinApkData>> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new ListApksJob(), watcher, mCallback);
    }

    private static void checkInit() {
        if (!inited) {
            throw new RuntimeException("you must init Bluefin before you use Bluefin functions");
        }
    }
}
