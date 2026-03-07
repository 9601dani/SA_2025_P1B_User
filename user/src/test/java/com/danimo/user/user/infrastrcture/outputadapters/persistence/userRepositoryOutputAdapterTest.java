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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class userRepositoryOutputAdapterTest {

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

        sampleUser = new User(userId, "john.doe", "password123", "john@example.com",
                true, true, locationId);

        sampleUserDbEntity = new UserDbEntity(userId, "john.doe", "password123", "john@example.com",
                 true, false, locationId,new ArrayList<>());
    }

    @Test
    void givenExistingUsername_whenFindByUsername_thenReturnUser() {
        // Arrange
        String username = "john.doe";
        when(userDbEntityJpaRepository.findByUsername(username)).thenReturn(Optional.of(sampleUserDbEntity));
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        // Act
        Optional<User> result = userRepositoryOutputAdapter.findByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleUser.getUsername(), result.get().getUsername());
        assertEquals(sampleUser.getEmail(), result.get().getEmail());
        verify(userDbEntityJpaRepository).findByUsername(username);
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenNonExistingUsername_whenFindByUsername_thenReturnEmpty() {
        // Arrange
        String username = "nonexistent";
        when(userDbEntityJpaRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepositoryOutputAdapter.findByUsername(username);

        // Assert
        assertFalse(result.isPresent());
        verify(userDbEntityJpaRepository).findByUsername(username);
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenExistingEmail_whenFindByEmail_thenReturnUser() {
        // Arrange
        String email = "john@example.com";
        when(userDbEntityJpaRepository.findByEmail(email)).thenReturn(Optional.of(sampleUserDbEntity));
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        // Act
        Optional<User> result = userRepositoryOutputAdapter.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleUser.getEmail(), result.get().getEmail());
        assertEquals(sampleUser.getUsername(), result.get().getUsername());
        verify(userDbEntityJpaRepository).findByEmail(email);
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenNonExistingEmail_whenFindByEmail_thenReturnEmpty() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userDbEntityJpaRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepositoryOutputAdapter.findByEmail(email);

        // Assert
        assertFalse(result.isPresent());
        verify(userDbEntityJpaRepository).findByEmail(email);
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenExistingId_whenFindById_thenReturnUser() {
        // Arrange
        String id = userId.toString();
        when(userDbEntityJpaRepository.findById(userId)).thenReturn(Optional.of(sampleUserDbEntity));
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        // Act
        Optional<User> result = userRepositoryOutputAdapter.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleUser.getId(), result.get().getId());
        assertEquals(sampleUser.getUsername(), result.get().getUsername());
        verify(userDbEntityJpaRepository).findById(userId);
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenNonExistingId_whenFindById_thenReturnEmpty() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        String id = nonExistentId.toString();
        when(userDbEntityJpaRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepositoryOutputAdapter.findById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(userDbEntityJpaRepository).findById(nonExistentId);
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenValidUser_whenSave_thenReturnSavedUser() {
        // Arrange
        when(userPersistenceMapper.toDbEntity(sampleUser)).thenReturn(sampleUserDbEntity);
        when(userDbEntityJpaRepository.save(sampleUserDbEntity)).thenReturn(sampleUserDbEntity);
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        // Act
        User result = userRepositoryOutputAdapter.save(sampleUser);

        // Assert
        assertEquals(sampleUser.getId(), result.getId());
        assertEquals(sampleUser.getUsername(), result.getUsername());
        assertEquals(sampleUser.getEmail(), result.getEmail());
        verify(userPersistenceMapper).toDbEntity(sampleUser);
        verify(userDbEntityJpaRepository).save(sampleUserDbEntity);
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenValidId_whenDeleteById_thenCallRepositoryDelete() {
        // Arrange
        String id = userId.toString();

        // Act
        userRepositoryOutputAdapter.deleteById(id);

        // Assert
        verify(userDbEntityJpaRepository).deleteById(userId);
    }

    @Test
    void givenUsersExist_whenFindAllEmployees_thenReturnUsersList() {
        // Arrange
        UserDbEntity secondUserDbEntity = new UserDbEntity(UUID.randomUUID(), "jane.doe", "pass456",
                "jane@example.com", false, true, UUID.randomUUID(), new ArrayList<>());

        User secondUser = new User(UUID.randomUUID(), "jane.doe", "pass456", "jane@example.com",
                 false, true, UUID.randomUUID());

        List<UserDbEntity> userDbEntities = Arrays.asList(sampleUserDbEntity, secondUserDbEntity);

        when(userDbEntityJpaRepository.findAll()).thenReturn(userDbEntities);
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);
        when(userPersistenceMapper.toDomain(secondUserDbEntity)).thenReturn(secondUser);

        // Act
        List<User> result = userRepositoryOutputAdapter.findAllEmployes();

        // Assert
        assertEquals(2, result.size());
        assertEquals(sampleUser.getUsername(), result.get(0).getUsername());
        assertEquals(secondUser.getUsername(), result.get(1).getUsername());
        verify(userDbEntityJpaRepository).findAll();
        verify(userPersistenceMapper, times(2)).toDomain(any(UserDbEntity.class));
    }

    @Test
    void givenNoUsers_whenFindAllEmployees_thenReturnEmptyList() {
        // Arrange
        when(userDbEntityJpaRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<User> result = userRepositoryOutputAdapter.findAllEmployes();

        // Assert
        assertTrue(result.isEmpty());
        verify(userDbEntityJpaRepository).findAll();
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenValidDto_whenUpdateEnabledState_thenReturnUser() {
        // Arrange
        UpdateEnabledStateDto dto = new UpdateEnabledStateDto(true, "john.doe");
        when(userDbEntityJpaRepository.findByUsername(dto.username())).thenReturn(Optional.of(sampleUserDbEntity));
        when(userPersistenceMapper.toDomain(sampleUserDbEntity)).thenReturn(sampleUser);

        // Act
        Optional<User> result = userRepositoryOutputAdapter.updateEnabledState(dto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleUser.getUsername(), result.get().getUsername());
        assertEquals(sampleUser.isEnabled(), result.get().isEnabled());
        verify(userDbEntityJpaRepository).findByUsername(dto.username());
        verify(userPersistenceMapper).toDomain(sampleUserDbEntity);
    }

    @Test
    void givenDtoWithNonExistingUser_whenUpdateEnabledState_thenReturnEmpty() {
        // Arrange
        UpdateEnabledStateDto dto = new UpdateEnabledStateDto(true, "nonexistent");
        when(userDbEntityJpaRepository.findByUsername(dto.username())).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepositoryOutputAdapter.updateEnabledState(dto);

        // Assert
        assertFalse(result.isPresent());
        verify(userDbEntityJpaRepository).findByUsername(dto.username());
        verify(userPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void givenValidUserDbEntity_whenToDomain_thenReturnUser() {
        // Arrange
        UserPersistenceMapper realMapper = new UserPersistenceMapper();

        // Act
        User result = realMapper.toDomain(sampleUserDbEntity);

        // Assert
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
        // Arrange
        UserPersistenceMapper realMapper = new UserPersistenceMapper();

        // Act
        User result = realMapper.toDomain(null);

        // Assert
        assertNull(result);
    }

    @Test
    void givenValidUser_whenToDbEntity_thenReturnUserDbEntity() {
        // Arrange
        UserPersistenceMapper realMapper = new UserPersistenceMapper();

        // Act
        UserDbEntity result = realMapper.toDbEntity(sampleUser);

        // Assert
        assertEquals(sampleUser.getId(), result.getId());
        assertEquals(sampleUser.getUsername(), result.getUsername());
        assertEquals(sampleUser.getPassword(), result.getPassword());
        assertEquals(sampleUser.getEmail(), result.getEmail());
        assertEquals(sampleUser.isFirstTime(), result.isFirstTime());
        assertEquals(sampleUser.isEnabled(), result.isEnabled());
        assertEquals(sampleUser.getLocationId(), result.getLocationId());
    }

    @Test
    void givenNullUser_whenToDbEntity_thenReturnNull() {
        // Arrange
        UserPersistenceMapper realMapper = new UserPersistenceMapper();

        // Act
        UserDbEntity result = realMapper.toDbEntity(null);

        // Assert
        assertNull(result);
    }
}