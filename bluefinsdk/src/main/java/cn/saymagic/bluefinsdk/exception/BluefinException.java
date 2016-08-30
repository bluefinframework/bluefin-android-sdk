package cn.saymagic.bluefinsdk.exception;

/**
 * Created by saymagic on 16/8/30.
 */
public class BluefinException extends Exception {

    public BluefinException() {
    }

    public BluefinException(String detailMessage) {
        super(detailMessage);
    }

    public BluefinException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BluefinException(Throwable throwable) {
        super(throwable);
    }

}
