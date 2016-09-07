package cn.saymagic.bluefinsdk;

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

import cn.saymagic.bluefinsdk.callback.BluefinCallbackAdapter;
import cn.saymagic.bluefinsdk.callback.BluefinJobWatcher;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.entity.PingResult;
import cn.saymagic.bluefinsdk.exception.BluefinException;
import cn.saymagic.bluefinsdk.job.GetNewestVersionJob;
import cn.saymagic.bluefinsdk.job.JobService;
import cn.saymagic.bluefinsdk.job.ListAllVersionJob;
import cn.saymagic.bluefinsdk.job.ListApksJob;
import cn.saymagic.bluefinsdk.job.PingJob;
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

    public static void init(Context context, String serverUrl) {
        init(context, serverUrl, Executors.newSingleThreadExecutor());
    }

    public static void init(Context context, String serverUrl, Executor executor) {
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
                        throw new NullPointerException("bluefin init: server url can't be null");
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sConfig = new BluefinHolder();
        sConfig.setContext(context);
        sConfig.setServerUrl(serverUrl);

        mCallback = new BluefinCallbackAdapter();

        sUIHandler = new BluefinHandler(Looper.getMainLooper(), mCallback);
        sConfig.setHandler(sUIHandler);

        sJobService = new JobService(sConfig.getServerUrl(), sConfig.getHandler(), sConfig.getContext(), executor);
        sJobService.start();
        inited = true;
    }

    /**
     * sync ping the server
     *
     * @return
     * @throws BluefinException
     */
    public static PingResult ping() throws BluefinException {
        checkInit();
        return sJobService.directlyRun(new PingJob());
    }

    /**
     * async ping the server
     *
     * @param watcher
     */
    public static void ping(BluefinJobWatcher<PingResult> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new PingJob(), watcher, mCallback);
    }

    /**
     * async search the newest version apk in the server and return apk info,
     * this method do nothing about downloading.
     *
     * @return
     */
    public static BluefinApkData checkNewestVersion(String packageName) throws BluefinException {
        checkInit();
        return sJobService.directlyRun(new GetNewestVersionJob(packageName));
    }

    /**
     * async search the newest version apk in the server and return apk info,
     * this method do nothing about downloading.
     *
     * @return
     */
    public static void checkNewestVersion(String packageName, BluefinJobWatcher<BluefinApkData> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new GetNewestVersionJob(packageName), watcher, mCallback);
    }

    /**
     * check the newest version for package, if server has newer version , download it and show notification
     *
     * @param packageName
     * @return
     */
    public static String simpleUpdate(String packageName) {
        checkInit();
        return sJobService.enqueue(new SimpleUpdateJob(packageName));
    }

    /**
     * sync to list all version info for package
     *
     * @param packageName
     * @return
     */
    public static List<BluefinApkData> listAllVersion(String packageName) throws BluefinException {
        checkInit();
        return sJobService.directlyRun(new ListAllVersionJob(packageName));
    }

    /**
     * async to list all version info for package
     *
     * @param packageName
     * @param watcher
     */
    public static void listAllVersion(String packageName, BluefinJobWatcher<List<BluefinApkData>> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new ListAllVersionJob(packageName), watcher, mCallback);
    }

    /**
     * sync to retrace string s
     *
     * @param s           string to be retraced
     * @param packageName
     * @param identity
     * @return
     * @throws BluefinException
     */
    public static String retrace(String s, String packageName, String identity) throws BluefinException {
        checkInit();
        return sJobService.directlyRun(new RetraceJob(s, packageName, identity));
    }

    /**
     * async to retrace string s
     *
     * @param s           string to be retraced
     * @param packageName
     * @param identity
     * @param watcher
     */
    public static void retrace(String s, String packageName, String identity, BluefinJobWatcher<String> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new RetraceJob(s, packageName, identity), watcher, mCallback);
    }

    /**
     * sync to retrace file f
     *
     * @param f           file to be retraced
     * @param packageName
     * @param identity
     * @return
     * @throws BluefinException
     */
    public static String retrace(File f, String packageName, String identity) throws BluefinException {
        checkInit();
        return sJobService.directlyRun(new RetraceJob(f, packageName, identity));
    }

    /**
     * async to retrace string f
     *
     * @param f           f to be retraced
     * @param packageName
     * @param identity
     * @param watcher
     */
    public static void retrace(File f, String packageName, String identity, BluefinJobWatcher<String> watcher) {
        checkInit();
        sJobService.enqueueWithWatcher(new RetraceJob(f, packageName, identity), watcher, mCallback);
    }

    /**
     * sync to list all various apk in the server
     * this method will return the newest version for apk
     */
    public static List<BluefinApkData> listAllApks() throws BluefinException {
        checkInit();
        return sJobService.directlyRun(new ListApksJob());
    }

    /**
     * async to list all various apk in the server
     * this method will return the newest version for apk
     *
     * @param watcher
     */
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
