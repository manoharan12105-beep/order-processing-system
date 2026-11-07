
package us.order_processing_system.service;

import us.order_processing_system.dto.request.ProductRequest;
import us.order_processing_system.dto.response.ProductResponse;
import us.order_processing_system.entity.Product;
import us.order_processing_system.exception.DuplicateResourceException;
import us.order_processing_system.exception.ResourceNotFoundException;
import us.order_processing_system.repo.ProductRepository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  
  @Override
  public ProductResponse createProduct(ProductRequest request) throws DuplicateResourceException {

    if(productRepository.existsBySku(request.sku())) {
      throw new DuplicateResourceException(
        "Product already exists with SKU: " + request.sku()
      );
    }

    Product product = new Product();

    product.setName(request.name());
    product.setSku(request.sku());
    product.setDescription(request.description());
    product.setPrice(request.price());

    Product savedProduct = productRepository.save(product);

    return mapToResponse(savedProduct);
  }

  
  @Override
  @Transactional(readOnly = true)
  public ProductResponse getProductById(Long id) {

    Product product = productRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(
        "Product not found with ID: " + id
      ));

    return mapToResponse(product);
  }

  
  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> getAllProducts(Pageable pageable) {

    return productRepository.findAll(pageable)
      .map(this::mapToResponse);
  }

  
  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> getProductsByName(
      String name,
      Pageable pageable) {

    return productRepository
      .findByNameContainingIgnoreCase(name, pageable)
      .map(this::mapToResponse);
  }

  
  @Override
  public ProductResponse updateProduct(Long id, ProductRequest request) throws DuplicateResourceException {

    Product product = productRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(
        "Product not found with ID: " + id
      ));

    if (!product.getSku().equals(request.sku())
        && productRepository.existsBySku(request.sku())) {

      throw new DuplicateResourceException(
        "Product already exists with SKU: " + request.sku()
      );
    }

    product.setName(request.name());
    product.setSku(request.sku());
    product.setDescription(request.description());
    product.setPrice(request.price());

    return mapToResponse(product);
  }

  
  @Override
  public void deleteProduct(Long id) {

    Product product = productRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(
        "Product not found with ID: " + id
      ));

    productRepository.delete(product);
  }

  
  private ProductResponse mapToResponse(Product product) {

    return new ProductResponse(
      product.getId(),
      product.getName(),
      product.getSku(),
      product.getDescription(),
      product.getPrice(),
      product.getStatus(),
      product.getCreatedAt(),
      product.getUpdatedAt()
    );
  }
}
