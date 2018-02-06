package com.ibiscus.myster.service.security;

import com.ibiscus.myster.model.security.User;
import com.ibiscus.myster.repository.security.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(long userId) {
        return userRepository.findOne(userId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.getByUsername(username));
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return user.map(u -> new UserInfo(u)).get();
    }
}
