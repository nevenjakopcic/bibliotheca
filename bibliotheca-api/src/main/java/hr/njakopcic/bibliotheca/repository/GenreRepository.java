package hr.njakopcic.bibliotheca.repository;

import hr.njakopcic.bibliotheca.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
