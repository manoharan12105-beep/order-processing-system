package us.order_processing_system.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.order_processing_system.dto.request.InventoryRequest;
import us.order_processing_system.dto.response.InventoryResponse;
import us.order_processing_system.exception.InvalidRequestBodyException;
import us.order_processing_system.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

  private final InventoryService inventoryService;

  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @PostMapping
  public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody InventoryRequest request) {
    InventoryResponse response = inventoryService.createInventory(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Long id) {
    InventoryResponse response = inventoryService.getInventoryById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/product/{productId}")
  public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable Long productId) {
    InventoryResponse response = inventoryService.getInventoryByProductId(productId);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<Page<InventoryResponse>> getAllInventory(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
    Page<InventoryResponse> inventory = inventoryService.getAllInventory(pageable);
    return ResponseEntity.ok(inventory);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InventoryResponse> updateInventory(@PathVariable Long id, @Valid @RequestBody InventoryRequest request) throws InvalidRequestBodyException {
    InventoryResponse response = inventoryService.updateInventory(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
    inventoryService.deleteInventory(id);
    return ResponseEntity.noContent().build();
  }
}
