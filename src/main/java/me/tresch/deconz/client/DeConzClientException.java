package me.tresch.deconz.client;

public class DeConzClientException extends RuntimeException {
  public DeConzClientException(int status, String response) {
    super(String.format("An HTTP error (%d) occurred while communicating with DeConz. Message: %s", status, response));
  }

  public DeConzClientException(Throwable cause) {
    super(cause);
  }
}
