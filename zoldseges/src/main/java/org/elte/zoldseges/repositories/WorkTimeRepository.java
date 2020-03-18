package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Integer> {
    List<WorkTime> findByDate(Date date);

    List<WorkTime> findByStartHour(String startHour);

    List<WorkTime> findByEndHour(String endHour);
}
