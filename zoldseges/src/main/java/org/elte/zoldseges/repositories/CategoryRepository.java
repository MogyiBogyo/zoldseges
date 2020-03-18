package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
    List<Category> findBySalePrice(Integer price);

    Category findByIsSale(boolean isSale);

}
