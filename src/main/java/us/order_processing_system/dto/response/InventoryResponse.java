package us.order_processing_system.dto.response;

import java.time.LocalDateTime;

public record InventoryResponse(
  Long id,
  Long productId,
  String productName,
  String sku,
  Integer quantity,
  Integer reservedQuantity,
  Integer availableQuantity,
  Integer reorderLevel,
  boolean lowStock,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
