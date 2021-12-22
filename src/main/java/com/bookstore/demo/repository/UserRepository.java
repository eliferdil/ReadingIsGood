package com.bookstore.demo.repository;

import com.bookstore.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);
    User findByEmail(String email);
    User findByToken(String token);
}
