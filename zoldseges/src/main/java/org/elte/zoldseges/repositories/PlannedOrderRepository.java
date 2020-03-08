package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.PlannedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannedOrderRepository extends JpaRepository<PlannedOrder, Integer> {
    List<PlannedOrder> findByQuantity(Integer quantity);
    List<PlannedOrder> findByProductId(Integer productId);
}
