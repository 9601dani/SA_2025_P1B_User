package com.danimo.user.information.application.usecases.updateinformation;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.common.application.exceptions.CredentialsDoesntSame;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.application.inputports.UpdatingUserInformationInputPort;
import com.danimo.user.information.application.outputports.persistence.FindingUserInformationOutputPort;
import com.danimo.user.information.application.outputports.persistence.StoringUserInformationOutputPort;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.application.outputports.persistence.StoringUserOutputPort;
import com.danimo.user.user.application.outputports.security.PasswordEncoderPort;
import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.outputadapters.security.BCryptPasswordEncoderAdapter;
import org.springframework.validation.annotation.Validated;



@UseCase
@Validated
public class UpdateUserInformationUseCase implements UpdatingUserInformationInputPort {

    private final FindingUserInformationOutputPort findingUserInformationOutputPort;
    private final StoringUserInformationOutputPort storingUserInformationOutputPort;
    private final StoringUserOutputPort storingUserOutputPort;
    private final FindingUserByUsernameOutputPort findingUserByUsernameOutputPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UpdateUserInformationUseCase(FindingUserInformationOutputPort findingUserInformationOutputPort, StoringUserInformationOutputPort storingUserInformationOutputPort,
                                        StoringUserOutputPort storingUserOutputPort, FindingUserByUsernameOutputPort findingUserByUsernameOutputPort,
                                        PasswordEncoderPort passwordEncoderPort) {
        this.findingUserInformationOutputPort = findingUserInformationOutputPort;
        this.storingUserInformationOutputPort = storingUserInformationOutputPort;
        this.storingUserOutputPort = storingUserOutputPort;
        this.findingUserByUsernameOutputPort = findingUserByUsernameOutputPort;
        this.passwordEncoderPort = passwordEncoderPort;

    }

    @Override
    public UserInformation updateUserInformation(UpdateUserInformationDto updateUserInformationDto) {
        UserInformation userInformation = this.findingUserInformationOutputPort
                .findUserInformationByUserId(updateUserInformationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Information user not found"));



        User user = this.findingUserByUsernameOutputPort.findById(userInformation.getUser().getId().toString())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        boolean firstTime = userInformation.getUser().isFirstTime();

        if(userInformation.getUser().isFirstTime()){
           firstTime = false;
        }

        String newPassword = getNewPassword(updateUserInformationDto, user);

        user.setPassword(newPassword);
        user.setFirstTime(firstTime);

        this.storingUserOutputPort.save(user);

        if(updateUserInformationDto.getName() != null){
            userInformation.setName(updateUserInformationDto.getName());
        }

        if(updateUserInformationDto.getLastName() != null){
            userInformation.setLastName(updateUserInformationDto.getLastName());
        }

        if(updateUserInformationDto.getBirthdate() != null){
            userInformation.setBirthdate(updateUserInformationDto.getBirthdate());
        }

        return this.storingUserInformationOutputPort.save(userInformation);
    }

    private String getNewPassword(UpdateUserInformationDto updateUserInformationDto, User user) {
        String newPassword = user.getPassword();

        if (!passwordEncoderPort.matches(updateUserInformationDto.getOldPassword(), user.getPassword())) {
            throw new CredentialsDoesntSame("La contrasena anterior es incorrecta");
        }

        if (updateUserInformationDto.getNewPassword() != null) {
            newPassword = passwordEncoderPort.encode(updateUserInformationDto.getNewPassword());
        }

        return newPassword;
    }


}
