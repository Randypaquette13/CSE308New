package cse308.server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByFirstName(String firstName);

    //findAll(), save(), delete() implemented by inheritance
}
