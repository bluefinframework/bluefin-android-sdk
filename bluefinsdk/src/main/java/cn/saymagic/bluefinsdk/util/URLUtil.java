package cn.saymagic.bluefinsdk.util;

import android.text.TextUtils;

/**
 * Created by saymagic on 16/6/25.
 */
public class URLUtil {

    public static String join(String host, String path) {
        if (TextUtils.isEmpty(host)) {
            throw new IllegalArgumentException("host can't be null");
        }
        if (TextUtils.isEmpty(path)) {
            return host;
        }
        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return host + "/" + path;
    }
}
