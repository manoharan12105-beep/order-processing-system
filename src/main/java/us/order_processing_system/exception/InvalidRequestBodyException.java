package us.order_processing_system.exception;

public class InvalidRequestBodyException extends Exception{
  public InvalidRequestBodyException(String message) {
    super(message);
  }
}
