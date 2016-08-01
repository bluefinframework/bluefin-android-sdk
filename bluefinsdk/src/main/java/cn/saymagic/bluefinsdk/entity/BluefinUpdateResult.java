package cn.saymagic.bluefinsdk.entity;

/**
 * Created by saymagic on 16/6/21.
 */
public class BluefinUpdateResult {

    private boolean hasNewVersion;

    private BluefinApkData mApkData;

    public boolean hadNewVersion() {
        return hasNewVersion;
    }

    public void setHasNewVersion(boolean hasNewVersion) {
        this.hasNewVersion = hasNewVersion;
    }

    public BluefinApkData getApkData() {
        return mApkData;
    }

    public void setApkData(BluefinApkData apkData) {
        this.mApkData = apkData;
    }
}
