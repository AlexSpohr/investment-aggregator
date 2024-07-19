package com.projects.investmentaggregator.controller.dto;

import com.projects.investmentaggregator.entity.User;

public record CreateUserDto(
        String username,
        String email,
        String password) {

    public User toUser() {
        return new User(
            username,
                email,
                password
        );
    }
}
