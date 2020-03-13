package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.UserDto;
import org.elte.zoldseges.entities.User;
import org.elte.zoldseges.entities.WorkTime;
import org.elte.zoldseges.repositories.UserRepository;
import org.elte.zoldseges.repositories.WorkTimeRepository;
import org.elte.zoldseges.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

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
    public ResponseEntity<Iterable<User>> getAllEnabled() {
        return ResponseEntity.ok(userRepository.findByEnable(true));
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/disabled")
    public ResponseEntity<Iterable<User>> getAllDisabled(Authentication auth) {
        Optional<User> loggedInUser = userRepository.findByEmail(auth.getName());
        System.out.println(auth.getName());

        if(loggedInUser.isPresent()) {
            if (loggedInUser.get().getRole().equals(User.Role.ROLE_ADMIN)) {
                return ResponseEntity.ok(userRepository.findByEnable(false));
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }else {
            return ResponseEntity.notFound().build();
        }


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


    @GetMapping("/{id}/worktimes")
    public ResponseEntity<Iterable<WorkTime>> worktimes(@PathVariable Integer id) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            return ResponseEntity.ok(oUser.get().getWorkTimeList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("")
    public ResponseEntity<User> post(@RequestBody UserDto userDto) {
        Optional<User> oUser = userRepository.findByUsername(userDto.getUsername());
        if (oUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        User savedUser = userRepository.save(mapFromDtoToEntity(userDto));
        return ResponseEntity.ok(savedUser);
    }

    private User mapFromDtoToEntity(UserDto userDto){
        return new User(
                userDto.getFamilyname(),
                userDto.getGivenname(),
                userDto.getUsername(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getRole(),
                userDto.isEnable(),
                userDto.getWorkTimeList()
        );
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        Optional<User> oUser = userRepository.findByUsername(userDto.getUsername());
        if (oUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRole(User.Role.ROLE_WORKER);
        return ResponseEntity.ok(userRepository.save(mapFromDtoToEntity(userDto)));
    }



    @PostMapping("login")
    public ResponseEntity login() {
        return ResponseEntity.ok(authenticatedUser.getUser());
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> put(@RequestBody User User, @PathVariable Integer id) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            return ResponseEntity.ok(userRepository.save(User));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            optionalUser.get().setEnable(false);
            userRepository.save(optionalUser.get());
            return ResponseEntity.ok(optionalUser.get().isEnable());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
