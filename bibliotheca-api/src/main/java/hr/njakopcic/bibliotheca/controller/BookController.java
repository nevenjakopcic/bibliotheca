package hr.njakopcic.bibliotheca.controller;

import hr.njakopcic.bibliotheca.dto.ApiResponse;
import hr.njakopcic.bibliotheca.dto.request.CreateBookRequest;
import hr.njakopcic.bibliotheca.dto.request.CreateGenreRequest;
import hr.njakopcic.bibliotheca.service.BookService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBooks(@RequestParam(required = false) final String title,
                                                   @RequestParam(required = false) final Long genre) {
        return new ResponseEntity<>(new ApiResponse(bookService.getAllBooks(title, genre)), HttpStatus.OK);
    }

    @GetMapping("/genres")
    public ResponseEntity<ApiResponse> getAllGenres() {
        return new ResponseEntity<>(new ApiResponse(bookService.getAllGenres()), HttpStatus.OK);
    }

    @GetMapping("/authors")
    public ResponseEntity<ApiResponse> getAllAuthors() {
        return new ResponseEntity<>(new ApiResponse(bookService.getAllAuthors()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable final Long id) {
        return new ResponseEntity<>(new ApiResponse(bookService.getBookById(id)), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse> createBook(@Valid @RequestBody final CreateBookRequest request) {
        return new ResponseEntity<>(new ApiResponse(bookService.createBook(request)), HttpStatus.CREATED);
    }

    @PostMapping("/genre")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse> createGenre(@Valid @RequestBody final CreateGenreRequest request) {
        return new ResponseEntity<>(new ApiResponse(bookService.createGenre(request)), HttpStatus.CREATED);
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<ApiResponse> borrowBook(@PathVariable final Long bookId) {
        return new ResponseEntity<>(new ApiResponse(bookService.borrowBook(bookId)), HttpStatus.OK);
    }

    @PostMapping("/return/{bookId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponse> returnBook(@PathVariable final Long bookId) {
        return new ResponseEntity<>(new ApiResponse(bookService.returnBook(bookId)), HttpStatus.OK);
    }
}
