package us.order_processing_system.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import us.order_processing_system.entity.Product;
import us.order_processing_system.enums.ProductStatus;

@Repository 
public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findBySku(String sku);

  boolean existsBySku(String sku);

  List<Product> findByStatus(ProductStatus status);

  List<Product> findByNameContainingIgnoreCase(String name);
}
