package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.PlannedOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedOrderRepository extends CrudRepository<PlannedOrder, Integer> {
}
