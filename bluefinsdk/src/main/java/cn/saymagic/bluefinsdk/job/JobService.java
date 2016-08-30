package cn.saymagic.bluefinsdk.job;

import android.content.Context;
import android.os.Handler;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

import cn.saymagic.bluefinsdk.callback.BluefinCallbackAdapter;
import cn.saymagic.bluefinsdk.callback.BluefinJobWatcher;
import cn.saymagic.bluefinsdk.exception.BluefinException;

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

    private Executor mExecutor;

    private volatile boolean mStop;

    private volatile Job mCurrentJob;

    public JobService(String serverUrl, Handler handler, Context context) {
        this.mServerUrl = serverUrl;
        this.mHandler = handler;
        this.mContext = context;
        this.mQueue = new LinkedBlockingQueue<Job>();
    }

    public JobService(String serverUrl, Handler handler, Context context, Executor executor) {
        this.mServerUrl = serverUrl;
        this.mHandler = handler;
        this.mContext = context;
        this.mExecutor = executor;
        this.mQueue = new LinkedBlockingQueue<Job>();
    }

    @Override
    public void run() {
        while (!isStop()) {
            Job job = mQueue.poll();
            if (job == null) {
                continue;
            }
            mExecutor.execute(new JobRunable(job));
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
        job.mount(mServerUrl, mHandler, id, mContext);
        try {
            mQueue.add(job);
            return id;
        } catch (IllegalStateException e) {
            return "-1";
        }
    }

    public <T> T directlyRun(Job<T> job) throws BluefinException {
        String id = generateJobId();
        job.mount(mServerUrl, mHandler, id, mContext);
        return job.perform();
    }

    public void enqueueWithWatcher(Job job, BluefinJobWatcher watcher, BluefinCallbackAdapter adapter) {
        String id = generateJobId();
        job.mount(mServerUrl, mHandler, id, mContext);
        adapter.addWatcher(id, watcher);
        try {
            mQueue.add(job);
        } catch (IllegalStateException ignore) {
        }
    }

    private String generateJobId() {
        return String.valueOf(UUID.randomUUID());
    }

    private class JobRunable implements Runnable {

        private Job job;

        public JobRunable(Job job) {
            this.job = job;
        }

        @Override
        public void run() {
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
}
