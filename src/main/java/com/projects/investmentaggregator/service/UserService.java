package com.projects.investmentaggregator.service;

import com.projects.investmentaggregator.controller.dto.AccountResponseDto;
import com.projects.investmentaggregator.controller.dto.CreateAccountDto;
import com.projects.investmentaggregator.controller.dto.CreateUserDto;
import com.projects.investmentaggregator.controller.dto.UpdateUserDto;
import com.projects.investmentaggregator.entity.Account;
import com.projects.investmentaggregator.entity.BillingAddress;
import com.projects.investmentaggregator.entity.User;
import com.projects.investmentaggregator.exception.UserEmailAlreadyExistException;
import com.projects.investmentaggregator.exception.UserNotFoundException;
import com.projects.investmentaggregator.repository.AccountRepository;
import com.projects.investmentaggregator.repository.BillingAddressRepository;
import com.projects.investmentaggregator.repository.UserRepository;
import com.projects.investmentaggregator.util.PasswordUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BillingAddressRepository billingAddressRepository;
    private final PasswordUtil passwordUtil;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       BillingAddressRepository billingAddressRepository, PasswordUtil passwordUtil) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.passwordUtil = passwordUtil;
    }

    @Transactional
    public String createUser(CreateUserDto createUserDto) {

        var userDb = userRepository.findByEmail(createUserDto.email());

        if (userDb.isPresent()) {
            throw new UserEmailAlreadyExistException("Email already exists");
        }

        var user = createUserDto.toUser();
        user.setPassword(passwordUtil.encode(createUserDto.password()));

        var userSaved = userRepository.save(user);
        return userSaved.getUserId();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        updateUserFields(user, updateUserDto);

        userRepository.save(user);
    }

    @Transactional
    public void updateUserFields(User user, UpdateUserDto updateUserDto) {
        if (updateUserDto.username() != null && !updateUserDto.username().isBlank()) {
            user.setUsername(updateUserDto.username());
        }

        if (updateUserDto.password() != null && !updateUserDto.password().isBlank()) {
            user.setPassword(passwordUtil.encode(updateUserDto.password()));
        }
    }

    @Transactional
    public void deleteUser(String userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);

    }

    @Transactional
    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

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

    @Transactional(readOnly = true)
    public List<AccountResponseDto> listAccounts(String userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return user.getAccounts()
                .stream()
                .map(account -> new AccountResponseDto(account.getAccountId().toString(), account.getDescription()))
                .toList();
    }
}
