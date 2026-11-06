
package us.order_processing_system.controller;

import us.order_processing_system.dto.request.ProductRequest;
import us.order_processing_system.dto.response.ProductResponse;
import us.order_processing_system.exception.DuplicateResourceException;
import us.order_processing_system.service.ProductService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  
  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(
      @Valid @RequestBody ProductRequest request) throws DuplicateResourceException {

    ProductResponse response = productService.createProduct(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }


  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(
      @PathVariable Long id) {

    ProductResponse response = productService.getProductById(id);

    return ResponseEntity.ok(response);
  }


  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {

    List<ProductResponse> products = productService.getAllProducts();

    return ResponseEntity.ok(products);
  }


  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(
      @PathVariable Long id,
      @Valid @RequestBody ProductRequest request) throws DuplicateResourceException {

    ProductResponse response = productService.updateProduct(id, request);

    return ResponseEntity.ok(response);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

    productService.deleteProduct(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductResponse>> getProductsByName(@RequestParam String name) {

    List<ProductResponse> products = productService.getProductsByName(name);

    return ResponseEntity.ok(products);
  }
}
