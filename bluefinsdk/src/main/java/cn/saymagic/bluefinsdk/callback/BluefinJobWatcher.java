package cn.saymagic.bluefinsdk.callback;

/**
 * Created by saymagic on 16/7/5.
 */
public interface BluefinJobWatcher<T> {

    /**
     * if success , t will have value
     *      Otherwise, e will have value
     * don't do any expensive things in this method, it will be invoked in the UI thread
     * @param t
     * @param e
     */
    void onResult(T t, Exception e);

}
