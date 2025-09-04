package com.danimo.user.user.application.usecases.createuser;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.common.application.exceptions.LocationNotFoundException;
import com.danimo.user.common.application.exceptions.UserAlreadyExistsException;
import com.danimo.user.user.application.inputports.CreatingUserIputPort;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.information.application.outputports.persistence.StoringUserInformationOutputPort;
import com.danimo.user.user.application.outputports.persistence.StoringUserOutputPort;
import com.danimo.user.information.application.usecases.createuserinformation.CreateUserInformationDto;
import com.danimo.user.user.application.outputports.rest.ExistLocationOutputPort;
import com.danimo.user.user.domain.User;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.user.infrastrcture.outputadapters.security.BCryptPasswordEncoderAdapter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@UseCase
@Validated
public class CreateUserUseCase implements CreatingUserIputPort {

    private final StoringUserOutputPort storingUserOutputPort;
    private final FindingUserByUsernameOutputPort findingUserByUsernameOutputPort;
    private final BCryptPasswordEncoderAdapter passwordEncoder;
    private final StoringUserInformationOutputPort storingUserInformationOutputPort;
    private final ExistLocationOutputPort existLocationOutputPort;

    public CreateUserUseCase(StoringUserOutputPort storingUserOutputPort,
                             FindingUserByUsernameOutputPort findingUserByUsernameOutputPort,
                             BCryptPasswordEncoderAdapter passwordEncoder,
                             StoringUserInformationOutputPort storingUserInformationOutputPort,
                             ExistLocationOutputPort existLocationOutputPort) {
        this.storingUserOutputPort = storingUserOutputPort;
        this.findingUserByUsernameOutputPort = findingUserByUsernameOutputPort;
        this.passwordEncoder = passwordEncoder;
        this.storingUserInformationOutputPort = storingUserInformationOutputPort;
        this.existLocationOutputPort = existLocationOutputPort;
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
        System.out.println("MANDARE " + userDto.getLocationId());

        if(!existLocationOutputPort.existLocation(userDto.getLocationId())){
            throw new LocationNotFoundException("No existe la location con el id: "+userDto.getLocationId());
        }

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        User newUser = userDto.toDomainPass(hashedPassword);

        User userSaved = storingUserOutputPort.save(newUser);

        if(userSaved != null) {
            UserInformation newUserInformation = new CreateUserInformationDto(userDto.getSalaryPerWeek(),userSaved.getId()).toDomain();
            storingUserInformationOutputPort.save(newUserInformation);
        }

        return userSaved;
    }
}
