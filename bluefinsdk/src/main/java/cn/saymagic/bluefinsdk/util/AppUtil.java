package cn.saymagic.bluefinsdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;

/**
 * Created by saymagic on 16/7/26.
 */
public class AppUtil {

    public static int getApplicationVersionCode(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pi == null) {
            return 0;
        } else {
            return pi.versionCode;
        }
    }

    public static String getApplicationName(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo pi = null;
        try {
            pi = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
        if (pi == null) {
            return "";
        } else {
            return String.valueOf(pm.getApplicationLabel(pi));
        }
    }

    public static Icon getApplicationIcon(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo pi = null;
        try {
            pi = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        if (pi == null) {
            return null;
        } else {
            Icon icon = Icon.createWithResource(context, pi.icon);
            return null;
        }
    }
}
