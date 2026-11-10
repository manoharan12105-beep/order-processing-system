package us.order_processing_system.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryRequest(
  @NotNull(message = "Product ID is required")
  @Min(value = 1, message = "Product ID must be positive")
  Long productId,

  @NotNull(message = "Quantity is required")
  @Min(value = 0, message = "Quantity cannot be negative")
  Integer quantity,

  @NotNull(message = "Reorder level is required")
  @Min(value = 0, message = "Reorder level cannot be negative")
  Integer reorderLevel

) {}