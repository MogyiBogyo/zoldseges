package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {

    Optional<Product> findById(Integer integer);

}
