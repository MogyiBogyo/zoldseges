package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.WorktimeDto;
import org.elte.zoldseges.entities.User;
import org.elte.zoldseges.entities.WorkTime;
import org.elte.zoldseges.repositories.UserRepository;
import org.elte.zoldseges.repositories.WorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * Allocates the "/worktimes" endpoint to control worktimes
 */
@CrossOrigin
@RestController
@RequestMapping("/worktimes")
public class WorkTimeController {
    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * Creates a new WorkTime Entity from DTO
     * @param worktimeDto data transfer object
     * @return a new WorkTime
     */
    private WorkTime mapFromDtoToEntity(WorktimeDto worktimeDto) {
        return new WorkTime(
                worktimeDto.getDate(),
                worktimeDto.getStartHour(),
                worktimeDto.getEndHour(),
                userRepository.findById(worktimeDto.getUserId()).get());
    }

    /**
     * Modify a WorkTime with DTO's data
     * @param worktimeDto data transfer object
     * @param foundedWorktime WorkTime for modify
     * @return an updated WorkTime
     */
    private WorkTime modifyEntityWithDto(WorktimeDto worktimeDto, WorkTime foundedWorktime) {
        foundedWorktime.setDate(worktimeDto.getDate());
        foundedWorktime.setStartHour(worktimeDto.getStartHour());
        foundedWorktime.setEndHour(worktimeDto.getEndHour());
        foundedWorktime.setUser(userRepository.findById(worktimeDto.getUserId()).get());

        return foundedWorktime;
    }

    /**
     * Returns all the WorkTimes
     * @return ResponseEntity of WorkTimes
     */
    @GetMapping("")
    public ResponseEntity<Iterable<WorkTime>> getAll() {
        return ResponseEntity.ok(workTimeRepository.findAll());
    }

    /**
     * Returns a WorkTime by ID
     * @param id Id of WorkTime
     * @return ResponseEntity of a WorkTime
     * Returns Not Found if WorkTime doesn't exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkTime> get(@PathVariable Integer id) {
        Optional<WorkTime> label = workTimeRepository.findById(id);
        if (label.isPresent()) {
            return ResponseEntity.ok(label.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Returns a User of a Worktime by Worktime ID
     * @param id Id of WorkTime
     * @return ResponseEntity of a User
     * Returns Not Found if WorkTime doesn't exists
     */
    @GetMapping("/{id}/user")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        Optional<WorkTime> workTime = workTimeRepository.findById(id);
        if (workTime.isPresent()) {
            return ResponseEntity.ok(workTime.get().getUser());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new WorkTime
     * @param worktimeDto The WorkTime data transfer Object to add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created WorkTime
     */
    @PostMapping("")
    public ResponseEntity<WorkTime> post(@RequestBody WorktimeDto worktimeDto) {
        Optional<User> optionalUser = userRepository.findById(worktimeDto.getUserId());
        if (optionalUser.isPresent()) {
            WorkTime savedWorkTime = workTimeRepository.save(mapFromDtoToEntity(worktimeDto));
            return ResponseEntity.ok(savedWorkTime);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Updates a WorkTime by ID
     * @param id Id of WorkTime which to modify
     * @param worktimeDto The WorkTime data transfer Object to add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated WorkTime
     * Returns Not Found if WorkTime or User doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkTime> put(@RequestBody WorktimeDto worktimeDto, @PathVariable Integer id) {
        Optional<WorkTime> oWorkTime = workTimeRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(worktimeDto.getUserId());
        if (oWorkTime.isPresent() && optionalUser.isPresent()) {
            return ResponseEntity.ok(workTimeRepository.save(modifyEntityWithDto(worktimeDto, oWorkTime.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a WorkTime by WorkTime ID
     * @param id ID of WorkTime
     * @return ResponseEntity
     * Returns Not Found if WorkTime doesn't exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<WorkTime> optionalWorkTime = workTimeRepository.findById(id);
        if (optionalWorkTime.isPresent()) {
            workTimeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
