package hr.njakopcic.bibliotheca.service;

import hr.njakopcic.bibliotheca.dto.request.CreateBookRequest;
import hr.njakopcic.bibliotheca.dto.response.BookDto;
import hr.njakopcic.bibliotheca.exception.NotFoundException;
import hr.njakopcic.bibliotheca.mapper.BookDtoMapper;
import hr.njakopcic.bibliotheca.model.Book;
import hr.njakopcic.bibliotheca.repository.AuthorRepository;
import hr.njakopcic.bibliotheca.repository.BookRepository;
import hr.njakopcic.bibliotheca.repository.GenreRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                                       .map(BookDtoMapper::map)
                                       .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Book with id %d not found.", id)));

        return BookDtoMapper.map(book);
    }

    public BookDto createBook(CreateBookRequest request) {
        Book book = Book.builder()
            .title(request.getTitle())
            .genre(genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new NotFoundException(String.format("Genre with id %d not found.", request.getGenreId()))))
            .author(authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new NotFoundException(String.format("Author with id %d not found.", request.getAuthorId()))))
            .description(request.getDescription())
            .publishDate(request.getPublishDate())
            .build();

        book = bookRepository.save(book);

        return BookDtoMapper.map(book);
    }
}
