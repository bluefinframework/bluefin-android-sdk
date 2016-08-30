package cn.saymagic.bluefinsdk.exception;

/**
 * Created by saymagic on 16/8/30.
 */
public class BluefinUnknowException extends BluefinException {


    public BluefinUnknowException() {
    }

    public BluefinUnknowException(String detailMessage) {
        super(detailMessage);
    }

    public BluefinUnknowException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BluefinUnknowException(Throwable throwable) {
        super(throwable);
    }

}
