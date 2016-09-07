package cn.saymagic.bluefinsdk.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public static HttpURLConnection openConnection(@NonNull URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        return connection;
    }
}
