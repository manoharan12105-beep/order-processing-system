
package us.order_processing_system.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.order_processing_system.dto.request.InventoryRequest;
import us.order_processing_system.dto.response.InventoryResponse;
import us.order_processing_system.entity.Inventory;
import us.order_processing_system.entity.Product;
import us.order_processing_system.exception.DuplicateResourceException;
import us.order_processing_system.exception.InvalidRequestBodyException;
import us.order_processing_system.exception.ResourceNotFoundException;
import us.order_processing_system.repo.InventoryRepository;
import us.order_processing_system.repo.ProductRepository;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;
  private final ProductRepository productRepository;

  public InventoryServiceImpl(
      InventoryRepository inventoryRepository,
      ProductRepository productRepository) {
    this.inventoryRepository = inventoryRepository;
    this.productRepository = productRepository;
  }

  @Override
  public InventoryResponse createInventory(InventoryRequest request) {
    Product product = productRepository.findById(request.productId())
      .orElseThrow(() -> new ResourceNotFoundException(
        "Product not found with ID: " + request.productId()));

    if(inventoryRepository.existsByProduct_Id(request.productId())) {
      throw new DuplicateResourceException(
        "Inventory already exists for product ID: " + request.productId());
    }

    Inventory inventory = new Inventory();
    inventory.setProduct(product);
    inventory.setQuantity(request.quantity());
    inventory.setReservedQuantity(0);
    inventory.setReorderLevel(request.reorderLevel());

    Inventory savedInventory = inventoryRepository.save(inventory);

    return mapToResponse(savedInventory);
  }

  @Override
  @Transactional(readOnly = true)
  public InventoryResponse getInventoryById(Long id) {
    Inventory inventory = inventoryRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(
        "Inventory not found with ID: " + id));

    return mapToResponse(inventory);
  }

  @Override
  @Transactional(readOnly = true)
  public InventoryResponse getInventoryByProductId(Long productId) {
    Inventory inventory = inventoryRepository.findByProduct_Id(productId)
      .orElseThrow(() -> new ResourceNotFoundException(
        "Inventory not found for product ID: " + productId));

    return mapToResponse(inventory);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<InventoryResponse> getAllInventory(Pageable pageable) {
    return inventoryRepository.findAll(pageable)
      .map(this::mapToResponse);
  }

  @Override
  public InventoryResponse updateInventory(Long id, InventoryRequest request) throws InvalidRequestBodyException {
    Inventory inventory = inventoryRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(
        "Inventory not found with ID: " + id));

    if(!Objects.equals(inventory.getProduct().getId(), request.productId())) {
      throw new InvalidRequestBodyException(
        "An inventory record cannot be reassigned to another product");
    }

    if(request.quantity() < inventory.getReservedQuantity()) {
      throw new InvalidRequestBodyException(
        "Quantity cannot be less than reserved quantity");
    }

    inventory.setQuantity(request.quantity());
    inventory.setReorderLevel(request.reorderLevel());

    return mapToResponse(inventory);
  }

  @Override
  public void deleteInventory(Long id) {
    Inventory inventory = inventoryRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(
        "Inventory not found with ID: " + id));

    inventoryRepository.delete(inventory);
  }

  private InventoryResponse mapToResponse(Inventory inventory) {
    Product product = inventory.getProduct();

    Integer availableQuantity = inventory.getAvailableQuantity();

    return new InventoryResponse(
      inventory.getId(),
      product.getId(),
      product.getName(),
      product.getSku(),
      inventory.getQuantity(),
      inventory.getReservedQuantity(),
      availableQuantity,
      inventory.getReorderLevel(),
      availableQuantity <= inventory.getReorderLevel(),
      inventory.getCreatedAt(),
      inventory.getUpdatedAt()
    );
  }
}
