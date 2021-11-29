package hr.njakopcic.bibliotheca.repository;

import hr.njakopcic.bibliotheca.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "select * from book_reservation br " +
                   "where br.book_id = :bookId " +
                   "order by id desc limit 1;", nativeQuery = true)
    Reservation findLatestReservation(@Param("bookId") Long bookId);

    @Query(value = "select br.id from book_reservation br " +
        "where br.book_id = :bookId " +
        "and br.returned = false ", nativeQuery = true)
    Long findUnreturnedReservationId(@Param("bookId") Long bookId);
}
