package hr.njakopcic.bibliotheca.controller;

import hr.njakopcic.bibliotheca.dto.ApiResponse;
import hr.njakopcic.bibliotheca.service.MembershipService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/membership")
@AllArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getMembershipByUserId(@PathVariable final Long userId) {
        return new ResponseEntity<>(new ApiResponse(membershipService.getMembership(userId)), HttpStatus.OK);
    }

    @GetMapping()
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse> getAllMemberships() {
        return new ResponseEntity<>(new ApiResponse(membershipService.getAllMemberships()), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse> extendMembership(@PathVariable final Long userId) {
        return new ResponseEntity<>(new ApiResponse(membershipService.extendMembership(userId, 1L)), HttpStatus.OK);
    }
}
