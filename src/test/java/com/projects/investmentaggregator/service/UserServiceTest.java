package com.projects.investmentaggregator.service;

import com.projects.investmentaggregator.controller.dto.CreateUserDto;
import com.projects.investmentaggregator.controller.dto.UpdateUserDto;
import com.projects.investmentaggregator.entity.User;
import com.projects.investmentaggregator.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create user with success")
        void shouldCreateUserWithSuccess() {
            // Arrange
            var user = new User(
                    UUID.randomUUID().toString(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "password"
            );

            // Act
            var output = userService.createUser(input);

            //Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(),userCaptured.getUsername());
            assertEquals(input.email(),userCaptured.getEmail());
            assertEquals(input.password(),userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());

            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "password"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {
            //Arrange
            var user = new User(
                    UUID.randomUUID().toString(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(user.getUserId());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {
            //Arrange
            var userId = UUID.randomUUID().toString();

            doReturn(Optional.empty())
                .when(userRepository)
                .findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(userId);

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());

        }
    }

    @Nested
    class listUsers {
        @Test
        @DisplayName("Should all user with success")
        void shouldReturnAllUsersWithSuccess() {
            //Arrange
            var user = new User(
                    UUID.randomUUID().toString(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(userList)
                    .when(userRepository)
                    .findAll();
            //Act
            var output = userService.listUsers();

            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteUser {
        @Test
        @DisplayName("Should delete user with success when user exist")
        void shouldDeleteUserWithSuccessWhenUserExist() {
            //Arrange
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID().toString();

            //Act
            userService.deleteUser(userId);

            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should not delete user when user not exist")
        void shouldNotDeleteUserWhenUserNotExist() {
            //Arrange
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID().toString();

            //Act
            userService.deleteUser(userId);

            //Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());
        }
    }

    @Nested
    class updateUser {
        @Test
        @DisplayName("Should update user by id when user exist and username and password filled")
        void shouldUpdateUserByIdWhenUserExistAndUsernameAndPasswordIsFilled() {
            //Arrange
            var user = new User(
                    UUID.randomUUID().toString(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            var input = new UpdateUserDto(
                    "usernameUpdate",
                    "passwordUpdate"
            );

            //Act
            userService.updateUserById(user.getUserId(), input);

            //Assert
            assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.password(), userCaptured.getPassword());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Should not update user when user not exists")
        void shouldNotUpdateUserWhenUserNotExists() {
            //Arrange
            var input = new UpdateUserDto(
                    "usernameUpdate",
                    "passwordUpdate"
            );

            var userId = UUID.randomUUID().toString();

            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                userService.updateUserById(userId, input);
            });

            //Assert
            assertEquals("Usuário não encontrado com ID: " + userId, exception.getMessage());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("Should not update user fields when UpdateUserDto contains invalid data")
        void shouldNotUpdateUserFieldsWhenUpdateUserDtoContainsInvalidData() {
            // Arrange
            var userId = UUID.randomUUID().toString();
            var existingUser = new User(
                    userId,
                    "existingUsername",
                    "email@email.com",
                    "existingPassword",
                    Instant.now(),
                    null
            );
            var input = new UpdateUserDto(null, "");

            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

            // Act
            userService.updateUserById(userId, input);

            // Assert
            verify(userRepository, times(1)).findById(userId);
            verify(userRepository, times(1)).save(userArgumentCaptor.capture());

            User savedUser = userArgumentCaptor.getValue();
            assertEquals("existingUsername", savedUser.getUsername());
            assertEquals("existingPassword", savedUser.getPassword());
            assertEquals(userId, savedUser.getUserId());
        }

        @Test
        @DisplayName("Should not update username when it is blank")
        void shouldNotUpdateUsernameWhenItIsBlank() {
            // Arrange
            var userId = UUID.randomUUID().toString();
            var existingUser = new User(
                    userId,
                    "existingUsername",
                    "email@email.com",
                    "existingPassword",
                    Instant.now(),
                    null
            );
            var input = new UpdateUserDto("   ", "validPassword");

            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

            // Act
            userService.updateUserById(userId, input);

            // Assert
            verify(userRepository).save(userArgumentCaptor.capture());
            User savedUser = userArgumentCaptor.getValue();
            assertEquals("existingUsername", savedUser.getUsername());
            assertEquals("validPassword", savedUser.getPassword());
        }

        @Test
        @DisplayName("Should update username when it is valid")
        void shouldUpdateUsernameWhenItIsValid() {
            // Arrange
            var userId = UUID.randomUUID().toString();
            var existingUser = new User(
                    userId,
                    "existingUsername",
                    "email@email.com",
                    "existingPassword",
                    Instant.now(),
                    null
            );
            var input = new UpdateUserDto("newUsername", null);

            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

            // Act
            userService.updateUserById(userId, input);

            // Assert
            verify(userRepository).save(userArgumentCaptor.capture());
            User savedUser = userArgumentCaptor.getValue();
            assertEquals("newUsername", savedUser.getUsername());
            assertEquals("existingPassword", savedUser.getPassword());
        }

        @Test
        @DisplayName("Should not update password when it is null")
        void shouldNotUpdatePasswordWhenItIsNull() {
            // Arrange
            var userId = UUID.randomUUID().toString();
            var existingUser = new User(
                    userId,
                    "existingUsername",
                    "email@email.com",
                    "existingPassword",
                    Instant.now(),
                    null
            );
            var input = new UpdateUserDto("newUsername", null);

            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

            // Act
            userService.updateUserById(userId, input);

            // Assert
            verify(userRepository).save(userArgumentCaptor.capture());
            User savedUser = userArgumentCaptor.getValue();
            assertEquals("newUsername", savedUser.getUsername());
            assertEquals("existingPassword", savedUser.getPassword());
        }

        @Test
        @DisplayName("Should update password when it is valid")
        void shouldUpdatePasswordWhenItIsValid() {
            // Arrange
            var userId = UUID.randomUUID().toString();
            var existingUser = new User(
                    userId,
                    "existingUsername",
                    "email@email.com",
                    "existingPassword",
                    Instant.now(),
                    null
            );
            var input = new UpdateUserDto(null, "newPassword");

            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

            // Act
            userService.updateUserById(userId, input);

            // Assert
            verify(userRepository).save(userArgumentCaptor.capture());
            User savedUser = userArgumentCaptor.getValue();
            assertEquals("existingUsername", savedUser.getUsername());
            assertEquals("newPassword", savedUser.getPassword());
        }
    }
}

