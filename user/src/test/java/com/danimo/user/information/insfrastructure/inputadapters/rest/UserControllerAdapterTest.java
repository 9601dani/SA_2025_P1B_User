package com.danimo.user.information.insfrastructure.inputadapters.rest;

import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.user.application.inputports.CreatingUserIputPort;
import com.danimo.user.user.application.inputports.FindingUserByUsernameInputPort;
import com.danimo.user.user.application.inputports.UpdatingEnabledStateInputPort;
import com.danimo.user.user.application.outputports.persistence.FindingAllEmployesOutputPort;
import com.danimo.user.user.application.usecases.createuser.CreateUserDto;
import com.danimo.user.user.application.usecases.updateenabled.UpdateEnabledStateDto;
import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.inputadapters.rest.UserControllerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User(UUID.randomUUID(), "john.doe", "pass", "john@example.com",
                "module1", true, false, true, UUID.randomUUID());
    }

    @Test
    void givenValidRequest_whenCreateUser_thenReturnsCreated() throws Exception {
        CreateUserDto dto = new CreateUserDto(
                sampleUser.getUsername(),
                "password",
                sampleUser.getEmail(),
                sampleUser.getModule(),
                BigDecimal.valueOf(100),
                false,
                sampleUser.getLocationId()
        );

        when(creatingUserIputPort.createUser(any(CreateUserDto.class))).thenReturn(sampleUser);

        String requestJson = "{ \"username\": \"john.doe\", \"password\": \"pass123\", \"email\": \"john@example.com\", \"module\": \"module1\", \"salaryPerWeek\": 100, \"isAdmin\": false, \"locationId\": \"" + sampleUser.getLocationId() + "\" }";

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(sampleUser.getUsername())))
                .andExpect(jsonPath("$.email", is(sampleUser.getEmail())));

        ArgumentCaptor<CreateUserDto> captor = ArgumentCaptor.forClass(CreateUserDto.class);
        verify(creatingUserIputPort).createUser(captor.capture());
        assertEquals(dto.getUsername(), captor.getValue().getUsername());
    }

    @Test
    void givenExistingUser_whenFindingUser_thenReturnsUserResponse() throws Exception {
        when(findingUserByUsernameInputPort.findByUsername("john.doe")).thenReturn(sampleUser);

        mockMvc.perform(get("/v1/users/john.doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(sampleUser.getUsername())))
                .andExpect(jsonPath("$.email", is(sampleUser.getEmail())));
    }

    @Test
    void givenUsersExist_whenFindAllUsers_thenReturnsList() throws Exception {
        List<User> users = Arrays.asList(sampleUser);
        when(findingAllEmployes.findAllEmployes()).thenReturn(users);

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is(sampleUser.getUsername())));
    }

    @Test
    void givenUserEnabledTrue_whenUpdateEnabled_thenReturnsUserListResponse() throws Exception {
        sampleUser.setEnabled(true);
        UpdateEnabledStateDto dto = new UpdateEnabledStateDto(true, "john.doe");
        when(updatingEnabledStateInputPort.updateEnabledState(any(UpdateEnabledStateDto.class))).thenReturn(sampleUser);

        String requestJson = "{ \"username\": \"john.doe\", \"enabled\": true }";

        mockMvc.perform(put("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(sampleUser.getUsername())))
                .andExpect(jsonPath("$.enabled", is(true)));
    }

    @Test
    void givenUserDisabled_whenUpdateEnabled_thenThrowsInactiveAccountException() throws Exception {
        sampleUser.setEnabled(false);
        UpdateEnabledStateDto dto = new UpdateEnabledStateDto(false, "john.doe");
        when(updatingEnabledStateInputPort.updateEnabledState(any(UpdateEnabledStateDto.class))).thenReturn(sampleUser);

        String requestJson = "{ \"username\": \"john.doe\", \"enabled\": false }";

        mockMvc.perform(put("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isLocked());
    }

    @Test
    void givenUserExists_whenCheckExistence_thenReturnsOk() throws Exception {
        when(findingUserByUsernameInputPort.findByUsername("john.doe")).thenReturn(sampleUser);

        mockMvc.perform(head("/v1/users/check/john.doe"))
                .andExpect(status().isOk());
    }

    @Test
    void givenUserDoesNotExist_whenCheckExistence_thenReturnsNotFound() throws Exception {
        when(findingUserByUsernameInputPort.findByUsername("unknown")).thenThrow(new UserNotFoundException("unknown"));

        mockMvc.perform(head("/v1/users/check/unknown"))
                .andExpect(status().isNotFound());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PlatformTransactionManager transactionManager() {
            return mock(PlatformTransactionManager.class);
        }
    }
}