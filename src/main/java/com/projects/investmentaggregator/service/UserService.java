package com.projects.investmentaggregator.service;

import com.projects.investmentaggregator.controller.dto.CreateUserDto;
import com.projects.investmentaggregator.entity.User;
import com.projects.investmentaggregator.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(CreateUserDto createUserDto) {

        var userSaved = userRepository.save(createUserDto.toUser());
        return userSaved.getUserId();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(String userId) {

        return userRepository.findById(userId);

    }
}
