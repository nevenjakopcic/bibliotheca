package hr.njakopcic.bibliotheca.repository;

import hr.njakopcic.bibliotheca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
