package cn.saymagic.bluefinsdk.entity;

import org.json.JSONObject;

/**
 * Created by saymagic on 16/6/21.
 */
public final class BluefinApkData {

    private double mFileSize;

    private String mQrlink;

    private String mName;

    private long mUpdateTime;

    private String mIconUrl;

    private String mVersionCode;

    private String mVersionName;

    private String mMinVersion;

    private String mTargetVersion;

    private String mPackageName;

    private String mDownloadUrl;

    private String mFileMd5;

    private String mUpdateInfo;

    private String mExtData;

    private String mIdentify;

    private boolean mIsEmpty = true;

    private BluefinApkData() {}

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public double getSize() {
        return mFileSize;
    }

    public void setSize(double mSize) {
        this.mFileSize = mSize;
    }

    public String getQrlink() {
        return mQrlink;
    }

    public void setQrlink(String mQrlink) {
        this.mQrlink = mQrlink;
    }

    public long getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(long mUpdateTime) {
        this.mUpdateTime = mUpdateTime;
    }

    public int getVersionCode() {
        return Integer.valueOf(mVersionCode);
    }

    public void setVersionCode(String mVersionCode) {
        this.mVersionCode = mVersionCode;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String mVersionName) {
        this.mVersionName = mVersionName;
    }

    public String getMinVersion() {
        return mMinVersion;
    }

    public void setMinVersion(String mMinVersion) {
        this.mMinVersion = mMinVersion;
    }

    public String getTargetVersion() {
        return mTargetVersion;
    }

    public void setTargetVersion(String mTargetVersion) {
        this.mTargetVersion = mTargetVersion;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String mDownloadUrl) {
        this.mDownloadUrl = mDownloadUrl;
    }

    public String getFileMd5() {
        return mFileMd5;
    }

    public void setFileMd5(String mFileMd5) {
        this.mFileMd5 = mFileMd5;
    }

    public String getUpdateInfo() {
        return mUpdateInfo;
    }

    public void setUpdateInfo(String mUpdateInfo) {
        this.mUpdateInfo = mUpdateInfo;
    }

    public String getExtData() {
        return mExtData;
    }

    public void setExtData(String mExtData) {
        this.mExtData = mExtData;
    }

    public String getIdentify() {
        return mIdentify;
    }

    public void setIdentify(String mIdentify) {
        this.mIdentify = mIdentify;
    }

    public boolean isEmpty() {
        return mIsEmpty;
    }

    public void setIsEmpty(boolean mIsEmpty) {
        this.mIsEmpty = mIsEmpty;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public BluefinApkData fromJSON(JSONObject object) {
        if (object == null) {
            setIsEmpty(true);
            return this;
        }
        setPackageName(object.optString("package"));
        setSize(object.optLong("size"));
        setMinVersion(object.optString("minVersion"));
        setTargetVersion(object.optString("targetVersion"));
        setVersionName(object.optString("versionName"));
        setVersionCode(object.optString("versionCode"));
        setUpdateTime(object.optLong("updateTime"));
        setQrlink(object.optString("qrlink"));
        setDownloadUrl(object.optString("downloadUrl"));
        setFileMd5(object.optString("fileMd5"));
        setUpdateInfo(object.optString("updateInfo"));
        setPackageName(object.optString("package"));
        setExtData(object.optString("extData"));
        setIdentify(object.optString("identity"));
        setIconUrl(object.optString("iconUrl"));
        setName(object.optString("name"));
        setIsEmpty(false);
        return this;
    }

    public static BluefinApkData from(JSONObject object) {
        return new BluefinApkData().fromJSON(object);
    }

    @Override
    public String toString() {
        return "BluefinApkData{" +
                "mFileSize=" + mFileSize +
                ", mQrlink='" + mQrlink + '\'' +
                ", mUpdateTime=" + mUpdateTime +
                ", mVersionCode='" + mVersionCode + '\'' +
                ", mVersionName='" + mVersionName + '\'' +
                ", mMinVersion='" + mMinVersion + '\'' +
                ", mTargetVersion='" + mTargetVersion + '\'' +
                ", mPackageName='" + mPackageName + '\'' +
                ", mDownloadUrl='" + mDownloadUrl + '\'' +
                ", mFileMd5='" + mFileMd5 + '\'' +
                ", mUpdateInfo='" + mUpdateInfo + '\'' +
                ", mExtData='" + mExtData + '\'' +
                ", mIdentify='" + mIdentify + '\'' +
                ", mIconUrl='" + mIconUrl + '\'' +
                ", mName='" + mName + '\'' +
                ", mIsEmpty=" + mIsEmpty +
                '}';
    }
}
