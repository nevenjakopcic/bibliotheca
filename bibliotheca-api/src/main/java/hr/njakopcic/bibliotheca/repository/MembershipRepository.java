package hr.njakopcic.bibliotheca.repository;

import hr.njakopcic.bibliotheca.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query(value = "SELECT m.* " +
                   "FROM person p " +
                   "LEFT JOIN membership m ON p.membership_id = m.id " +
                   "WHERE p.id = :userId", nativeQuery = true)
    Membership findByUserId(@Param("userId") Long userId);
}
