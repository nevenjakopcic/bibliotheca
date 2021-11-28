package hr.njakopcic.bibliotheca.repository;

import hr.njakopcic.bibliotheca.model.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    @Query(value = "select * from book_reservation br " +
                   "where br.book_id = :bookId " +
                   "order by id desc limit 1;", nativeQuery = true)
    BookReservation findLatestReservation(@Param("bookId") Long bookId);
}
