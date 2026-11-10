package us.order_processing_system.service;

import us.order_processing_system.dto.request.InventoryRequest;
import us.order_processing_system.dto.response.InventoryResponse;
import us.order_processing_system.exception.InvalidRequestBodyException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

  InventoryResponse createInventory(InventoryRequest request);

  InventoryResponse getInventoryById(Long id);

  InventoryResponse getInventoryByProductId(Long productId);

  Page<InventoryResponse> getAllInventory(Pageable pageable);

  InventoryResponse updateInventory(Long id, InventoryRequest request) throws InvalidRequestBodyException;

  void deleteInventory(Long id);
}