package us.order_processing_system.service;

import us.order_processing_system.dto.request.ProductRequest;
import us.order_processing_system.dto.response.ProductResponse;
import us.order_processing_system.exception.DuplicateResourceException;

import java.util.List;

public interface ProductService {

  ProductResponse createProduct(ProductRequest request) throws DuplicateResourceException;

  ProductResponse getProductById(Long id);

  List<ProductResponse> getAllProducts();

  ProductResponse updateProduct(Long id, ProductRequest request) throws DuplicateResourceException;

  void deleteProduct(Long id);

  List<ProductResponse> getProductsByName(String name);
}