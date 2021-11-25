package hr.njakopcic.bibliotheca.service;

import hr.njakopcic.bibliotheca.dto.response.MembershipDto;
import hr.njakopcic.bibliotheca.mapper.MembershipDtoMapper;
import hr.njakopcic.bibliotheca.model.Membership;
import hr.njakopcic.bibliotheca.repository.MembershipRepository;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipDto extendMembership(Long userId, Long monthsToAdd) {
        Membership membership = membershipRepository.findByUserId(userId);
        Membership updatedMembership = Membership.builder()
            .id(membership.getId())
            .dateCreated(membership.getDateCreated())
            .lastUpdated(LocalDate.now())
            .validUntil(membership.getValidUntil().plusMonths(monthsToAdd))
            .build();

        updatedMembership = membershipRepository.save(updatedMembership);

        return MembershipDtoMapper.map(updatedMembership);
    }
}
