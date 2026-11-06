package us.order_processing_system.dto.response;

import java.time.Instant;

public record ApiError(
  Instant timestamp,
  int status,
  String error,
  String message,
  String path
) {}
