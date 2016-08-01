package cn.saymagic.bluefinsdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by saymagic on 16/7/26.
 */
public class AppUtil {

    public static int getApplicationVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pi == null) {
            return 0;
        } else {
            return pi.versionCode;
        }
    }

    public static String getApplicationPackageName(Context context) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo pi = null;
        try {
            pi = pm.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
        if (pi == null) {
            return "";
        } else {
            return pi.packageName;
        }
    }

}
