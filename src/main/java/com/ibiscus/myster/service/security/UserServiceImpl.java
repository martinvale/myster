package com.ibiscus.myster.service.security;

import com.ibiscus.myster.model.security.User;
import com.ibiscus.myster.repository.security.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User get(long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

}
