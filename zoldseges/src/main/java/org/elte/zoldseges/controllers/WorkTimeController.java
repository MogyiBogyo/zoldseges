package org.elte.zoldseges.controllers;

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

    @GetMapping("")
    public ResponseEntity<Iterable<WorkTime>> getAll() {
        return ResponseEntity.ok(workTimeRepository.findAll());
    }
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
     * @param worktime
     *
     * @return
     */
    @PostMapping("")
    public ResponseEntity<WorkTime> post(@RequestBody WorkTime worktime) {
        WorkTime savedWorkTime = workTimeRepository.save(worktime);
        return ResponseEntity.ok(savedWorkTime);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<WorkTime> put(@RequestBody WorkTime worktime, @PathVariable Integer id) {
        Optional<WorkTime> oWorkTime = workTimeRepository.findById(id);
        if (oWorkTime.isPresent()) {
            worktime.setId(id);
            return ResponseEntity.ok(courseRepository.save(course));
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<WorkTime> optionalWorkTime= workTimeRepository.findById(id);
        if (optionalWorkTime.isPresent()) {
            workTimeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
