package cn.saymagic.bluefinsdk.entity;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by saymagic on 16/8/30.
 */
public class PingResult {

    private String mServerIp;

    private String mClientIp;

    private long mServerTimemillis;

    public String getServerIp() {
        return mServerIp;
    }

    public void setServerIp(String serverIp) {
        this.mServerIp = serverIp;
    }

    public String getClientIp() {
        return mClientIp;
    }

    public void setClientIp(String clientIp) {
        this.mClientIp = clientIp;
    }

    public long getServerTimemillis() {
        return mServerTimemillis;
    }

    public void setServerTimemillis(long serverTimemillis) {
        this.mServerTimemillis = serverTimemillis;
    }

    public static PingResult from(JSONObject object) {
        if (object == null) {
            throw new NullPointerException();
        }
        String sIp = object.optString("serverip"), cIp = object.optString("requestip");
        long time = object.optLong("timemillis");
        if (TextUtils.isEmpty(sIp) || TextUtils.isEmpty(cIp)) {
            throw new IllegalArgumentException();
        }
        PingResult result = new PingResult();
        result.setClientIp(cIp);
        result.setServerIp(sIp);
        result.setServerTimemillis(time);
        return result;
    }
}
