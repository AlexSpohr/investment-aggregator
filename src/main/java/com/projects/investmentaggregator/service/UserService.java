package com.projects.investmentaggregator.service;

import com.projects.investmentaggregator.controller.dto.CreateUserDto;
import com.projects.investmentaggregator.controller.dto.UpdateUserDto;
import com.projects.investmentaggregator.entity.User;
import com.projects.investmentaggregator.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        updateUserFields(user, updateUserDto);

        userRepository.save(user);
    }

    public void updateUserFields(User user, UpdateUserDto updateUserDto) {
        if (updateUserDto.username() != null && !updateUserDto.username().isBlank()) {
            user.setUsername(updateUserDto.username());
        }

        if (updateUserDto.password() != null && !updateUserDto.password().isBlank()) {
            user.setPassword((updateUserDto.password()));
        }
    }

    public void deleteUser(String userId) {

        var userExists = userRepository.existsById(userId);
        if (userExists) {
            userRepository.deleteById(userId);
        }
    }
}
