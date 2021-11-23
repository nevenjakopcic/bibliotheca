package hr.njakopcic.bibliotheca.controller;

import hr.njakopcic.bibliotheca.dto.ApiResponse;
import hr.njakopcic.bibliotheca.dto.request.ChangePasswordRequest;
import hr.njakopcic.bibliotheca.dto.request.ChangeUsernameRequest;
import hr.njakopcic.bibliotheca.service.UserService;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse> getAllUsers() {
        return new ResponseEntity<>(new ApiResponse(userService.getAll()), HttpStatus.OK);
    }

    @PutMapping("/change-username")
    public ResponseEntity<ApiResponse> changeUsername(@NotBlank @RequestBody final ChangeUsernameRequest request) {
        userService.changeUsername(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@NotBlank @RequestBody final ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.noContent().build();
    }
}
