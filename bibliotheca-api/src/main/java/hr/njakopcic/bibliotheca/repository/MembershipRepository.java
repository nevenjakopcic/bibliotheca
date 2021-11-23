package hr.njakopcic.bibliotheca.repository;

import hr.njakopcic.bibliotheca.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

}
