package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.UserDto;
import org.elte.zoldseges.entities.User;
import org.elte.zoldseges.entities.WorkTime;
import org.elte.zoldseges.repositories.UserRepository;
import org.elte.zoldseges.repositories.WorkTimeRepository;
import org.elte.zoldseges.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private User converToDto(UserDto userDto){
       return new User(userDto.getId(),
                userDto.getFamilyname(),
                userDto.getGivenname(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole(),
                userDto.getWorkTimeList()
                );

    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private WorkTimeRepository workTimeRepository;


    /**
     * @return return all user
     */
    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * @param id
     * @return return user with this id, if it exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        Optional<User> User = userRepository.findById(id);
        if (User.isPresent()) {
            return ResponseEntity.ok(User.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<User> post(@RequestBody User User) {
        User savedUser = userRepository.save(User);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
        Optional<User> oUser = userRepository.findByUsername(user.getUsername());
        if (oUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setEnabled(true);
        user.setRole(User.Role.ROLE_WORKER);
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("login")
    public ResponseEntity login() {
        return ResponseEntity.ok(authenticatedUser.getUser());
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> put(@RequestBody User User, @PathVariable Integer id) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            //User.setId(id);
            return ResponseEntity.ok(userRepository.save(User));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/worktimes")
    public ResponseEntity<Iterable<WorkTime>> worktimes(@PathVariable Integer id) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            return ResponseEntity.ok(oUser.get().getWorkTimeList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
