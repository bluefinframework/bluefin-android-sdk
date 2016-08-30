package cn.saymagic.bluefinrxsdk;

import java.io.File;
import java.util.List;

import cn.saymagic.bluefinsdk.Bluefin;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.entity.PingResult;
import cn.saymagic.bluefinsdk.exception.BluefinException;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by saymagic on 16/8/30.
 */
public class BluefinRxAdapter {

    public static Observable<PingResult> ping() {
        return Observable.create(new Observable.OnSubscribe<PingResult>() {
            @Override
            public void call(Subscriber<? super PingResult> subscriber) {
                try {
                    PingResult result = Bluefin.ping();
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (BluefinException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<BluefinApkData> checkNewestVersion(final String packageName) {
        return Observable.create(new Observable.OnSubscribe<BluefinApkData>() {
            @Override
            public void call(Subscriber<? super BluefinApkData> subscriber) {
                try {
                    BluefinApkData result = Bluefin.checkNewestVersion(packageName);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (BluefinException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<String> simpleUpdate(final String packageName) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = Bluefin.simpleUpdate(packageName);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<List<BluefinApkData>> listAllVersion(final String packageName) {
        return Observable.create(new Observable.OnSubscribe<List<BluefinApkData>>() {
            @Override
            public void call(Subscriber<? super List<BluefinApkData>> subscriber) {
                try {
                    List<BluefinApkData> result = Bluefin.listAllVersion(packageName);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (BluefinException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<List<BluefinApkData>> listAllApks() {
        return Observable.create(new Observable.OnSubscribe<List<BluefinApkData>>() {
            @Override
            public void call(Subscriber<? super List<BluefinApkData>> subscriber) {
                try {
                    List<BluefinApkData> result = Bluefin.listAllApks();
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (BluefinException e) {
                    subscriber.onError(e);
                }
            }
        });
    }


    public static Observable<String> retrace(final String s, final String packageName, final String identity) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = Bluefin.retrace(s, packageName, identity);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (BluefinException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<String> retrace(final File file, final String packageName, final String identity) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = Bluefin.retrace(file, packageName, identity);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (BluefinException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
