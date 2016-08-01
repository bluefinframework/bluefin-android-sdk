package cn.saymagic.bluefinsdk.job;

import android.content.Context;
import android.os.Handler;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import cn.saymagic.bluefinsdk.callback.BluefinCallbackAdapter;
import cn.saymagic.bluefinsdk.callback.BluefinJobWatcher;

/**
 * Created by saymagic on 16/6/21.
 */
public class JobService extends Thread {

    protected String mServerUrl;

    protected Handler mHandler;

    protected String mPackageName;

    protected String mIdentity;

    protected Context mContext;

    private LinkedBlockingQueue<Job> mQueue;

    private volatile boolean mStop;

    private volatile Job mCurrentJob;

    public JobService(String serverUrl, Handler handler, String packageName, String identity, Context context) {
        this.mServerUrl = serverUrl;
        this.mHandler = handler;
        this.mPackageName = packageName;
        this.mIdentity = identity;
        this.mContext = context;
        this.mQueue = new LinkedBlockingQueue<Job>();
    }

    @Override
    public void run() {
        while (!isStop()) {
            Job job = mQueue.poll();
            if (job == null) {
                continue;
            }
            mCurrentJob = job;
            Object o = null;
            try {
                o = job.perform();
            } catch (Exception e) {
                job.onFail(e);
            }
            if (o != null) {
                if (!isStop()) {
                    job.onDone(o);
                } else {
                    job.onCancel(o);
                }
            }
        }
    }

    public Job getCurrentJob() {
        return mCurrentJob;
    }

    public boolean isStop() {
        return mStop;
    }

    public void setStop(boolean mStop) {
        this.mStop = mStop;
    }

    public String enqueue(Job job) {
        String id = generateJobId();
        job.mount(mServerUrl, mHandler, mPackageName, mIdentity, id, mContext);
        mQueue.add(job);
        return id;
    }

    public void enqueueWithWatcher(Job job, BluefinJobWatcher watcher, BluefinCallbackAdapter adapter) {
        String id = generateJobId();
        job.mount(mServerUrl, mHandler, mPackageName, mIdentity, id, mContext);
        adapter.addWatcher(id, watcher);
        mQueue.offer(job);
    }

    private String generateJobId() {
        return String.valueOf(UUID.randomUUID());
    }

}
