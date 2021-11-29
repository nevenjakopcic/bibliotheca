package hr.njakopcic.bibliotheca.service;

import hr.njakopcic.bibliotheca.dto.request.CreateAuthorRequest;
import hr.njakopcic.bibliotheca.dto.request.CreateBookRequest;
import hr.njakopcic.bibliotheca.dto.request.CreateGenreRequest;
import hr.njakopcic.bibliotheca.dto.response.AuthorDto;
import hr.njakopcic.bibliotheca.dto.response.BookDto;
import hr.njakopcic.bibliotheca.dto.response.GenreDto;
import hr.njakopcic.bibliotheca.dto.response.ReservationDto;
import hr.njakopcic.bibliotheca.exception.BookAlreadyBorrowedException;
import hr.njakopcic.bibliotheca.exception.MembershipExpiredException;
import hr.njakopcic.bibliotheca.exception.NotFoundException;
import hr.njakopcic.bibliotheca.mapper.AuthorDtoMapper;
import hr.njakopcic.bibliotheca.mapper.BookDtoMapper;
import hr.njakopcic.bibliotheca.mapper.GenreDtoMapper;
import hr.njakopcic.bibliotheca.mapper.ReservationDtoMapper;
import hr.njakopcic.bibliotheca.model.Author;
import hr.njakopcic.bibliotheca.model.Book;
import hr.njakopcic.bibliotheca.model.Genre;
import hr.njakopcic.bibliotheca.model.Reservation;
import hr.njakopcic.bibliotheca.model.User;
import hr.njakopcic.bibliotheca.repository.AuthorRepository;
import hr.njakopcic.bibliotheca.repository.BookRepository;
import hr.njakopcic.bibliotheca.repository.GenreRepository;
import hr.njakopcic.bibliotheca.repository.ReservationRepository;
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
    private final ReservationRepository reservationRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    private final CurrentUserService currentUserService;
    private final MembershipService membershipService;

    public List<BookDto> getAllBooks(String title, Long genreId) {
        return bookRepository.findAll(title, genreId).stream()
                                       .map((Book source) -> BookDtoMapper.map(source, reservationRepository.findUnreturnedReservationId(source.getId())))
                                       .collect(Collectors.toList());
    }

    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                                        .map(GenreDtoMapper::map)
                                        .collect(Collectors.toList());
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                               .map(AuthorDtoMapper::map)
                               .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Book with id %d not found.", id)));

        return BookDtoMapper.map(book, reservationRepository.findUnreturnedReservationId(book.getId()));
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

        return BookDtoMapper.map(book, null);
    }

    public GenreDto createGenre(CreateGenreRequest request) {
        Genre genre = Genre.builder()
            .name(request.getName())
            .build();

        genre = genreRepository.save(genre);

        return GenreDtoMapper.map(genre);
    }

    public AuthorDto createAuthor(CreateAuthorRequest request) {
        Author author = Author.builder()
            .name(request.getName())
            .build();

        author = authorRepository.save(author);

        return AuthorDtoMapper.map(author);
    }

    public ReservationDto borrowBook(Long bookId) {

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
        Reservation reservation = Reservation.builder()
            .book(book)
            .borrower(currentUser)
            .borrowedDate(LocalDate.now())
            .dueDate(LocalDate.now().plusDays(DAYS_TO_RETURN))
            .returned(false)
            .build();

        reservation = reservationRepository.save(reservation);

        return ReservationDtoMapper.map(reservation);
    }

    public ReservationDto returnBook(Long bookId) {

        Reservation reservation = reservationRepository.findLatestReservation(bookId);

        reservation.setReturned(true);
        reservation = reservationRepository.save(reservation);

        return ReservationDtoMapper.map(reservation);
    }

    private boolean isBookAvailableToBorrow(Long bookId) {
        Reservation latestReservation = reservationRepository.findLatestReservation(bookId);
        return latestReservation == null || latestReservation.getReturned();
    }
}
