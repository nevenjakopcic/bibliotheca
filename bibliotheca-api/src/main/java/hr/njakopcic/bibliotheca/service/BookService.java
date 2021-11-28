package hr.njakopcic.bibliotheca.service;

import hr.njakopcic.bibliotheca.dto.request.CreateBookRequest;
import hr.njakopcic.bibliotheca.dto.response.BookDto;
import hr.njakopcic.bibliotheca.dto.response.BookReservationDto;
import hr.njakopcic.bibliotheca.exception.BookAlreadyBorrowedException;
import hr.njakopcic.bibliotheca.exception.MembershipExpiredException;
import hr.njakopcic.bibliotheca.exception.NotFoundException;
import hr.njakopcic.bibliotheca.mapper.BookDtoMapper;
import hr.njakopcic.bibliotheca.mapper.BookReservationDtoMapper;
import hr.njakopcic.bibliotheca.model.Book;
import hr.njakopcic.bibliotheca.model.BookReservation;
import hr.njakopcic.bibliotheca.model.User;
import hr.njakopcic.bibliotheca.repository.AuthorRepository;
import hr.njakopcic.bibliotheca.repository.BookRepository;
import hr.njakopcic.bibliotheca.repository.BookReservationRepository;
import hr.njakopcic.bibliotheca.repository.GenreRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {

    private static final int DAYS_TO_RETURN = 14;

    private final BookRepository bookRepository;
    private final BookReservationRepository reservationRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    private final CurrentUserService currentUserService;
    private final MembershipService membershipService;

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
            .build();

        book = bookRepository.save(book);

        return BookDtoMapper.map(book);
    }

    public BookReservationDto borrowBook(Long bookId) {

        User currentUser = currentUserService.getLoggedInUser();

        // check if book exists
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found."));

        // check if user's membership is valid
        if (!membershipService.isMembershipActive(currentUser.getId())) {
            throw new MembershipExpiredException("Your membership has expired.");
        }

        // check if book is available to borrow
        if (!isBookAvailableToBorrow(bookId)) {
            throw new BookAlreadyBorrowedException("This book is already borrowed.");
        }

        // reserve book
        BookReservation reservation = BookReservation.builder()
            .book(book)
            .borrower(currentUser)
            .borrowedDate(LocalDate.now())
            .dueDate(LocalDate.now().plusDays(DAYS_TO_RETURN))
            .returned(false)
            .build();

        return BookReservationDtoMapper.map(reservation);
    }

    private boolean isBookAvailableToBorrow(Long bookId) {
        BookReservation latestReservation = reservationRepository.findLatestReservation(bookId);
        return latestReservation == null || latestReservation.getReturned();
    }
}
