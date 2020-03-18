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

@CrossOrigin
@RestController
@RequestMapping("/worktimes")
public class WorkTimeController {
    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Autowired
    private UserRepository userRepository;

    private WorkTime mapFromDtoToEntity(WorktimeDto worktimeDto) {
        Optional<User> optionalUser = userRepository.findById(worktimeDto.getUserId());
        return new WorkTime(
                worktimeDto.getDate(),
                worktimeDto.getStartHour(),
                worktimeDto.getEndHour(),
                optionalUser.get());
    }

    private WorkTime modifyEntityWithDto(WorktimeDto worktimeDto, WorkTime findedWorkTime) {
        Optional<User> optionalUser = userRepository.findById(worktimeDto.getUserId());
        if (optionalUser.isPresent()) {
            findedWorkTime.setDate(worktimeDto.getDate());
            findedWorkTime.setStartHour(worktimeDto.getStartHour());
            findedWorkTime.setEndHour(worktimeDto.getEndHour());
            findedWorkTime.setUser(optionalUser.get());
        } else {
            findedWorkTime.setDate(worktimeDto.getDate());
            findedWorkTime.setStartHour(worktimeDto.getStartHour());
            findedWorkTime.setEndHour(worktimeDto.getEndHour());

        }


        return findedWorkTime;
    }

    /**
     * @return all worktime
     */
    @GetMapping("")
    public ResponseEntity<Iterable<WorkTime>> getAll() {
        return ResponseEntity.ok(workTimeRepository.findAll());
    }

    /**
     * @param id
     * @return retun 1 worktime with this id, if the id exists
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
     * @param worktimeDto
     * @return adds a new worktime
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
     * @param worktimeDto
     * @param id
     * @return modify a worktime if the id exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkTime> put(@RequestBody WorktimeDto worktimeDto, @PathVariable Integer id) {
        Optional<WorkTime> oWorkTime = workTimeRepository.findById(id);
        if (oWorkTime.isPresent() && worktimeDto.getUserId().equals(id)) {
            return ResponseEntity.ok(workTimeRepository.save(modifyEntityWithDto(worktimeDto, oWorkTime.get())));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @param id
     * @return deletes a worktime with this id
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

    /**
     * @param id
     * @return return the user of the worktime, if it exists
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
}
