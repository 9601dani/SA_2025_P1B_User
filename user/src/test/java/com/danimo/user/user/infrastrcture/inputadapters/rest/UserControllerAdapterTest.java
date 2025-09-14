package com.danimo.user.user.infrastrcture.inputadapters.rest;

import com.danimo.user.common.application.exceptions.InactiveAccountException;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.user.application.inputports.CreatingUserIputPort;
import com.danimo.user.user.application.inputports.FindingUserByUsernameInputPort;
import com.danimo.user.user.application.inputports.UpdatingEnabledStateInputPort;
import com.danimo.user.user.application.outputports.persistence.FindingAllEmployesOutputPort;
import com.danimo.user.user.application.usecases.createuser.CreateUserDto;
import com.danimo.user.user.application.usecases.updateenabled.UpdateEnabledStateDto;
import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.inputadapters.rest.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserControllerAdapter.class)
class UserControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FindingUserByUsernameInputPort findingUserByUsernameInputPort;

    @MockitoBean
    private CreatingUserIputPort creatingUserIputPort;

    @MockitoBean
    private FindingAllEmployesOutputPort findingAllEmployes;

    @MockitoBean
    private UpdatingEnabledStateInputPort updatingEnabledStateInputPort;

    @MockitoBean
    private PlatformTransactionManager transactionManager;

    private User sampleUser;
    private UUID userId;
    private UUID locationId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        locationId = UUID.randomUUID();
        sampleUser = new User(userId, "john.doe", "password123", "john@example.com",
                "module1", true, false, true, locationId);
    }

    @Test
    void givenValidRequest_whenCreateUser_thenReturnsCreatedUserResponse() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("john.doe", "password123", "john@example.com",
                "module1", BigDecimal.valueOf(1000), false, locationId.toString());

        when(creatingUserIputPort.createUser(any(CreateUserDto.class))).thenReturn(sampleUser);

        String requestJson = objectMapper.writeValueAsString(request);

        // Act & Assert
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(sampleUser.getId().toString())))
                .andExpect(jsonPath("$.username", is(sampleUser.getUsername())))
                .andExpect(jsonPath("$.email", is(sampleUser.getEmail())))
                .andExpect(jsonPath("$.module", is(sampleUser.getModule())))
                .andExpect(jsonPath("$.firstTime", is(sampleUser.isFirstTime())));

        ArgumentCaptor<CreateUserDto> captor = ArgumentCaptor.forClass(CreateUserDto.class);
        verify(creatingUserIputPort).createUser(captor.capture());
        assertEquals("john.doe", captor.getValue().getUsername());
        assertEquals("john@example.com", captor.getValue().getEmail());
    }

    @Test
    void givenExistingUsername_whenFindingUserByUsername_thenReturnsUserResponse() throws Exception {
        // Arrange
        when(findingUserByUsernameInputPort.findByUsername("john.doe")).thenReturn(sampleUser);

        // Act & Assert
        mockMvc.perform(get("/v1/users/john.doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(sampleUser.getId().toString())))
                .andExpect(jsonPath("$.username", is(sampleUser.getUsername())))
                .andExpect(jsonPath("$.password", is(sampleUser.getPassword())))
                .andExpect(jsonPath("$.email", is(sampleUser.getEmail())))
                .andExpect(jsonPath("$.module", is(sampleUser.getModule())))
                .andExpect(jsonPath("$.firstTime", is(sampleUser.isFirstTime())))
                .andExpect(jsonPath("$.manager", is(sampleUser.isManager())))
                .andExpect(jsonPath("$.enabled", is(sampleUser.isEnabled())))
                .andExpect(jsonPath("$.locationId", is(sampleUser.getLocationId().toString())));

        verify(findingUserByUsernameInputPort).findByUsername("john.doe");
    }

    @Test
    void givenNonExistingUsername_whenFindingUserByUsername_thenReturnsNotFound() throws Exception {
        // Arrange
        when(findingUserByUsernameInputPort.findByUsername("unknown"))
                .thenThrow(new UserNotFoundException("unknown"));

        // Act & Assert
        mockMvc.perform(get("/v1/users/unknown"))
                .andExpect(status().isNotFound());

        verify(findingUserByUsernameInputPort).findByUsername("unknown");
    }

    @Test
    void givenUsersExist_whenFindAllUsers_thenReturnsUsersList() throws Exception {
        // Arrange
        User secondUser = new User(UUID.randomUUID(), "jane.doe", "password456", "jane@example.com",
                "module2", false, true, true, UUID.randomUUID());

        List<User> users = Arrays.asList(sampleUser, secondUser);
        when(findingAllEmployes.findAllEmployes()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(sampleUser.getId().toString())))
                .andExpect(jsonPath("$[0].username", is(sampleUser.getUsername())))
                .andExpect(jsonPath("$[0].email", is(sampleUser.getEmail())))
                .andExpect(jsonPath("$[0].module", is(sampleUser.getModule())))
                .andExpect(jsonPath("$[0].manager", is(sampleUser.isManager())))
                .andExpect(jsonPath("$[0].enabled", is(sampleUser.isEnabled())))
                .andExpect(jsonPath("$[0].locationId", is(sampleUser.getLocationId().toString())))
                .andExpect(jsonPath("$[1].id", is(secondUser.getId().toString())))
                .andExpect(jsonPath("$[1].username", is(secondUser.getUsername())));

        verify(findingAllEmployes).findAllEmployes();
    }

    @Test
    void givenNoUsers_whenFindAllUsers_thenReturnsEmptyList() throws Exception {
        // Arrange
        when(findingAllEmployes.findAllEmployes()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(findingAllEmployes).findAllEmployes();
    }

    @Test
    void givenUserEnabledTrue_whenUpdateEnabledState_thenReturnsUserListResponse() throws Exception {
        // Arrange
        UpdateUserEnabledStateRequest request = new UpdateUserEnabledStateRequest("john.doe", true);
        User enabledUser = new User(userId, "john.doe", "password123", "john@example.com",
                "module1", true, false, true, locationId);

        when(updatingEnabledStateInputPort.updateEnabledState(any(UpdateEnabledStateDto.class)))
                .thenReturn(enabledUser);

        String requestJson = objectMapper.writeValueAsString(request);

        // Act & Assert
        mockMvc.perform(put("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(enabledUser.getId().toString())))
                .andExpect(jsonPath("$.username", is(enabledUser.getUsername())))
                .andExpect(jsonPath("$.email", is(enabledUser.getEmail())))
                .andExpect(jsonPath("$.module", is(enabledUser.getModule())))
                .andExpect(jsonPath("$.manager", is(enabledUser.isManager())))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.locationId", is(enabledUser.getLocationId().toString())));

        ArgumentCaptor<UpdateEnabledStateDto> captor = ArgumentCaptor.forClass(UpdateEnabledStateDto.class);
        verify(updatingEnabledStateInputPort).updateEnabledState(captor.capture());
        assertEquals("john.doe", captor.getValue().username());
        assertEquals(true, captor.getValue().enabled());
    }

    @Test
    void givenUserDisabled_whenUpdateEnabledState_thenThrowsInactiveAccountException() throws Exception {
        // Arrange
        UpdateUserEnabledStateRequest request = new UpdateUserEnabledStateRequest("john.doe", false);
        User disabledUser = new User(userId, "john.doe", "password123", "john@example.com",
                "module1", true, false, false, locationId);

        when(updatingEnabledStateInputPort.updateEnabledState(any(UpdateEnabledStateDto.class)))
                .thenReturn(disabledUser);

        String requestJson = objectMapper.writeValueAsString(request);

        // Act & Assert
        mockMvc.perform(put("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isLocked())
                .andExpect(jsonPath("$.statusCode", is(423)))
                .andExpect(jsonPath("$.message", is("El usuario: john.doe esta inactivado, Favor contacta con un Administador")));

        verify(updatingEnabledStateInputPort).updateEnabledState(any(UpdateEnabledStateDto.class));
    }

    @Test
    void givenExistingUser_whenCheckExistence_thenReturnsOk() throws Exception {
        // Arrange
        when(findingUserByUsernameInputPort.findByUsername("john.doe")).thenReturn(sampleUser);

        // Act & Assert
        mockMvc.perform(head("/v1/users/check/john.doe"))
                .andExpect(status().isOk());

        verify(findingUserByUsernameInputPort).findByUsername("john.doe");
    }

    @Test
    void givenNonExistingUser_whenCheckExistence_thenReturnsNotFound() throws Exception {
        // Arrange
        when(findingUserByUsernameInputPort.findByUsername("unknown"))
                .thenThrow(new UserNotFoundException("unknown"));

        // Act & Assert
        mockMvc.perform(head("/v1/users/check/unknown"))
                .andExpect(status().isNotFound());

        verify(findingUserByUsernameInputPort).findByUsername("unknown");
    }

    @Test
    void givenValidCreateUserRequest_whenConvertToDomain_thenReturnsCorrectDto() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("test.user", "password123",
                "test@example.com", "testModule", BigDecimal.valueOf(2000), true, locationId.toString());

        // Act
        CreateUserDto dto = request.toDomain();

        // Assert
        assertEquals("test.user", dto.getUsername());
        assertEquals("password123", dto.getPassword());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("testModule", dto.getModule());
        assertEquals(BigDecimal.valueOf(2000), dto.getSalaryPerWeek());
        assertEquals(locationId, dto.getLocationId());
    }

    @Test
    void givenValidUser_whenCreateCreatedUserResponse_thenReturnsCorrectResponse() {
        // Act
        CreatedUserResponse response = CreatedUserResponse.fromDomain(sampleUser);

        // Assert
        assertEquals(sampleUser.getId(), response.getId());
        assertEquals(sampleUser.getUsername(), response.getUsername());
        assertEquals(sampleUser.getEmail(), response.getEmail());
        assertEquals(sampleUser.getModule(), response.getModule());
        assertEquals(sampleUser.isFirstTime(), response.isFirstTime());
    }

    @Test
    void givenValidUser_whenCreateUserResponse_thenReturnsCorrectResponse() {
        // Act
        UserResponse response = UserResponse.fromDomain(sampleUser);

        // Assert
        assertEquals(sampleUser.getId(), response.getId());
        assertEquals(sampleUser.getUsername(), response.getUsername());
        assertEquals(sampleUser.getPassword(), response.getPassword());
        assertEquals(sampleUser.getEmail(), response.getEmail());
        assertEquals(sampleUser.getModule(), response.getModule());
        assertEquals(sampleUser.isFirstTime(), response.isFirstTime());
        assertEquals(sampleUser.isManager(), response.isManager());
        assertEquals(sampleUser.isEnabled(), response.isEnabled());
        assertEquals(sampleUser.getLocationId().toString(), response.getLocationId());
    }

    @Test
    void givenValidUser_whenCreateUserListResponse_thenReturnsCorrectResponse() {
        // Act
        UserListResponse response = UserListResponse.fromDomain(sampleUser);

        // Assert
        assertEquals(sampleUser.getId().toString(), response.getId());
        assertEquals(sampleUser.getUsername(), response.getUsername());
        assertEquals(sampleUser.getEmail(), response.getEmail());
        assertEquals(sampleUser.getModule(), response.getModule());
        assertEquals(sampleUser.isManager(), response.isManager());
        assertEquals(sampleUser.isEnabled(), response.isEnabled());
        assertEquals(sampleUser.getLocationId().toString(), response.getLocationId());
    }

    @Test
    void givenValidUpdateRequest_whenConvertToDomain_thenReturnsCorrectDto() {
        // Arrange
        UpdateUserEnabledStateRequest request = new UpdateUserEnabledStateRequest("test.user", true);

        // Act
        UpdateEnabledStateDto dto = request.toDomain();

        // Assert
        assertEquals(true, dto.enabled());
        assertEquals("test.user", dto.username());
    }
}