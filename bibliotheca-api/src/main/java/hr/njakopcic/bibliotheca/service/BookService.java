package hr.njakopcic.bibliotheca.service;

import hr.njakopcic.bibliotheca.dto.response.BookDto;
import hr.njakopcic.bibliotheca.mapper.BookDtoMapper;
import hr.njakopcic.bibliotheca.repository.BookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                                       .map(BookDtoMapper::map)
                                       .collect(Collectors.toList());
    }
}
