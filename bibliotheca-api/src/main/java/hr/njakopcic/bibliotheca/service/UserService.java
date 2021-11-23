package hr.njakopcic.bibliotheca.service;

import hr.njakopcic.bibliotheca.dto.request.ChangePasswordRequest;
import hr.njakopcic.bibliotheca.dto.request.ChangeUsernameRequest;
import hr.njakopcic.bibliotheca.dto.request.RegisterUserRequest;
import hr.njakopcic.bibliotheca.dto.response.UserDto;
import hr.njakopcic.bibliotheca.exception.EmailAlreadyTakenException;
import hr.njakopcic.bibliotheca.exception.UsernameAlreadyTakenException;
import hr.njakopcic.bibliotheca.mapper.UserDtoMapper;
import hr.njakopcic.bibliotheca.model.Membership;
import hr.njakopcic.bibliotheca.model.User;
import hr.njakopcic.bibliotheca.model.UserRoles;
import hr.njakopcic.bibliotheca.repository.MembershipRepository;
import hr.njakopcic.bibliotheca.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();

        // TODO: also return memberships
        return users.stream().map(UserDtoMapper::map).collect(Collectors.toList());
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(RegisterUserRequest registerRequest) {

        Membership membership = Membership.builder()
            .dateCreated(LocalDate.now())
            .lastUpdated(LocalDate.now())
            .validUntil(LocalDate.now().plusMonths(1))
            .build();
        membership = membershipRepository.save(membership);

        User user = User.builder()
            .username(registerRequest.getUsername())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .email(registerRequest.getEmail())
            .role(UserRoles.ROLE_USER)
            .membership(membership)
            .disabled(false).build();

        if (getUserByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException("This username is already taken.");
        }

        if (getUserByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException("This email is already taken.");
        }

        userRepository.save(user);
    }

    public void changeUsername(ChangeUsernameRequest request) {
        User user = currentUserService.getLoggedInUser();

        user.setUsername(request.getNewUsername());
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyTakenException("This username is already taken.", e);
        }
    }

    public void changePassword(ChangePasswordRequest request) {
        User user = currentUserService.getLoggedInUser();

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
