package cn.saymagic.bluefinsdk;

import android.content.Context;
import android.os.Handler;

import cn.saymagic.bluefinsdk.callback.BluefinCallback;

/**
 * Created by saymagic on 16/6/21.
 */
public class BluefinHolder {

    /**
     * application context
     */
    private Context mContext;

    /**
     * application name
     */
    private String mPackageName;

    /**
     * server address
     */
    private String mServerUrl;

    /**
     * the switch, bluefin won't do nothing if false.
     */
    private boolean mSwitchOn;

    /**
     * the callback for client,
     */
    private BluefinCallback mCallBack;

    /**
     * UI handler for sending message to UI from background thread
     */
    private Handler mHandler;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isSwitchOn() {
        return mSwitchOn;
    }

    public void setSwitchOn(boolean mSwitchOn) {
        this.mSwitchOn = mSwitchOn;
    }

    public String getServerUrl() {
        return mServerUrl;
    }

    public void setServerUrl(String mAimUrl) {
        this.mServerUrl = mAimUrl;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }
}
