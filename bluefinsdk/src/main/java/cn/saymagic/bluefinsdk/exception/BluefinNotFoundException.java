package cn.saymagic.bluefinsdk.exception;

/**
 * Created by saymagic on 16/6/25.
 */
public class BluefinNotFoundException extends BluefinException {

    public BluefinNotFoundException() {
    }

    public BluefinNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public BluefinNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BluefinNotFoundException(Throwable throwable) {
        super(throwable);
    }


}
