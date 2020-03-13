package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    List<User> findByFamilyname(String familiname);
    List<User> findByGivenname(String givenname);
    Optional<User> findByEmail(String email);
    List<User> findByEnable(boolean isEnable);


}
