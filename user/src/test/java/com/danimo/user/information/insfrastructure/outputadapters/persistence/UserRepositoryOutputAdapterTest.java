package com.danimo.user.user.infrastrcture.outputadapters.persistence;

import com.danimo.user.user.application.usecases.updateenabled.UpdateEnabledStateDto;
import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.UserDbEntity;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.mapper.UserPersistenceMapper;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.repository.UserDbEntityJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryOutputAdapterTest {

    @Mock
    private UserDbEntityJpaRepository userDbEntityJpaRepository;

    @Mock
    private UserPersistenceMapper userPersistenceMapper;

    @InjectMocks
    private UserRepositoryOutputAdapter userRepositoryOutputAdapter;

    private User sampleUser;
    private UserDbEntity sampleUserDbEntity;
    private UUID userId;
    private UUID locationId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        locationId = UUID.randomUUID();

        sampleUser = new User(
                userId,
                "john.doe",
                "password123",
                "john@example.com",
                true,
                true,
                locationId
        );

        sampleUserDbEntity = new UserDbEntity(
                userId,
                "john.doe",
                "password123",
                "john@example.com",
                true,
                true,
                locationId,
                new ArrayList<>()
        );
    }

    @Test
    void givenExistingUsername_whenFindByUsername_thenReturnUser() {
        String username = "john.doe";
        when(userDbEntityJpaRepository.findByUsername(username)).thenReturn(Optional.of(sampleUserDbEntity));
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        Optional<User> result = userRepositoryOutputAdapter.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(sampleUser.getUsername(), result.get().getUsername());
        assertEquals(sampleUser.getEmail(), result.get().getEmail());
        verify(userDbEntityJpaRepository).findByUsername(username);
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenNonExistingUsername_whenFindByUsername_thenReturnEmpty() {
        String username = "nonexistent";
        when(userDbEntityJpaRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<User> result = userRepositoryOutputAdapter.findByUsername(username);

        assertFalse(result.isPresent());
        verify(userDbEntityJpaRepository).findByUsername(username);
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenExistingEmail_whenFindByEmail_thenReturnUser() {
        String email = "john@example.com";
        when(userDbEntityJpaRepository.findByEmail(email)).thenReturn(Optional.of(sampleUserDbEntity));
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        Optional<User> result = userRepositoryOutputAdapter.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(sampleUser.getEmail(), result.get().getEmail());
        assertEquals(sampleUser.getUsername(), result.get().getUsername());
        verify(userDbEntityJpaRepository).findByEmail(email);
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenNonExistingEmail_whenFindByEmail_thenReturnEmpty() {
        String email = "nonexistent@example.com";
        when(userDbEntityJpaRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userRepositoryOutputAdapter.findByEmail(email);

        assertFalse(result.isPresent());
        verify(userDbEntityJpaRepository).findByEmail(email);
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenExistingId_whenFindById_thenReturnUser() {
        String id = userId.toString();
        when(userDbEntityJpaRepository.findById(userId)).thenReturn(Optional.of(sampleUserDbEntity));
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        Optional<User> result = userRepositoryOutputAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(sampleUser.getId(), result.get().getId());
        assertEquals(sampleUser.getUsername(), result.get().getUsername());
        verify(userDbEntityJpaRepository).findById(userId);
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenNonExistingId_whenFindById_thenReturnEmpty() {
        UUID nonExistentId = UUID.randomUUID();
        String id = nonExistentId.toString();
        when(userDbEntityJpaRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<User> result = userRepositoryOutputAdapter.findById(id);

        assertFalse(result.isPresent());
        verify(userDbEntityJpaRepository).findById(nonExistentId);
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenValidUser_whenSave_thenReturnSavedUser() {
        when(userPersistenceMapper.toDbEntity(sampleUser)).thenReturn(sampleUserDbEntity);
        when(userDbEntityJpaRepository.save(sampleUserDbEntity)).thenReturn(sampleUserDbEntity);
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        User result = userRepositoryOutputAdapter.save(sampleUser);

        assertEquals(sampleUser.getId(), result.getId());
        assertEquals(sampleUser.getUsername(), result.getUsername());
        assertEquals(sampleUser.getEmail(), result.getEmail());
        verify(userPersistenceMapper).toDbEntity(sampleUser);
        verify(userDbEntityJpaRepository).save(sampleUserDbEntity);
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenValidId_whenDeleteById_thenCallRepositoryDelete() {
        String id = userId.toString();

        userRepositoryOutputAdapter.deleteById(id);

        verify(userDbEntityJpaRepository).deleteById(userId);
    }

    @Test
    void givenUsersExist_whenFindAllEmployees_thenReturnUsersList() {
        UUID secondUserId = UUID.randomUUID();
        UUID secondLocationId = UUID.randomUUID();

        UserDbEntity secondUserDbEntity = new UserDbEntity(
                secondUserId,
                "jane.doe",
                "pass456",
                "jane@example.com",
                false,
                true,
                secondLocationId,
                new ArrayList<>()
        );

        User secondUser = new User(
                secondUserId,
                "jane.doe",
                "pass456",
                "jane@example.com",
                false,
                true,
                secondLocationId
        );

        List<UserDbEntity> userDbEntities = Arrays.asList(sampleUserDbEntity, secondUserDbEntity);

        when(userDbEntityJpaRepository.findAll()).thenReturn(userDbEntities);
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);
        when(userPersistenceMapper.toDomain(secondUserDbEntity)).thenReturn(secondUser);

        List<User> result = userRepositoryOutputAdapter.findAllEmployes();

        assertEquals(2, result.size());
        assertEquals(sampleUser.getUsername(), result.get(0).getUsername());
        assertEquals(secondUser.getUsername(), result.get(1).getUsername());
        verify(userDbEntityJpaRepository).findAll();
        verify(userPersistenceMapper, times(2)).toDomain(any(UserDbEntity.class));
    }

    @Test
    void givenNoUsers_whenFindAllEmployees_thenReturnEmptyList() {
        when(userDbEntityJpaRepository.findAll()).thenReturn(List.of());

        List<User> result = userRepositoryOutputAdapter.findAllEmployes();

        assertTrue(result.isEmpty());
        verify(userDbEntityJpaRepository).findAll();
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenValidDto_whenUpdateEnabledState_thenReturnUser() {
        UpdateEnabledStateDto dto = new UpdateEnabledStateDto(true, "john.doe");
        UserDbEntity updatedEntity = new UserDbEntity(
                sampleUserDbEntity.getId(),
                sampleUserDbEntity.getUsername(),
                sampleUserDbEntity.getPassword(),
                sampleUserDbEntity.getEmail(),
                sampleUserDbEntity.isFirstTime(),
                true,
                sampleUserDbEntity.getLocationId(),
                sampleUserDbEntity.getRoles()
        );

        User updatedUser = new User(
                sampleUser.getId(),
                sampleUser.getUsername(),
                sampleUser.getPassword(),
                sampleUser.getEmail(),
                sampleUser.isFirstTime(),
                true,
                sampleUser.getLocationId()
        );

        when(userDbEntityJpaRepository.findByUsername(dto.username())).thenReturn(Optional.of(sampleUserDbEntity));
        when(userDbEntityJpaRepository.save(any(UserDbEntity.class))).thenReturn(updatedEntity);
        when(userPersistenceMapper.toDomain(updatedEntity)).thenReturn(updatedUser);

        Optional<User> result = userRepositoryOutputAdapter.updateEnabledState(dto);

        assertTrue(result.isPresent());
        assertEquals(updatedUser.getUsername(), result.get().getUsername());
        assertTrue(result.get().isEnabled());
        verify(userDbEntityJpaRepository).findByUsername(dto.username());
        verify(userDbEntityJpaRepository).save(any(UserDbEntity.class));
        verify(userPersistenceMapper).toDomain(updatedEntity);
    }

    @Test
    void givenDtoWithNonExistingUser_whenUpdateEnabledState_thenReturnEmpty() {
        UpdateEnabledStateDto dto = new UpdateEnabledStateDto(true, "nonexistent");
        when(userDbEntityJpaRepository.findByUsername(dto.username())).thenReturn(Optional.empty());

        Optional<User> result = userRepositoryOutputAdapter.updateEnabledState(dto);

        assertFalse(result.isPresent());
        verify(userDbEntityJpaRepository).findByUsername(dto.username());
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenValidUserDbEntity_whenToDomain_thenReturnUser() {
        UserPersistenceMapper realMapper = new UserPersistenceMapper();

        User result = realMapper.toDomain(sampleUserDbEntity);

        assertEquals(sampleUserDbEntity.getId(), result.getId());
        assertEquals(sampleUserDbEntity.getUsername(), result.getUsername());
        assertEquals(sampleUserDbEntity.getPassword(), result.getPassword());
        assertEquals(sampleUserDbEntity.getEmail(), result.getEmail());
        assertEquals(sampleUserDbEntity.isFirstTime(), result.isFirstTime());
        assertEquals(sampleUserDbEntity.isEnabled(), result.isEnabled());
        assertEquals(sampleUserDbEntity.getLocationId(), result.getLocationId());
    }

    @Test
    void givenNullUserDbEntity_whenToDomain_thenReturnNull() {
        UserPersistenceMapper realMapper = new UserPersistenceMapper();

        User result = realMapper.toDomain(null);

        assertNull(result);
    }

    @Test
    void givenValidUser_whenToDbEntity_thenReturnUserDbEntity() {
        UserPersistenceMapper realMapper = new UserPersistenceMapper();

        UserDbEntity result = realMapper.toDbEntity(sampleUser);

        assertEquals(sampleUser.getId(), result.getId());
        assertEquals(sampleUser.getUsername(), result.getUsername());
        assertEquals(sampleUser.getPassword(), result.getPassword());
        assertEquals(sampleUser.getEmail(), result.getEmail());
        assertEquals(sampleUser.isFirstTime(), result.isFirstTime());
        assertEquals(sampleUser.isEnabled(), result.isEnabled());
        assertEquals(sampleUser.getLocationId(), result.getLocationId());
        assertNotNull(result.getRoles());
        assertTrue(result.getRoles().isEmpty());
    }

    @Test
    void givenNullUser_whenToDbEntity_thenReturnNull() {
        UserPersistenceMapper realMapper = new UserPersistenceMapper();

        UserDbEntity result = realMapper.toDbEntity(null);

        assertNull(result);
    }
}