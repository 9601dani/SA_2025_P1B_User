package com.danimo.user.information.application.usecases.finduserinformation;

import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.application.outputports.persistence.FindingUserInformationOutputPort;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.information.domain.UserInformationId;
import com.danimo.user.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class FindUserInformationUseCaseTest {
    private FindingUserInformationOutputPort findingUserInformationOutputPort;
    private FindUserInformationUseCase useCase;

    private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private static final BigDecimal SALARY = BigDecimal.valueOf(1000);
    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "test@example.com";
    private static final String MODULE = "MODULE";
    private static final boolean FIRST_TIME = true;
    private static final boolean MANAGER = false;
    private static final boolean ENABLED = true;
    private static final UUID LOCATION_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private static final User TEST_USER = new User(
            USER_ID,
            USERNAME,
            PASSWORD,
            EMAIL,
            FIRST_TIME,
            ENABLED,
            LOCATION_ID
    );
    private static final UserInformation TEST_USER_INFORMATION = UserInformation.builder()
            .id(UserInformationId.generate())
            .createdAt(LocalDateTime.now())
            .salaryPerWeek(SALARY)
            .user(TEST_USER)
            .build();

    @BeforeEach
    void setUp() {
        findingUserInformationOutputPort = mock(FindingUserInformationOutputPort.class);
        useCase = new FindUserInformationUseCase(findingUserInformationOutputPort);
    }

    @Test
    void shouldReturnUserInformationWhenUserExists() throws UserNotFoundException {
        // arrange
        when(findingUserInformationOutputPort.findUserInformationByUserId(USER_ID))
                .thenReturn(Optional.of(TEST_USER_INFORMATION));

        // act
        UserInformation result = useCase.findUserInformationByUserId(USER_ID.toString());

        // assert
        assertNotNull(result);
        assertEquals(SALARY, result.getSalaryPerWeek());
        assertEquals(USER_ID, result.getUser().getId());

        verify(findingUserInformationOutputPort).findUserInformationByUserId(USER_ID);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        // arrange
        when(findingUserInformationOutputPort.findUserInformationByUserId(USER_ID))
                .thenReturn(Optional.empty());

        // act y assert
        assertThrows(UserNotFoundException.class,
                () -> useCase.findUserInformationByUserId(USER_ID.toString()));

        verify(findingUserInformationOutputPort).findUserInformationByUserId(USER_ID);
    }
}
