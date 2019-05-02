package cse308.server.repository;

import cse308.server.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByFirstName(String firstName);

    //findAll(), save(), delete() implemented by inheritance
}
