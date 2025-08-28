package com.danimo.user.user.application.usecases.createuser;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.common.application.exceptions.UserAlreadyExistsException;
import com.danimo.user.user.application.inputports.CreatingUserIputPort;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.application.outputports.persistence.StoringUserOutputPort;
import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.outputadapters.security.BCryptPasswordEncoderAdapter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@UseCase
@Validated
public class CreateUserUseCase implements CreatingUserIputPort {

    private final StoringUserOutputPort storingUserOutputPort;
    private final FindingUserByUsernameOutputPort findingUserByUsernameOutputPort;
    private final BCryptPasswordEncoderAdapter passwordEncoder;

    public CreateUserUseCase(StoringUserOutputPort storingUserOutputPort, FindingUserByUsernameOutputPort findingUserByUsernameOutputPort
    , BCryptPasswordEncoderAdapter passwordEncoder) {
        this.storingUserOutputPort = storingUserOutputPort;
        this.findingUserByUsernameOutputPort = findingUserByUsernameOutputPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User createUser(CreateUserDto userDto) {
        if(findingUserByUsernameOutputPort.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(userDto.getUsername());
        }

        if(findingUserByUsernameOutputPort.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Ya existe un usuario con Email",userDto.getEmail());
        }

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        User newUser = userDto.toDomainPass(hashedPassword);

        return storingUserOutputPort.save(newUser);
    }
}
