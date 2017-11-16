package com.ibiscus.myster.service.security;

import com.ibiscus.myster.model.security.User;

public interface UserService {

    User get(long userId);

    User save(User user);

    User getByUsername(String username);

}
