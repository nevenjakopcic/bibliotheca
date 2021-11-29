package hr.njakopcic.bibliotheca.repository;

import hr.njakopcic.bibliotheca.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b.* FROM book b " +
                   "WHERE :title IS NULL OR lower(b.title) LIKE '%' || lower(cast(:title as varchar)) || ' %' " +
                   "AND :genre IS NULL OR b.genre_id = cast(cast(:genre as varchar) as bigint)",
           nativeQuery = true)
    List<Book> findAll(@Param("title") String title,
                       @Param("genre") Long genre);
}
