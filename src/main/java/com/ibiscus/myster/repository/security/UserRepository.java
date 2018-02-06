package com.ibiscus.myster.repository.security;

import org.springframework.data.repository.CrudRepository;

import com.ibiscus.myster.model.security.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User getByUsername(String username);

}
