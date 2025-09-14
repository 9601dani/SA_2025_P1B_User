package com.danimo.user.information.application.usecases.createuserinformation;


import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.application.inputports.CreatingUserInformationInputPort;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.information.application.outputports.persistence.StoringUserInformationOutputPort;
import com.danimo.user.information.domain.UserInformation;
import org.springframework.validation.annotation.Validated;

@UseCase
@Validated
public class CreateUserInformationUseCase implements CreatingUserInformationInputPort {

    private FindingUserByUsernameOutputPort findingUserByUsernameOutputPort;
    private StoringUserInformationOutputPort storingUserInformationOutputPort;

    public CreateUserInformationUseCase(FindingUserByUsernameOutputPort findingUserByUsernameOutputPort, StoringUserInformationOutputPort storingUserInformationOutputPort) {
        this.findingUserByUsernameOutputPort = findingUserByUsernameOutputPort;
        this.storingUserInformationOutputPort = storingUserInformationOutputPort;
    }

    @Override
    public UserInformation createUserInformation(CreateUserInformationDto createUserInformationDto) throws UserNotFoundException {
        if(this.findingUserByUsernameOutputPort.findById(String.valueOf(createUserInformationDto.getUserId())).isEmpty()) {
            throw new UserNotFoundException("No existe el usuario");
        }

        UserInformation newUserInformation = createUserInformationDto.toDomain();

        return storingUserInformationOutputPort.save(newUserInformation);
    }
}
