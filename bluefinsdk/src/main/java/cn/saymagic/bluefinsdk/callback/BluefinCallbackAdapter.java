package cn.saymagic.bluefinsdk.callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.saymagic.bluefinsdk.entity.BluefinApkData;

/**
 * Created by saymagic on 16/7/5.
 */
public class BluefinCallbackAdapter implements BluefinCallback{

    private BluefinCallback mCallBack;

    public void setCustomCallBack(BluefinCallback customCallBack) {
        this.mCallBack = customCallBack;
    }

    private Map<String, List<BluefinJobWatcher>> mWatchers = new HashMap<String, List<BluefinJobWatcher>>();

    @Override
    public void onCheckUpdateResult(String id, BluefinApkData data, Exception e) {
        List<BluefinJobWatcher> watchers = mWatchers.get(id);
        if (watchers == null) {
            if (mCallBack != null) {
                mCallBack.onCheckUpdateResult(id, data, e);
            }
            return;
        }
        for (BluefinJobWatcher watcher : watchers) {
            watcher.onResult(data, e);
        }
        mWatchers.remove(id);
    }

    @Override
    public void onListAllVersionResult(String id, List<BluefinApkData> datas, Exception e) {
        List<BluefinJobWatcher> watchers = mWatchers.get(id);
        if (watchers == null) {
            if (mCallBack != null) {
                mCallBack.onListAllVersionResult(id, datas, e);
            }
            return;
        }
        for (BluefinJobWatcher watcher : watchers) {
            watcher.onResult(datas, e);
        }
        mWatchers.remove(id);
    }

    @Override
    public void onRetraceResult(String id, String result, Exception e) {
        List<BluefinJobWatcher> watchers = mWatchers.get(id);
        if (watchers == null) {
            if (mCallBack != null) {
                mCallBack.onRetraceResult(id, result, e);
            }
            return;
        }
        for (BluefinJobWatcher watcher : watchers) {
            watcher.onResult(result, e);
        }
        mWatchers.remove(id);
    }

    @Override
    public void onSimpleUpdateJobResult(String id, long result, Exception e) {
        List<BluefinJobWatcher> watchers = mWatchers.get(id);
        if (watchers == null) {
            if (mCallBack != null) {
                mCallBack.onSimpleUpdateJobResult(id, result, e);
            }
            return;
        }
        for (BluefinJobWatcher watcher : watchers) {
            watcher.onResult(result, e);
        }
        mWatchers.remove(id);
    }

    @Override
    public void onListAllApkResult(String id, List<BluefinApkData> datas, Exception e) {
        List<BluefinJobWatcher> watchers = mWatchers.get(id);
        if (watchers == null) {
            if (mCallBack != null) {
                mCallBack.onListAllApkResult(id, datas, e);
            }
            return;
        }
        for (BluefinJobWatcher watcher : watchers) {
            watcher.onResult(datas, e);
        }
        mWatchers.remove(id);
    }

    public void addWatcher(String id, BluefinJobWatcher watcher) {
        if (mWatchers.containsKey(id)) {
            List<BluefinJobWatcher> watchers = mWatchers.get(id);
            watchers.add(watcher);
            return;
        }
        List<BluefinJobWatcher> watchers = new ArrayList<BluefinJobWatcher>();
        watchers.add(watcher);
        mWatchers.put(id, watchers);
    }


}
