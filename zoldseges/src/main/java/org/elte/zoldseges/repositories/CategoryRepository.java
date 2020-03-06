package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    //Category findByName(String name);
}
