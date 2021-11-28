package hr.njakopcic.bibliotheca.controller;

import hr.njakopcic.bibliotheca.dto.ApiResponse;
import hr.njakopcic.bibliotheca.dto.request.CreateBookRequest;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBooks() {
        return new ResponseEntity<>(new ApiResponse(bookService.getAllBooks()), HttpStatus.OK);
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

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<ApiResponse> borrowBook(@PathVariable final Long bookId) {
        return new ResponseEntity<>(new ApiResponse(bookService.borrowBook(bookId)), HttpStatus.OK);
    }
}
