package us.order_processing_system.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import us.order_processing_system.entity.Product;
import us.order_processing_system.enums.ProductStatus;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findBySku(String sku);

  boolean existsBySku(String sku);

  Page<Product> findByStatus(ProductStatus status, Pageable pageable);

  Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}