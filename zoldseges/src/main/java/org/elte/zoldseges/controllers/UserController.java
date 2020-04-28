package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.UserDto;
import org.elte.zoldseges.entities.User;
import org.elte.zoldseges.entities.WorkTime;
import org.elte.zoldseges.repositories.UserRepository;
import org.elte.zoldseges.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Allocates the "/users" endpoint to control users
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUser authenticatedUser;



    /**
     * Creates a new User Entity from DTO
     * @param userDto data transfer object
     * @return a new User
     */
    private User mapFromDtoToEntity(UserDto userDto) {
        return new User(
                userDto.getFamilyname(),
                userDto.getGivenname(),
                userDto.getUsername(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getRole(),
                userDto.getWorkTimeList());
    }


    /**
     * Modify a User with DTO's data
     * @param userDto data transfer object
     * @param findedUser User for modify
     * @return an updated User
     */
    private User modifyEntityWithDto(UserDto userDto, User findedUser) {
        findedUser.setFamilyname(userDto.getFamilyname());
        findedUser.setGivenname(userDto.getGivenname());
        findedUser.setUsername(userDto.getUsername());
        findedUser.setEmail(userDto.getEmail());
        if(userDto.getPassword() != null){
            findedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        findedUser.setRole(userDto.getRole());
        return findedUser;
    }


    /**
     * Returns all enabled Users
     * @return ResponseEntity of Users
     */
    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllEnabled() {
        return ResponseEntity.ok(userRepository.findByEnable(true));
    }


    /**
     * Returns all of Users
     * @return ResponseEntity of Users
     */
    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }


    /**
     * Returns all disabled Users
     * @return ResponseEntity of Users
     */
    @GetMapping("/disabled")
    public ResponseEntity<Iterable<User>> getAllDisabled() {
        return ResponseEntity.ok(userRepository.findByEnable(false));
    }


    /**
     * Returns a User by ID
     * @param id Id of User
     * @return ResponseEntity of a User
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

    /**
     * Returns a WorkTimes of a User by User id
     * @param id id of a User
     * @return ResponseEntity of Worktimes
     */
    @GetMapping("/{id}/worktimes")
    public ResponseEntity<Iterable<WorkTime>> getWorktimes(@PathVariable Integer id) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            return ResponseEntity.ok(oUser.get().getWorkTimeList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Creates a new User
     * @param userDto The User data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created User
     */
    @PostMapping("")
    public ResponseEntity<User> post(@RequestBody UserDto userDto) {
        Optional<User> oUser = userRepository.findByUsername(userDto.getUsername());
        if (oUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        User savedUser = userRepository.save(mapFromDtoToEntity(userDto));
        return ResponseEntity.ok(savedUser);
    }


    /**
     * Creates a new User
     * @param userDto The User data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created User
     */
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


    /**
     * Authenticate a User with username and password
     * @return ResponseEntity of Authenticated User
     */
    @PostMapping("login")
    public ResponseEntity login() {
        return ResponseEntity.ok(authenticatedUser.getUser());
    }


    /**
     * Updates a User by ID
     * @param id Id of User which to modify
     * @param userDto The User data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated User
     * Returns Not Found if User doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> put(@RequestBody UserDto userDto, @PathVariable Integer id) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            return ResponseEntity.ok(userRepository.save(modifyEntityWithDto(userDto, oUser.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Updates a User role by ID
     * @param id Id of User which to modify
     * @param auth The authenticated User wirh Role
     * @return ResponseEntity of the updated User
     * Returns Not Found if LogedUser or User doesn't exist.
     * Returns Unauthorized if Authenticated User is not an Admin.
     */
    @PutMapping("/{id}/makeAdmin")
    public ResponseEntity<User> makeAdmin(@PathVariable Integer id, Authentication auth) {
        Optional<User> loggedInUser = userRepository.findByEmail(auth.getName());
        if (loggedInUser.isPresent()) {
            if (loggedInUser.get().getRole().equals(User.Role.ROLE_ADMIN)) {
                Optional<User> oUser = userRepository.findById(id);
                if (oUser.isPresent()) {
                    oUser.get().setRole(User.Role.ROLE_ADMIN);
                    return ResponseEntity.ok(userRepository.save(oUser.get()));
                } else {
                    return ResponseEntity.notFound().build();
                }
            }else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Deletes a User by User Id
     * @param id Id of a user
     * @return ResponseEntity
     * returns Not Found if User doesn't exists
     */
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
