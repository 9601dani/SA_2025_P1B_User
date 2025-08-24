package com.danimo.user.infrastrcture.inputadapters.rest;

import com.danimo.common.infrastructure.annotations.WebAdapter;
import com.danimo.user.application.inputports.FindingUserByUsernameInputPort;
import com.danimo.user.domain.User;
import com.danimo.user.infrastrcture.inputadapters.rest.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "Operaciones relacionadas a los usuarios")
@RestController
@RequestMapping("/v1/users")
@WebAdapter
public class UserControllerAdapter {

    private final FindingUserByUsernameInputPort findingUserByUsernameInputPort;

    @Autowired
    public UserControllerAdapter(FindingUserByUsernameInputPort findingUserByUsernameInputPort) {
        this.findingUserByUsernameInputPort = findingUserByUsernameInputPort;
    }


    @Operation(
            summary = "Buscar usuario por nombre de usuario",
            description = "Devuelve la información del usuario correspondiente al username dado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> findingUserByUsername(@PathVariable String username) {
        User user = findingUserByUsernameInputPort.findByUsername(username);
        return ResponseEntity.ok(UserResponse.fromDomain(user));
    }

}
