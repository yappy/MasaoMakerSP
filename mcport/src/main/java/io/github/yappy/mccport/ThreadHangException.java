package io.github.yappy.mccport;

public class ThreadHangException extends Exception {

    public ThreadHangException() {
        super();
    }

    public ThreadHangException(String message) {
        super(message);
    }

    public ThreadHangException(Throwable cause) {
        super(cause);
    }

    public ThreadHangException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThreadHangException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
