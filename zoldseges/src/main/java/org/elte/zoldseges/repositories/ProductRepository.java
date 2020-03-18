package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findById(Integer integer);

    Product findByName(String name);

    List<Product> findByPrice(Integer price);

    List<Product> findBySalePrice(Integer salePrice);

    List<Product> findByIsSale(boolean isSale);

    Optional<Product> findByCategoryId(Integer categoryId);
}
