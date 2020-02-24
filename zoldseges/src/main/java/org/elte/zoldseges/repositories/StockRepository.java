package org.elte.zoldseges.repositories;


import org.elte.zoldseges.entities.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer>{
}
