package cn.saymagic.bluefinsdk.exception;

/**
 * Created by saymagic on 16/8/30.
 */
public class BluefinNotConnectException extends BluefinException {

    public BluefinNotConnectException() {
    }

    public BluefinNotConnectException(String detailMessage) {
        super(detailMessage);
    }

    public BluefinNotConnectException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BluefinNotConnectException(Throwable throwable) {
        super(throwable);
    }

}
