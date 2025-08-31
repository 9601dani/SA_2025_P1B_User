package com.danimo.user.information.infrastructure.inputadapters.rest;

import com.danimo.user.common.infrastructure.annotations.WebAdapter;
import com.danimo.user.information.application.inputports.FindingUserInformationInputPort;
import com.danimo.user.information.application.inputports.UpdatingUserInformationInputPort;
import com.danimo.user.information.application.usecases.updateinformation.UpdateUserInformationDto;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.information.infrastructure.inputadapters.rest.dto.UserInformationResponse;
import com.danimo.user.information.infrastructure.inputadapters.rest.dto.UserInformationUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Information", description = "Operaciones relacionadas a la informacion de los usuarios")
@RestController
@RequestMapping("/v1/users/information")
@WebAdapter
public class UserInformationControllerAdapter {

    private final FindingUserInformationInputPort findingUserInformationInputPort;
    private final UpdatingUserInformationInputPort updatingUserInformationInputPort;

    @Autowired
    public UserInformationControllerAdapter(FindingUserInformationInputPort findingUserInformationInputPort,
                                            UpdatingUserInformationInputPort updatingUserInformationInputPort) {
        this.findingUserInformationInputPort = findingUserInformationInputPort;
        this.updatingUserInformationInputPort = updatingUserInformationInputPort;
    }

    @GetMapping("/{userId}")
    @Transactional
    public ResponseEntity<UserInformationResponse> findUserInformation(@PathVariable String userId) {

        UserInformation user = findingUserInformationInputPort.findUserInformationByUserId(userId);

        return ResponseEntity.ok(UserInformationResponse.fromDomain(user));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserInformationResponse> updateUserInformation(@RequestBody UserInformationUpdateRequest userInformationUpdateRequest) {
        UserInformation user = updatingUserInformationInputPort.updateUserInformation(UpdateUserInformationDto.toDomain(userInformationUpdateRequest));
        return  ResponseEntity.ok(UserInformationResponse.fromDomain(user));
    }
}
