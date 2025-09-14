package com.danimo.user.user.application.usecases.finduser;

import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserByUsernameUseCaseTest {

    @Mock
    private FindingUserByUsernameOutputPort findingByIdPort;

    @InjectMocks
    private FindUserByUsernameUseCase findUserUseCase;

    @Test
    void givenExistingUsername_whenFindByUsername_thenReturnUser() {
        // Arrange
        final String USERNAME = "john.doe";
        User user = new User(UUID.randomUUID(), USERNAME, "pass", "john@example.com", null, true, false, true, null);
        when(findingByIdPort.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        // Act
        User result = findUserUseCase.findByUsername(USERNAME);

        // Assert
        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        verify(findingByIdPort).findByUsername(USERNAME);
        verify(findingByIdPort, never()).findByEmail(anyString());
        verify(findingByIdPort, never()).findById(anyString());
    }

    @Test
    void givenExistingEmail_whenFindByUsername_thenReturnUser() {
        // Arrange
        final String EMAIL = "jane@example.com";
        User user = new User(UUID.randomUUID(), "jane.doe", "pass", EMAIL, null, true, false, true, null);
        when(findingByIdPort.findByUsername(EMAIL)).thenReturn(Optional.empty());
        when(findingByIdPort.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        // Act
        User result = findUserUseCase.findByUsername(EMAIL);

        // Assert
        assertNotNull(result);
        assertEquals(EMAIL, result.getEmail());
        verify(findingByIdPort).findByUsername(EMAIL);
        verify(findingByIdPort).findByEmail(EMAIL);
        verify(findingByIdPort, never()).findById(anyString());
    }

    @Test
    void givenExistingId_whenFindByUsername_thenReturnUser() {
        // Arrange
        final String ID = UUID.randomUUID().toString();
        User user = new User(UUID.fromString(ID), "bob.doe", "pass", "bob@example.com", null, true, false, true, null);
        when(findingByIdPort.findByUsername(ID)).thenReturn(Optional.empty());
        when(findingByIdPort.findByEmail(ID)).thenReturn(Optional.empty());
        when(findingByIdPort.findById(ID)).thenReturn(Optional.of(user));

        // Act
        User result = findUserUseCase.findByUsername(ID);

        // Assert
        assertNotNull(result);
        assertEquals(ID, result.getId().toString());
        verify(findingByIdPort).findByUsername(ID);
        verify(findingByIdPort).findByEmail(ID);
        verify(findingByIdPort).findById(ID);
    }

    @Test
    void givenNonExistentUser_whenFindByUsername_thenThrowsUserNotFoundException() {
        // Arrange
        final String USERNAME = "unknown";
        when(findingByIdPort.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(findingByIdPort.findByEmail(USERNAME)).thenReturn(Optional.empty());
        when(findingByIdPort.findById(USERNAME)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> findUserUseCase.findByUsername(USERNAME));

        assertTrue(exception.getMessage().contains(USERNAME));
        verify(findingByIdPort).findByUsername(USERNAME);
        verify(findingByIdPort).findByEmail(USERNAME);
        verify(findingByIdPort).findById(USERNAME);
    }
}
