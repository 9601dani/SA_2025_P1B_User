package com.danimo.user.information.infrastructure.inputadapters.rest;

import com.danimo.user.common.infrastructure.annotations.WebAdapter;
import com.danimo.user.information.application.inputports.FindingUserInformationInputPort;
import com.danimo.user.information.domain.UserInformation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Information", description = "Operaciones relacionadas a la informacion de los usuarios")
@RestController
@RequestMapping("/v1/users/information")
@WebAdapter
public class UserInformationControllerAdapter {

    private final FindingUserInformationInputPort findingUserInformationInputPort;

    @Autowired
    public UserInformationControllerAdapter(FindingUserInformationInputPort findingUserInformationInputPort) {
        this.findingUserInformationInputPort = findingUserInformationInputPort;
    }

    @GetMapping("/{userId}")
    @Transactional
    public UserInformation findUserInformation(@PathVariable String userId) {
        return findingUserInformationInputPort.findUserInformationByUserId(userId);

    }
}
