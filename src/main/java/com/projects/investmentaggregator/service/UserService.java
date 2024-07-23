package com.projects.investmentaggregator.service;

import com.projects.investmentaggregator.controller.dto.AccountResponseDto;
import com.projects.investmentaggregator.controller.dto.CreateAccountDto;
import com.projects.investmentaggregator.controller.dto.CreateUserDto;
import com.projects.investmentaggregator.controller.dto.UpdateUserDto;
import com.projects.investmentaggregator.entity.Account;
import com.projects.investmentaggregator.entity.BillingAddress;
import com.projects.investmentaggregator.entity.User;
import com.projects.investmentaggregator.repository.AccountRepository;
import com.projects.investmentaggregator.repository.BillingAddressRepository;
import com.projects.investmentaggregator.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
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

    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account (
                createAccountDto.description(),
                null,
                user,
                new ArrayList<>()
        );

        var savedAccount = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                savedAccount.getAccountId(),
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDto> listAccounts(String userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(account -> new AccountResponseDto(account.getAccountId().toString(), account.getDescription()))
                .toList();
    }
}
