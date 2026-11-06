package us.order_processing_system.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(

  @NotBlank(message = "Product name is required")
  @Size(max = 150, message = "Name cannot exceed 150 characters")
  String name,

  @NotBlank(message = "SKU is required")
  @Size(max = 50, message = "SKU cannot exceed 50 characters")
  String sku,

  @Size(max = 1024, message = "Description cannot exceed 1024 characters")
  String description,

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.01", message = "Price must be greater than zero")
  @Digits(integer = 10, fraction = 2)
  BigDecimal price

) {}