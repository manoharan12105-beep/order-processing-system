package us.order_processing_system.service;

import us.order_processing_system.dto.request.ProductRequest;
import us.order_processing_system.dto.response.ProductResponse;
import us.order_processing_system.exception.DuplicateResourceException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  ProductResponse createProduct(ProductRequest request) throws DuplicateResourceException;

  ProductResponse getProductById(Long id);

  Page<ProductResponse> getAllProducts(Pageable pageable);

  Page<ProductResponse> getProductsByName(String name, Pageable pageable);

  ProductResponse updateProduct(Long id, ProductRequest request) throws DuplicateResourceException;

  void deleteProduct(Long id);
}