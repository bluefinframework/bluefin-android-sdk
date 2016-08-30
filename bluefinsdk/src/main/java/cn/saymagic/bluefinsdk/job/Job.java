package cn.saymagic.bluefinsdk.job;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import cn.saymagic.bluefinsdk.exception.BluefinException;

/**
 * Created by saymagic on 16/6/21.
 */
public abstract class Job<T> {

    protected String mServerUrl;

    protected Handler mHandler;

    protected Context mContext;

    protected String mJobId;

    protected abstract T perform() throws BluefinException;

    public abstract String getName();

    public abstract void onDone(T t);

    public abstract void onFail(Exception e);

    public abstract void onCancel(T t);

    public Job() {
    }

    ;

    public Job(String serverUrl, Handler handler, String jobId, Context context) {
        this.mount(serverUrl, handler, jobId, context);
    }

    /**
     * mount some useful info for this job.
     * JobService will do this.
     *
     */
    public void mount(String serverUrl, Handler handler,String jobId, Context context) {
        this.mServerUrl = serverUrl;
        this.mHandler = handler;
        this.mJobId = jobId;
        this.mContext = context;
    }

    public void sendMessageData(int what, Object obj) {
        Message message = Message.obtain();
        message.setTarget(mHandler);
        Bundle bundle = new Bundle();
        bundle.putString("id", mJobId);
        message.setData(bundle);
        message.what = what;
        message.obj = obj;
        message.sendToTarget();
    }
}
