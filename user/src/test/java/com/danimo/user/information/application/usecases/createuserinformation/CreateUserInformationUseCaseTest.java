package com.danimo.user.information.application.usecases.createuserinformation;

import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.application.outputports.persistence.StoringUserInformationOutputPort;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateUserInformationUseCaseTest {
    private FindingUserByUsernameOutputPort findingUserByUsernameOutputPort;
    private StoringUserInformationOutputPort storingUserInformationOutputPort;
    private CreateUserInformationUseCase useCase;

    private static final UUID USER_ID = new UUID(0, 0);
    private static final BigDecimal SALARY = BigDecimal.valueOf(1000);

    private static final CreateUserInformationDto TEST_DTO = new CreateUserInformationDto(SALARY, USER_ID);

    @BeforeEach
    void setUp() {
        findingUserByUsernameOutputPort = mock(FindingUserByUsernameOutputPort.class);
        storingUserInformationOutputPort = mock(StoringUserInformationOutputPort.class);
        useCase = new CreateUserInformationUseCase(findingUserByUsernameOutputPort, storingUserInformationOutputPort);
    }

    @Test
    void shouldCreateUserInformationWhenUserExists() throws UserNotFoundException {
        // arrange
        when(findingUserByUsernameOutputPort.findById(USER_ID.toString()))
                .thenReturn(Optional.of(
                        User.builder()
                                .id(USER_ID)
                                .username("testuser")
                                .password("password")
                                .email("test@example.com")
                                .firstTime(true)
                                .enabled(true)
                                .locationId(UUID.randomUUID())
                                .build()
                ));

        when(storingUserInformationOutputPort.save(any(UserInformation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        UserInformation result = useCase.createUserInformation(TEST_DTO);

        //assert
        assertNotNull(result);
        assertEquals(SALARY, result.getSalaryPerWeek());
        assertEquals(USER_ID, result.getUser().getId());

        verify(findingUserByUsernameOutputPort).findById(USER_ID.toString());
        verify(storingUserInformationOutputPort).save(any(UserInformation.class));
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        //arrange
        when(findingUserByUsernameOutputPort.findById(USER_ID.toString()))
                .thenReturn(Optional.empty());
        //act y assert
        assertThrows(UserNotFoundException.class, () -> useCase.createUserInformation(TEST_DTO));

        verify(findingUserByUsernameOutputPort).findById(USER_ID.toString());
        verifyNoInteractions(storingUserInformationOutputPort);
    }
}
