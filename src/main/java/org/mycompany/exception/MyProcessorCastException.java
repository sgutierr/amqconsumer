package org.mycompany.exception;


public class MyProcessorCastException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1483063309010541458L;
    private int errorCode;

    public MyProcessorCastException() {
        super();
    }

    public MyProcessorCastException(final String message) {
        super(message);
    }

    public MyProcessorCastException(final String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MyProcessorCastException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyProcessorCastException(final String message, final int errorCode, final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public MyProcessorCastException(final Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
