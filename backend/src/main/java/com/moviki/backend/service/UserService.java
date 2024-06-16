package com.moviki.backend.service;

import com.moviki.backend.model.User;
import com.moviki.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            log.info("User with id: {} doesn't exist!", id);
        }

        return optionalUser.orElse(null);
    }

    @Transactional
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        log.info("User with id: {} saved successfully!", user.getId());
        return savedUser;
    }

    @Transactional
    public User updateUser(User user) {
        User updatedUser = userRepository.save(user);
        log.info("User with id: {} updated successfully!", user.getId());
        return updatedUser;
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
