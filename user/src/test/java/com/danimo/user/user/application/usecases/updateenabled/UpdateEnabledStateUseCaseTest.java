package com.danimo.user.user.application.usecases.updateenabled;

import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.user.application.outputports.persistence.StoringUserOutputPort;
import com.danimo.user.user.application.outputports.persistence.UpdatingEnabledStateOutputPort;
import com.danimo.user.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateEnabledStateUseCaseTest {

    @Mock
    private UpdatingEnabledStateOutputPort outputPort;

    @Mock
    private StoringUserOutputPort storingUserOutputPort;

    @InjectMocks
    private UpdateEnabledStateUseCase updateEnabledStateUseCase;

    @Test
    void givenExistingUser_whenUpdateEnabledState_thenUserUpdatedAndSaved() {
        // Arrange
        final String USERNAME = "john.doe";
        final boolean NEW_ENABLED = false;

        UpdateEnabledStateDto dto = new UpdateEnabledStateDto(NEW_ENABLED, USERNAME);

        User user = new User(UUID.randomUUID(), USERNAME, "pass", "john@example.com", true, false, null);

        when(outputPort.updateEnabledState(dto)).thenReturn(Optional.of(user));

        // Act
        User result = updateEnabledStateUseCase.updateEnabledState(dto);

        // Assert
        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        assertEquals(NEW_ENABLED, result.isEnabled());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(storingUserOutputPort).save(captor.capture());
        assertEquals(NEW_ENABLED, captor.getValue().isEnabled());

        verify(outputPort).updateEnabledState(dto);
    }

    @Test
    void givenNonExistentUser_whenUpdateEnabledState_thenThrowsUserNotFoundException() {
        // Arrange
        final String USERNAME = "unknown";
        final boolean NEW_ENABLED = true;

        UpdateEnabledStateDto dto = new UpdateEnabledStateDto(NEW_ENABLED, USERNAME);

        when(outputPort.updateEnabledState(dto)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> updateEnabledStateUseCase.updateEnabledState(dto));

        assertTrue(exception.getMessage().contains(USERNAME));

        verify(storingUserOutputPort, never()).save(any());
        verify(outputPort).updateEnabledState(dto);
    }
}
