package us.order_processing_system.exception;

import us.order_processing_system.dto.response.ApiError;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

    return buildResponse(
      HttpStatus.NOT_FOUND,
      ex.getMessage(),
      request.getRequestURI()
    );
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ApiError> handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {

    return buildResponse(
      HttpStatus.CONFLICT,
      ex.getMessage(),
      request.getRequestURI()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {

    String message = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .findFirst()
      .orElse("Invalid request fields");

    return buildResponse(
      HttpStatus.BAD_REQUEST,
      message,
      request.getRequestURI()
    );
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleUnreadableBody(HttpMessageNotReadableException ex, HttpServletRequest request) {

    return buildResponse(
      HttpStatus.BAD_REQUEST,
      "Request body is missing or contains invalid JSON",
      request.getRequestURI()
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnexpectedException(Exception ex, HttpServletRequest request) {

    return buildResponse(
      HttpStatus.INTERNAL_SERVER_ERROR,
      "An unexpected error occurred",
      request.getRequestURI()
    );
  }

  private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message, String path) {

    ApiError apiError = new ApiError(
      Instant.now(),
      status.value(),
      status.getReasonPhrase(),
      message,
      path
    );

    return ResponseEntity.status(status).body(apiError);
  }
}
