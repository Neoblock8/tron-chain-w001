package org.tron.core.exception;

public class WyzthRuntimeException extends RuntimeException {

  public WyzthRuntimeException() {
    super();
  }

  public WyzthRuntimeException(String message) {
    super(message);
  }

  public WyzthRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public WyzthRuntimeException(Throwable cause) {
    super(cause);
  }

  protected WyzthRuntimeException(String message, Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }


}
