package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.WorkTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTimeRepository extends CrudRepository<WorkTime, Integer> {
    
}
