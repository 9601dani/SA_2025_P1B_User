package com.danimo.user.user.infrastrcture.inputadapters.rest;

import com.danimo.user.common.infrastructure.annotations.WebAdapter;
import com.danimo.user.information.application.inputports.UpdatingUserInformationInputPort;
import com.danimo.user.user.application.inputports.CreatingUserIputPort;
import com.danimo.user.user.application.inputports.FindingUserByUsernameInputPort;
import com.danimo.user.user.application.inputports.UpdatingEnabledStateInputPort;
import com.danimo.user.user.application.outputports.persistence.FindingAllEmployesOutputPort;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.application.outputports.persistence.UpdatingEnabledStateOutputPort;
import com.danimo.user.user.application.usecases.createuser.CreateUserDto;
import com.danimo.user.user.application.usecases.updateenabled.UpdateEnabledStateDto;
import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.inputadapters.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Users", description = "Operaciones relacionadas a los usuarios")
@RestController
@RequestMapping("/v1/users")
@WebAdapter
public class UserControllerAdapter {

    private final FindingUserByUsernameInputPort findingUserByUsernameInputPort;
    private final CreatingUserIputPort creatingUserIputPort;
    private final FindingAllEmployesOutputPort findingAllEmployes;
    private final UpdatingEnabledStateInputPort updatingEnabledStateInputPort ;

    @Autowired
    public UserControllerAdapter(FindingUserByUsernameInputPort findingUserByUsernameInputPort, CreatingUserIputPort creatingUserIputPort
    , FindingAllEmployesOutputPort findingAllEmployes, UpdatingEnabledStateInputPort updatingEnabledStateInputPort) {
        this.findingUserByUsernameInputPort = findingUserByUsernameInputPort;
        this.creatingUserIputPort = creatingUserIputPort;
        this.findingAllEmployes = findingAllEmployes;
        this.updatingEnabledStateInputPort = updatingEnabledStateInputPort;
    }

    @Operation(
            summary = "Crear usuario",
            description = "Crea un usuario y devuelve la información del nuevo recurso."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Datos para crear el usuario",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreateUserRequest.class),
                    examples = @ExampleObject(
                            name = "Ejemplo de solicitud",
                            value = "{\n" +
                                    "  \"username\": \"juan.perez\",\n" +
                                    "  \"password\": \"S3gura!123\",\n" +
                                    "  \"email\": \"juan.perez@example.com\"\n" +
                                    "}"
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado",
                    headers = {
                            @Header(name = "Location", description = "URI del recurso creado")
                    },
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatedUserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflicto (username o email ya existente)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno",
                    content = @Content
            )
    })
    @PostMapping
    @Transactional
    public ResponseEntity<CreatedUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserDto objectAdapterFromRestToDomain = createUserRequest.toDomain();

        User user = creatingUserIputPort.createUser(objectAdapterFromRestToDomain);

        CreatedUserResponse createdUserResponse = CreatedUserResponse.fromDomain(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserResponse);

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

    @Operation(
            summary = "Listar todos los usuarios",
            description = "Devuelve la información de todos los usuarios (empleaados)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
            @ApiResponse(responseCode = "404", description = "Usuarios no encontrados")
    })
    @GetMapping("")
    public ResponseEntity<List<UserListResponse>> findAllUsers() {
        List<User> users = this.findingAllEmployes.findAllEmployes();
        return ResponseEntity.ok(users
                .stream()
                .map(UserListResponse::fromDomain)
                .toList());
    }

    @Operation(
            summary = "Cambiar estado del enabled de usuario",
            description = "No devuelve nada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo"),
            @ApiResponse(responseCode = "404", description = "No se actualizo")
    })
    @PutMapping
    public ResponseEntity<UserListResponse> updateEnabledState(@RequestBody UpdateUserEnabledStateRequest dto){
        User user = this.updatingEnabledStateInputPort.updateEnabledState(dto.toDomain());

        return ResponseEntity.ok(UserListResponse.fromDomain(user));
    }



}
