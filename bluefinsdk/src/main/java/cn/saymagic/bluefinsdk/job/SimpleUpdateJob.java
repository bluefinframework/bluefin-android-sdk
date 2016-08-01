package cn.saymagic.bluefinsdk.job;

import android.app.DownloadManager;
import android.net.Uri;
import android.text.TextUtils;

import cn.saymagic.bluefinsdk.BluefinHandler;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.util.AppUtil;

/**
 * Created by saymagic on 16/7/26.
 */
public class SimpleUpdateJob extends Job<Long> {

    @Override
    public Long perform() throws Exception {
        CheckUpdateJob baseJob = new CheckUpdateJob();
        baseJob.mount(mServerUrl, mHandler, mPackageName, mIdentity, mJobId, mContext);
        BluefinApkData apkData = baseJob.perform();
        if (apkData == null || apkData.isEmpty() || apkData.getVersionCode() <= AppUtil.getApplicationVersionCode(mContext)) {
            return -1l;
        }
        //just let system to do the download job.
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkData.getDownloadUrl()));
        request.setDestinationInExternalPublicDir("Bluefin", apkData.getIdentify() + ".apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setMimeType("application/vnd.android.package-archive");
        request.setTitle(AppUtil.getApplicationPackageName(mContext));
        String updateInfo = apkData.getUpdateInfo();
        if (!TextUtils.isEmpty(updateInfo)) {
            request.setDescription(updateInfo);
        }
        long downloadId = downloadManager.enqueue(request);
        return downloadId;
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
