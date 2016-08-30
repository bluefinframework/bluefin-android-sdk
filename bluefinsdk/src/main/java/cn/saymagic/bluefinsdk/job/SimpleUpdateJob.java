package cn.saymagic.bluefinsdk.job;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

import cn.saymagic.bluefinsdk.BluefinHandler;
import cn.saymagic.bluefinsdk.R;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.exception.BluefinException;
import cn.saymagic.bluefinsdk.util.AppUtil;
import cn.saymagic.bluefinsdk.util.EncryUtil;

/**
 * Created by saymagic on 16/7/26.
 */
public class SimpleUpdateJob extends Job<Long> {

    public static final String DOWNLOAD_DIR_NAME = "Bluefin";
    public static final int NOTIFICATION_ID = 1;

    private String mPakcageName;

    public SimpleUpdateJob(String mPakcageName) {
        this.mPakcageName = mPakcageName;
    }

    @Override
    public Long perform() throws BluefinException {
        GetNewestVersionJob baseJob = new GetNewestVersionJob(mPakcageName);
        baseJob.mount(mServerUrl, mHandler,  mJobId, mContext);
        BluefinApkData apkData = baseJob.perform();
        if (apkData == null || apkData.isEmpty() || apkData.getVersionCode() <= AppUtil.getApplicationVersionCode(mPakcageName, mContext)) {
            return -1l;
        }
        String apkName = mPakcageName.replace(".", "-") + "-" + apkData.getIdentify() + ".apk";
        if (isApkFileExist(apkData, apkName)) {
            showUpdateRemind(apkData, apkName);
            return -1l;
        }
        //just let system to do the download job.
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkData.getDownloadUrl()));
        request.setDestinationInExternalPublicDir("Bluefin", apkName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setMimeType("application/vnd.android.package-archive");
        request.setTitle(AppUtil.getApplicationName(mPakcageName, mContext) + mContext.getString(R.string.bluefin_have_new_version));
        String updateInfo = apkData.getUpdateInfo();
        if (!TextUtils.isEmpty(updateInfo)) {
            request.setDescription(updateInfo);
        }
        long downloadId = downloadManager.enqueue(request);
        return downloadId;
    }

    private void showUpdateRemind(@NonNull BluefinApkData apkData, @NonNull String apkName) {
        NotificationManager manger = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle(AppUtil.getApplicationName(mPakcageName, mContext) + mContext.getString(R.string.bluefin_have_new_version));
        builder.setSmallIcon(android.R.drawable.stat_sys_download_done);
        String updateInfo = apkData.getUpdateInfo();
        if (!TextUtils.isEmpty(updateInfo)) {
            builder.setContentText(updateInfo);
        } else {
            builder.setContentText(mContext.getString(R.string.bluefin_download_finish));
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        File file = Environment.getExternalStoragePublicDirectory(DOWNLOAD_DIR_NAME);
        if (file == null || !file.exists() || file.isFile()) {
            return;
        }
        File apk = new File(file.getAbsolutePath(), apkName);
        intent.setDataAndType(Uri.parse("file://" + apk.getAbsolutePath()),
                "application/vnd.android.package-archive");
        PendingIntent pIntent = PendingIntent.getActivity(mContext, 1, intent, 0);
        builder.setContentIntent(pIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        try{
            manger.notify(NOTIFICATION_ID,notification);
        }catch (Exception e){
            Log.w("notify",e.getMessage());
        }
    }

    private boolean isApkFileExist(@NonNull BluefinApkData apkData, @NonNull String name) {
        try {
            File file = Environment.getExternalStoragePublicDirectory(DOWNLOAD_DIR_NAME);
            if (file == null || !file.exists() || file.isFile()) {
                return false;
            }
            File apk = new File(file.getAbsolutePath(), name);
            return apk.exists() && apkData.getFileMd5().equals(EncryUtil.getMD5(apk));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "SimpleUpdateJob";
    }

    @Override
    public void onDone(Long o) {
        sendMessageData(BluefinHandler.SIMPLE_UPDATE_JOB_DONE, o);
    }

    @Override
    public void onFail(Exception e) {
        sendMessageData(BluefinHandler.SIMPLE_UPDATE_JOB_FAILED, e);
    }

    @Override
    public void onCancel(Long id) {
        sendMessageData(BluefinHandler.SIMPLE_UPDATE_JOB_CANCLE, id);
    }
}
