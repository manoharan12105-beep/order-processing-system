package us.order_processing_system.exception;

public class DuplicateResourceException extends Exception{
   public DuplicateResourceException(String message) {
    super(message);
  }
}
