package org.elte.zoldseges.repositories;


import org.elte.zoldseges.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByProductId(Integer productId);

    List<Stock> findByQuantity(Integer quantity);

}
