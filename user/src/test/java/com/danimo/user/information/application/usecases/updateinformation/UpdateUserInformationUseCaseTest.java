package com.danimo.user.information.application.usecases.updateinformation;

import com.danimo.user.common.application.exceptions.CredentialsDoesntSame;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.application.outputports.persistence.FindingUserInformationOutputPort;
import com.danimo.user.information.application.outputports.persistence.StoringUserInformationOutputPort;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.information.domain.UserInformationId;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.application.outputports.persistence.StoringUserOutputPort;
import com.danimo.user.user.application.outputports.security.PasswordEncoderPort;
import com.danimo.user.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateUserInformationUseCaseTest {

    private FindingUserInformationOutputPort findingUserInformationOutputPort;
    private StoringUserInformationOutputPort storingUserInformationOutputPort;
    private StoringUserOutputPort storingUserOutputPort;
    private FindingUserByUsernameOutputPort findingUserByUsernameOutputPort;
    private UpdateUserInformationUseCase useCase;
    private PasswordEncoderPort passwordEncoder;

    private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private static final String OLD_PASSWORD = "oldPassword";
    private static final String NEW_PASSWORD = "newPassword";

    private static final User TEST_USER = new User(
            USER_ID,
            "testuser",
            OLD_PASSWORD,
            "test@example.com",
            true,
            false,
            UUID.randomUUID()
    );

    private static final UserInformation TEST_USER_INFORMATION = UserInformation.builder()
            .id(UserInformationId.fromUUID(USER_ID))
            .salaryPerWeek(BigDecimal.valueOf(1000))
            .user(TEST_USER)
            .name("John")
            .lastName("Doe")
            .birthdate(LocalDate.of(2000,1,1))
            .build();

    private static final UpdateUserInformationDto DTO = new UpdateUserInformationDto(
            USER_ID,
            "Jane",
            "Smith",
            OLD_PASSWORD,
            NEW_PASSWORD,
            LocalDate.of(1995,5,5)
    );

    @BeforeEach
    void setUp() {
        findingUserInformationOutputPort = mock(FindingUserInformationOutputPort.class);
        storingUserInformationOutputPort = mock(StoringUserInformationOutputPort.class);
        storingUserOutputPort = mock(StoringUserOutputPort.class);
        findingUserByUsernameOutputPort = mock(FindingUserByUsernameOutputPort.class);
        passwordEncoder = mock(PasswordEncoderPort.class);

        useCase = new UpdateUserInformationUseCase(
                findingUserInformationOutputPort,
                storingUserInformationOutputPort,
                storingUserOutputPort,
                findingUserByUsernameOutputPort,
                passwordEncoder
        );
    }

    @Test
    void shouldUpdateUserInformationSuccessfully() {
        // Arrange
        when(findingUserInformationOutputPort.findUserInformationByUserId(USER_ID))
                .thenReturn(Optional.of(TEST_USER_INFORMATION));
        when(findingUserByUsernameOutputPort.findById(USER_ID.toString()))
                .thenReturn(Optional.of(TEST_USER));
        when(storingUserInformationOutputPort.save(any(UserInformation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(storingUserOutputPort.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(passwordEncoder.matches(DTO.getOldPassword(), TEST_USER.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(DTO.getNewPassword())).thenReturn(NEW_PASSWORD);

        // Act
        UserInformation result = useCase.updateUserInformation(DTO);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getName());
        assertEquals("Smith", result.getLastName());
        assertEquals(LocalDate.of(1995,5,5), result.getBirthdate());
        assertFalse(TEST_USER.isFirstTime());
        assertNotEquals(OLD_PASSWORD, TEST_USER.getPassword());
        assertEquals(NEW_PASSWORD, TEST_USER.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenUserInformationNotFound() {
        // Arrange
        when(findingUserInformationOutputPort.findUserInformationByUserId(USER_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> useCase.updateUserInformation(DTO));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(findingUserInformationOutputPort.findUserInformationByUserId(USER_ID))
                .thenReturn(Optional.of(TEST_USER_INFORMATION));
        when(findingUserByUsernameOutputPort.findById(USER_ID.toString()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> useCase.updateUserInformation(DTO));
    }

    @Test
    void shouldThrowExceptionWhenOldPasswordDoesntMatch() {
        // Arrange
        UpdateUserInformationDto dtoWrongPassword = new UpdateUserInformationDto(
                USER_ID,
                "wrongPassword",
                NEW_PASSWORD,
                "Jane",
                "Smith",
                LocalDate.of(1995,5,5)
        );

        when(findingUserInformationOutputPort.findUserInformationByUserId(USER_ID))
                .thenReturn(Optional.of(TEST_USER_INFORMATION));
        when(findingUserByUsernameOutputPort.findById(USER_ID.toString()))
                .thenReturn(Optional.of(TEST_USER));

        // Act & Assert
        assertThrows(CredentialsDoesntSame.class, () -> useCase.updateUserInformation(dtoWrongPassword));
    }
}
