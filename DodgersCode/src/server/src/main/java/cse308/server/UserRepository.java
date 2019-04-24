package cse308.server;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String email);

    User findByFirstName(String firstName);
}
