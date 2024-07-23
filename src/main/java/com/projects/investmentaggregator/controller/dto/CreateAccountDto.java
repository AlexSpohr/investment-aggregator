package com.projects.investmentaggregator.controller.dto;

import com.projects.investmentaggregator.entity.Account;
import com.projects.investmentaggregator.entity.User;

public record CreateAccountDto(String description,
                               String street,
                               Integer number) {
}
