package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.Income;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Integer> {

}
