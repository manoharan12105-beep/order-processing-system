package us.order_processing_system.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import us.order_processing_system.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  Optional<Inventory> findByProduct_Id(Long productId);

  boolean existsByProduct_Id(Long productId);

  Page<Inventory> findByQuantityLessThanEqual(Integer quantity, Pageable pageable);
}