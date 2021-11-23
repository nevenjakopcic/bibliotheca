package hr.njakopcic.bibliotheca.service;

import hr.njakopcic.bibliotheca.model.User;
import hr.njakopcic.bibliotheca.model.UserRoles;
import hr.njakopcic.bibliotheca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getLoggedInUser() {
        return userRepository.findByUsername(getLoggedInUserUsername()).get();
    }

    private String getLoggedInUserUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void validateIsLoggedInUserOrAdmin(Long userId, String errorMessage) {
        if (isLoggedInUserOrAdmin(userId))
            return;

        throw new AccessDeniedException(errorMessage);
    }

    public void validateIsLoggedInUserOrAdmin(Long userId) {
        validateIsLoggedInUserOrAdmin(userId, "Access denied.");
    }

    public boolean isLoggedInUserOrAdmin(Long userId) {
        User loggedInUser = getLoggedInUser();
        return userId.equals(loggedInUser.getId()) || UserRoles.ROLE_ADMIN.equals(loggedInUser.getRole());
    }
}
