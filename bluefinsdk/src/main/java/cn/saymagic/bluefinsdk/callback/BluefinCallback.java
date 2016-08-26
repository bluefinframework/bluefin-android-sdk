package cn.saymagic.bluefinsdk.callback;

import java.util.List;

import cn.saymagic.bluefinsdk.entity.BluefinApkData;

/**
 * Created by saymagic on 16/6/21.
 * interface for calling client when asynchronous job done.
 * happened in main thread
 */
public interface BluefinCallback {

    /**
     * the callback for checkupdate,
     * @param id the id of the request job
     * @param data the result of callback if CHECK_UPDATE_DONE or CHECK_UPDATE_CANCLE. otherwise null.
     * @param e  exception if exists, if CHECK_UPDATE_FAILED, the e will not be null.
     */
    void onCheckUpdateResult(String id, BluefinApkData data, Exception e);


    /**
     * the callback for list all version datas,
     * @param id the id of the request job
     * @param datas the result of callback if LIST_ALL_VERSION_DONE or LIST_ALL_VERSION_CANCLE. otherwise null.
     * @param e  exception if exists, if LIST_ALL_VERSION_FAILED, the e will not be null.
     */
    void onListAllVersionResult(String id, List<BluefinApkData> datas, Exception e);

    /**
     * the callback for retrace,
     * @param id the id of the request job
     * @param result the result of callback if RETRACE_DONE or RETRACE_CANCLE. otherwise null.
     * @param e  exception if exists, if RETRACE_FAILED, the e will not be null.
     */
    void onRetraceResult(String id, String result, Exception e);

    /**
     * the callback for simple update job,
     * @param id the id of the request job
     * @param result the result of callback if SIMPLE_UPDATE_JOB_DONE or SIMPLE_UPDATE_JOB_CANCLE. otherwise null.
     * @param e  exception if exists, if SIMPLE_UPDATE_JOB_FAILED, the e will not be null.
     */
    void onSimpleUpdateJobResult(String id, long result, Exception e);

    /**
     * the callback for list all apk datas,
     * @param id the id of the request job
     * @param datas the result of callback if LIST_ALL_APKS_DONE or LIST_ALL_APKS_CANCLE. otherwise null.
     * @param e  exception if exists, if LIST_ALL_APKS_FAILED, the e will not be null.
     */
    void onListAllApkResult(String id, List<BluefinApkData> datas, Exception e);


}
