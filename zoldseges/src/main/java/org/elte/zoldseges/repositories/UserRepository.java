package org.elte.zoldseges.repositories;

import org.elte.zoldseges.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByFamilyname(String familyName);
}
