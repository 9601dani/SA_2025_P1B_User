package com.danimo.user.user.application.usecases.createuser;

import com.danimo.user.common.application.exceptions.LocationNotFoundException;
import com.danimo.user.common.application.exceptions.UserAlreadyExistsException;
import com.danimo.user.information.application.outputports.persistence.StoringUserInformationOutputPort;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.application.outputports.persistence.StoringUserOutputPort;
import com.danimo.user.user.application.outputports.rest.ExistLocationOutputPort;
import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.outputadapters.security.BCryptPasswordEncoderAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserUseCaseTest {

    private CreateUserUseCase createUserUseCase;
    private StoringUserOutputPort storingUserOutputPort;
    private FindingUserByUsernameOutputPort findingUserByUsernameOutputPort;
    private BCryptPasswordEncoderAdapter passwordEncoder;
    private StoringUserInformationOutputPort storingUserInformationOutputPort;
    private ExistLocationOutputPort existLocationOutputPort;

    @BeforeEach
    void setUp() {
        storingUserOutputPort = mock(StoringUserOutputPort.class);
        findingUserByUsernameOutputPort = mock(FindingUserByUsernameOutputPort.class);
        passwordEncoder = mock(BCryptPasswordEncoderAdapter.class);
        storingUserInformationOutputPort = mock(StoringUserInformationOutputPort.class);
        existLocationOutputPort = mock(ExistLocationOutputPort.class);

        createUserUseCase = new CreateUserUseCase(
                storingUserOutputPort,
                findingUserByUsernameOutputPort,
                passwordEncoder,
                storingUserInformationOutputPort,
                existLocationOutputPort
        );
    }

    @Test
    void givenExistingUsername_whenCreateUser_thenThrowsUserAlreadyExistsException() {
        // Arrange
        final String USERNAME = "existingUser";
        final String EMAIL = "test@example.com";
        final UUID LOCATION_ID = UUID.randomUUID();
        final BigDecimal SALARY = BigDecimal.valueOf(100.0);

        CreateUserDto dto = new CreateUserDto(
                USERNAME, "password", EMAIL, null, SALARY, false, LOCATION_ID
        );

        when(findingUserByUsernameOutputPort.findByUsername(USERNAME)).thenReturn(Optional.of(User.builder().build()));

        // Act & Assert
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> createUserUseCase.createUser(dto));

        assertTrue(exception.getMessage().contains(USERNAME));


        verify(findingUserByUsernameOutputPort, never()).findByEmail(anyString());
        verify(existLocationOutputPort, never()).existLocation(any());
    }

    @Test
    void givenExistingEmail_whenCreateUser_thenThrowsUserAlreadyExistsException() {
        // Arrange
        final String USERNAME = "newUser";
        final String EMAIL = "existing@example.com";
        final UUID LOCATION_ID = UUID.randomUUID();
        final BigDecimal SALARY = BigDecimal.valueOf(150.0);

        CreateUserDto dto = new CreateUserDto(
                USERNAME, "password", EMAIL, null, SALARY, false, LOCATION_ID
        );

        when(findingUserByUsernameOutputPort.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(findingUserByUsernameOutputPort.findByEmail(EMAIL)).thenReturn(Optional.of(User.builder().build()));

        // Act & Assert
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> createUserUseCase.createUser(dto));

        assertTrue(exception.getMessage().contains(EMAIL));
        verify(existLocationOutputPort, never()).existLocation(any());
    }

    @Test
    void givenNonExistentLocation_whenCreateUser_thenThrowsLocationNotFoundException() {
        // Arrange
        final String USERNAME = "newUser";
        final String EMAIL = "new@example.com";
        final UUID LOCATION_ID = UUID.randomUUID();
        final BigDecimal SALARY = BigDecimal.valueOf(200.0);

        CreateUserDto dto = new CreateUserDto(
                USERNAME, "password", EMAIL, null, SALARY, false, LOCATION_ID
        );

        when(findingUserByUsernameOutputPort.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(findingUserByUsernameOutputPort.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(existLocationOutputPort.existLocation(LOCATION_ID)).thenReturn(false);

        // Act & Assert
        LocationNotFoundException exception = assertThrows(LocationNotFoundException.class,
                () -> createUserUseCase.createUser(dto));

        assertTrue(exception.getMessage().contains(LOCATION_ID.toString()));
    }

    @Test
    void givenValidUser_whenCreateUser_thenUserSavedAndInformationStored() {
        // Arrange
        final String USERNAME = "newUser";
        final String EMAIL = "new@example.com";
        final String PASSWORD = "password";
        final String HASHED_PASSWORD = "hashedPassword";
        final UUID LOCATION_ID = UUID.randomUUID();
        final BigDecimal SALARY = BigDecimal.valueOf(300.0);

        CreateUserDto dto = new CreateUserDto(
                USERNAME, PASSWORD, EMAIL, null,  SALARY, false, LOCATION_ID
        );

        when(findingUserByUsernameOutputPort.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(findingUserByUsernameOutputPort.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(existLocationOutputPort.existLocation(LOCATION_ID)).thenReturn(true);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(HASHED_PASSWORD);

        User savedUser = new User(UUID.randomUUID(), USERNAME, HASHED_PASSWORD, EMAIL, null, true, false, true, LOCATION_ID);
        when(storingUserOutputPort.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = createUserUseCase.createUser(dto);

        // Assert
        assertNotNull(result);
        assertEquals(USERNAME, result.getUsername());
        assertEquals(EMAIL, result.getEmail());

        ArgumentCaptor<UserInformation> infoCaptor = ArgumentCaptor.forClass(UserInformation.class);
        verify(storingUserInformationOutputPort).save(infoCaptor.capture());

        UserInformation infoSaved = infoCaptor.getValue();
        assertEquals(SALARY, infoSaved.getSalaryPerWeek());
        assertEquals(result.getId(), infoSaved.getUser().getId());
    }
}
