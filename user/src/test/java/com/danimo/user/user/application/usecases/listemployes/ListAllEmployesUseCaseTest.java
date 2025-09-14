package com.danimo.user.user.application.usecases.listallemployes;

import com.danimo.user.user.application.outputports.persistence.FindingAllEmployesOutputPort;
import com.danimo.user.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListAllEmployesUseCaseTest {

    @Mock
    private FindingAllEmployesOutputPort findingAllEmployesOutputPort;

    @InjectMocks
    private ListAllEmployesUseCase listAllEmployesUseCase;

    @Test
    void givenEmployesExist_whenListAllEmployes_thenReturnList() {
        // Arrange
        User user1 = new User(UUID.randomUUID(), "john.doe", "pass", "john@example.com", null, true, false, true, null);
        User user2 = new User(UUID.randomUUID(), "jane.doe", "pass", "jane@example.com", null, true, false, true, null);

        List<User> employes = Arrays.asList(user1, user2);

        when(findingAllEmployesOutputPort.findAllEmployes()).thenReturn(employes);

        // Act
        List<User> result = listAllEmployesUseCase.listAllEmployes();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));

        verify(findingAllEmployesOutputPort).findAllEmployes();
    }

    @Test
    void givenNoEmployesExist_whenListAllEmployes_thenReturnEmptyList() {
        // Arrange
        when(findingAllEmployesOutputPort.findAllEmployes()).thenReturn(Collections.emptyList());

        // Act
        List<User> result = listAllEmployesUseCase.listAllEmployes();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(findingAllEmployesOutputPort).findAllEmployes();
    }
}
