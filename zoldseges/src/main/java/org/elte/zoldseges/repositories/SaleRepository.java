package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.Income;
import org.elte.zoldseges.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Income> findByQuantity(Integer quantity);

    List<Income> findByBuyer(String buyer);

    List<Income> findByProductId(Integer productId);

    List<Income> findByDate(Date date);

    List<Income> findByPrice(Integer price);
}
