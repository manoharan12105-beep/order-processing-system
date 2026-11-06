package us.order_processing_system.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import us.order_processing_system.enums.ProductStatus;

public record ProductResponse(
  Long id,
  String name,
  String sku,
  String description,
  BigDecimal price,
  ProductStatus status,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}